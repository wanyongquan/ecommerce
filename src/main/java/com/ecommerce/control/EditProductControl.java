package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.ColorStock;
import com.ecommerce.entity.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "EditProductControl", value = "/edit-product")
@MultipartConfig
public class EditProductControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();
    Database dbManager =  new Database();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8"); 
    	HttpSession session = request.getSession();
    	  String alertMsg = (String) session.getAttribute("alertMsg");
          String alertType = (String) session.getAttribute("alertType");
  		if (alertMsg != null) {
      	    request.setAttribute("alertMsg", alertMsg);
      	    request.setAttribute("alertType", alertType);

      	    session.removeAttribute("alertMsg");
      	    session.removeAttribute("alertType");
      	}
    	// Get request product from database.
        int productId = Integer.parseInt(request.getParameter("product-id"));
        

        
        // Get product from database.
        Product product = productDao.getProduct(productId);
        // Get category for product.
        List<Category> categoryList = categoryDao.getAllCategories();
        List<ColorStock> colorList = productDao.getColorStockByProductId(productId);
        request.setAttribute("product", product);
        request.setAttribute("category_list", categoryList);
        request.setAttribute("product_sku_list", colorList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/edit-product.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
    	HttpSession session = request.getSession();
    	// Get the product id from request.
        int productId = Integer.parseInt(request.getParameter("product-id"));
        
        String action = request.getParameter("action");
        Connection connection = null;
        switch (action) {
            case "edit-info":
                // update product base fields
            	// Get product information from request.
                String productName = request.getParameter("product-name");
                double productPrice = Double.parseDouble((request.getParameter("product-price")));
                String productDescription = request.getParameter("product-description");
                int productCategory = Integer.parseInt(request.getParameter("product-category"));
                
                try {
                	connection = dbManager.getConnection();
        	        ProductDao productDao = new ProductDao();
        	        productDao.editProduct(connection, productId, productName,  productPrice, productDescription, productCategory);
        	        session.setAttribute("alertMsg", "商品信息保存成功！");
					session.setAttribute("alertType", "success");
        	        response.sendRedirect(request.getContextPath() +"/edit-product?product-id=" + productId+"&action=edit-info");
        	        return;
                }catch (Exception e) {
        	        
        		    throw new AppException("数据库操作异常",e); // 继续向上抛
        		}finally {
        		    if (connection != null) {
        		        try {
        		            connection.close();
        		        } catch (SQLException ignored) {}
        		    }
        		}
                
            case "edit-image":
				// only handle multipart image

				// 只处理图片（只读 Part）
				// Set default profile image for account.
				Part part = request.getPart("product-image");
				InputStream inputStream = part.getInputStream();

				try {
					if (part == null || part.getSize() == 0) {
					    session.setAttribute("alertMsg", "请先选择图片文件");
					    session.setAttribute("alertType", "warning");
					    response.sendRedirect(
					        request.getContextPath() + "/edit-product?product-id=" + productId +"&action=edit-image");
					   
					    return;
					}
					connection = dbManager.getConnection();

					productDao.editProductImage(connection, productId, inputStream);

					session.setAttribute("alertMsg", "图片保存成功！");
					session.setAttribute("alertType", "success");
					response.sendRedirect(request.getContextPath() +"/edit-product?product-id=" + productId+"&action=edit-image");
        	        return;
				} catch (Exception e) {

					throw new AppException("数据库操作异常", e); // 继续向上抛
				} finally {
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException ignored) {
						}
					}
				}
            case "edit-sku":
				try {
					connection = dbManager.getConnection();
					dbManager.beginTransaction();
					/* ===== ① 更新已有 SKU ===== */
					String[] skuIds = request.getParameterValues("sku-id[]");
					if (skuIds != null) {
						for (String skuIdStr : skuIds) {
							int skuId = Integer.parseInt(skuIdStr);
							String color = request.getParameter("sku-color-" + skuId);
							int qty = Integer.parseInt(request.getParameter("sku-qty-" + skuId));

							productDao.updateSku(connection, skuId, color, qty);
						}
					}

					/* ===== ② 新增 SKU ===== */
					String[] newColors = request.getParameterValues("new-sku-color[]");
					String[] newQtys = request.getParameterValues("new-sku-qty[]");

					if (newColors != null) {
						Map<String, Integer> colorStockMap = new HashMap<>();
			            for (int i = 0; i < newColors.length; i++) {
			                String colorName = newColors[i].trim();
			                if (!colorName.isEmpty() && i < newQtys.length) {
			                    try {
			                        int stock = Integer.parseInt(newQtys[i].trim());
			                        colorStockMap.put(colorName, stock);
			                    } catch (NumberFormatException e) {
			                        // 处理库存格式错误
			                    	session.setAttribute("alertMsg", "库存格式错误！");
			    					session.setAttribute("alertType", "warning");
			    					response.sendRedirect(request.getContextPath() + "/edit-product?product-id=" + productId+"&action=edit-sku");
			                        System.err.println("颜色" + colorName+ "的库存格式不正确");
			                        return ;
			                    }
			                }
			            }
			            
			            if (!colorStockMap.isEmpty()) {
			                productDao.addProductColors(connection, productId, colorStockMap);
			            }
					}
					dbManager.commitTransaction();
					session.setAttribute("alertMsg", "商品规格更新成功！");
					session.setAttribute("alertType", "success");

					response.sendRedirect(request.getContextPath() + "/edit-product?product-id=" + productId+"&action=edit-sku");
					return;

				} catch (Exception e) {
					 dbManager.rollbackTransaction();
					throw new AppException("SKU 更新失败", e);
				} finally {
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException ignored) {
						}
					}
				}

        }
        

        // Add product to database.
        response.sendRedirect("seller-home");
    }
}
