package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.TopNProductSales;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeControl", value = {"","/index.jsp"})
public class HomeControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();
    Database dbManager = new Database();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get all products from database.
    	
    	Connection connection =null;
    	try {
    		connection = dbManager.getConnection();
	        List<TopNProductSales> productList = productDao.getTop5SalesProductList(connection);
	        // Get all categories from database.
	        List<Category> categoryList = categoryDao.getAllCategories();
	        System.out.println("HomeControl:   doGet" );
	
	        request.setAttribute("recommend_product_list", productList);
	        request.setAttribute("category_list", categoryList);
	        // Set attribute active class for home in header.
	        request.setAttribute("home_active", "active");
    	}catch (Exception e) {

			throw new AppException("数据库操作异常",e); // 继续向上抛
		}finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ignored) {}
			}
		}
        // Get request dispatcher and render to index page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
        requestDispatcher.forward(request, response);
    }
}