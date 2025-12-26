package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.ShopDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.AfterSalesService;
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
import java.util.List;

@WebServlet(name = "AfterSalesHistoryControl", value = "/after-sales-history")
@MultipartConfig
public class AfterSalesHistoryControl extends HttpServlet {
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
        System.out.println("Comment : " + " 检查是否已经登录");
        if (account == null) {
        	throw new AppException("LoginRequired");
        }
        int accountId = account.getId();
      
        Connection connection = null;
        try {
        	connection = dbManager.getConnection();
        	// check if exist after sales apply; 
        	List<AfterSalesService> afterSalesList = productDao.getAfterSalesServiceHistory(connection, accountId); 
        	
        	request.setAttribute("after_sales_service_history", afterSalesList);
        	
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/after-sales-history.jsp");
            requestDispatcher.forward(request, response);
        	
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

  
}
