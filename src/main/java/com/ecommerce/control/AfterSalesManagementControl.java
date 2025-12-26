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

@WebServlet(name = "AfterSalesManagementControl", value = "/after-sales-management")
@MultipartConfig
public class AfterSalesManagementControl extends HttpServlet {
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
        	List<AfterSalesService> afterSalesList = productDao.getAfterSalesServiceHistoryForSeller(connection, accountId); 
        	
        	request.setAttribute("after_sales_service_history", afterSalesList);
        	
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/after-sales-management.jsp");
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
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        String action = request.getParameter("handleaction");
        Connection connection = null;
        try {
        	connection = dbManager.getConnection();
        	
	        if ("approve".equals(action)) {
	        	dbManager.beginTransaction();
	        	productDao.approveRefund(connection, serviceId, orderId, productId, accountId);
	        	dbManager.commitTransaction();
	        }
	        else if ("reject".equals(action)) {
	        	productDao.rejectRefund(connection, accountId);
	        	
	        }
	        else {
	        	throw new AppException("Wrong parameters, 请联系管理员");
	        }
	        response.sendRedirect(request.getContextPath() + "/after-sales-management");	
		}  
        catch (Exception e) {
        	dbManager.rollbackTransaction();
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
