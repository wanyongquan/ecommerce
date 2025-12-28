package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddProductControl", value = "/add-product")
@MultipartConfig
public class AddProductControl extends HttpServlet {
	Database dbManager = new Database();
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// 设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
    	// Get product information from request.
        String productName = request.getParameter("product-name");
        double productPrice = Double.parseDouble((request.getParameter("product-price")));
        String productDescription = request.getParameter("product-description");
        int productCategory = Integer.parseInt(request.getParameter("product-category"));
        int productAmount = Integer.parseInt(request.getParameter("product-amount"));
        
        /************  12.17 ************/
        // 获取颜色和库存信息
        String[] colorNames = request.getParameterValues("color-name[]");
        String[] colorStocks = request.getParameterValues("color-stock[]");
        
        // 验证颜色数据
        if (colorNames == null || colorStocks == null || colorNames.length == 0) {
            // 处理错误：没有颜色信息
            request.setAttribute("error", "At least one color is required.");
            request.getRequestDispatcher("product-management").forward(request, response);
            return;
        }
        /************  12.17 ************/

        // Get upload image.
        Part part = request.getPart("product-image");
        InputStream inputStream = part.getInputStream();

        // Get the seller id for product.
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        int sellerId = account.getId();

        // Add product to database.
        ProductDao productDao = new ProductDao();

        int productId = productDao.addProductAndReturnID(productName, inputStream, productPrice, productDescription, productCategory, sellerId, productAmount);
        
        Connection connection = null;
		try {
			connection = dbManager.getConnection();
		
	        // 保存颜色库存信息
	        if (productId > 0) {
	            Map<String, Integer> colorStockMap = new HashMap<>();
	            for (int i = 0; i < colorNames.length; i++) {
	                String colorName = colorNames[i].trim();
	                if (!colorName.isEmpty() && i < colorStocks.length) {
	                    try {
	                        int stock = Integer.parseInt(colorStocks[i].trim());
	                        colorStockMap.put(colorName, stock);
	                    } catch (NumberFormatException e) {
	                        // 处理库存格式错误
	                        System.err.println("颜色" + colorName+ "的库存格式不正确");
	                    }
	                }
	            }
	            
	            if (!colorStockMap.isEmpty()) {
	                productDao.addProductColors(connection, productId, colorStockMap);
	            }
	        }
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
        response.sendRedirect("product-management");
    }
}

