package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeSellerControl", value = "/seller_home")
public class HomeSellerControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();
    OrderDao orderDao = new OrderDao();
    Database dbManager = new Database();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8"); 
    	HttpSession session = request.getSession();
         Account account = (Account) session.getAttribute("account");
    	// get the amount of the all products; 
         Connection connection= null;
        try {
	        connection = dbManager.getConnection();
	    	int product_amount = productDao.getAmountOfProduct(connection, account.getId());
	    	
	    	int order_amount  = orderDao.getAmountOfOrder(connection, account.getId());
	    	
	    	request.setAttribute("product_amount", product_amount);
	    	request.setAttribute("order_amount", order_amount);
	    	request.setAttribute("dashboard_active", "active");
	       
	    	 
	    }catch (Exception e) {
	        dbManager.rollbackTransaction();
	        throw new AppException("数据库操作异常",e); // 继续向上抛
	    }finally {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException ignored) {}
	        }
	    }
    	// Get all products published by this seller from database.

        int sellerId = account.getId();
        List<Product> productList = productDao.getSellerProducts(sellerId);
        // Get all categories from database.
        List<Category> categoryList = categoryDao.getAllCategories();


        request.setAttribute("product_list", productList);
        request.setAttribute("category_list", categoryList);
        // Set attribute active class for home in header.
        request.setAttribute("home_active", "active");
        // Get request dispatcher and render to index page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/index_seller.jsp");
        requestDispatcher.forward(request, response);
    }
}