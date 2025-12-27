package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.ShopDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductComment;
import com.ecommerce.entity.Shop;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "AfterSalesControl", value = "/after-sales-apply")
@MultipartConfig
public class AfterSalesControl extends HttpServlet {
    // Call DAO class to access with the database.
    AccountDao accountDao = new AccountDao();
    ShopDao shopDao = new ShopDao();
    Database dbManager = new Database();
    ProductDao productDao = new ProductDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8"); 
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        System.out.println("Comment : " + " 检查是否已经登录： ");
        if (account == null) {
        	throw new AppException("LoginRequired");
        }
        int accountId = account.getId();
//        
        int productId = Integer.parseInt(request.getParameter("product_id") );
        int orderId = Integer.parseInt(request.getParameter("order_id"));
//        String sku = request.getParameter("sku");
//        
        Connection connection = null;
        try {
        	connection = dbManager.getConnection();
        	// check if exist after sales apply; 
        	boolean existsAfterSalesApply = productDao.checkAfterSalesApplyExists(connection, orderId, productId);
        	if (existsAfterSalesApply) {
        		RequestDispatcher requestDispatcher = request.getRequestDispatcher("after-sales-history");
	            requestDispatcher.forward(request, response);
        	}
        	else {
	        	CartProduct cartProduct = productDao.getOrderDetail(connection, orderId, productId);
	        	Product product = productDao.getProduct(productId);
	
	        	
	        	request.setAttribute("product_id", productId);
	        	request.setAttribute("order_id", orderId);
	        	request.setAttribute("product", product);
	        	request.setAttribute("cartProduct", cartProduct);
	        	request.setAttribute("sku", cartProduct.getPickedColor());
	        	request.setAttribute("existsAfterSalesApply", existsAfterSalesApply);
	        	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/after-sales.jsp");
	            requestDispatcher.forward(request, response);
        	}
        }
        catch (Exception e) {
	        
	        throw new AppException("数据库操作异常",e); // 继续向上抛
	    }finally {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException ignored) {}
	        }
	    }
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
        	throw new AppException("LoginRequired");
        }
        int accountId = account.getId();
        
        String orderId = request.getParameter("orderId");
        String productId = request.getParameter("productId");
        
        String serviceType= "RETURN";// 只支持1种售后类型；
        String reason = request.getParameter("reason");
        String description = request.getParameter("description");

        Connection connection= null;
        try {
        	connection = dbManager.getConnection();
        
        	 productDao.createAfterSalesApply(connection, Integer.parseInt(orderId), Integer.parseInt(productId), accountId, 0, reason, description);         
	    }
	    catch (Exception e) {
	        
	        throw new AppException("数据库操作异常",e); // 继续向上抛
	    }finally {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException ignored) {}
	        }
	    }

       
        response.sendRedirect(request.getContextPath() + "/after-detail?order_id="+orderId);
    }
}
