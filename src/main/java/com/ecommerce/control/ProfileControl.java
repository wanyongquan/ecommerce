package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.ShopDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Shop;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "ProfileControl", value = "/profile-page")
@MultipartConfig
public class ProfileControl extends HttpServlet {
    // Call DAO class to access with the database.
    AccountDao accountDao = new AccountDao();
    ShopDao shopDao = new ShopDao();
    Database dbManager = new Database();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8"); 
    	HttpSession session = request.getSession();
         Account account = (Account) session.getAttribute("account");
         
         String alertMsg = (String) session.getAttribute("alertMsg");
         String alertType = (String) session.getAttribute("alertType");
 		if (alertMsg != null) {
     	    request.setAttribute("alertMsg", alertMsg);
     	    request.setAttribute("alertType", alertType);

     	    session.removeAttribute("alertMsg");
     	    session.removeAttribute("alertType");
     	}
         try {
	         Shop shop = shopDao.getAccountShop(account.getId());
	         request.setAttribute("account_shop", shop);
         }
         catch (SQLException e) {
             // 更详细的错误处理
             System.err.println("数据库操作失败:");
             System.err.println("SQL状态码: " + e.getSQLState());
             System.err.println("错误代码: " + e.getErrorCode());
             System.err.println("错误信息: " + e.getMessage());
         }
         catch(Exception e)
         {
         	 System.err.println("失败: " + e.getMessage());
         }
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/profile-page.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
     // 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        Account account = (Account) session.getAttribute("account");

        int accountId = account.getId();
        String updateType = request.getParameter("updateType");
        Connection connection = null;
        if ("info".equals(updateType)) {
	        String firstName = request.getParameter("first-name");
	        String lastName = request.getParameter("last-name");
	        String address = request.getParameter("address");
	        String email = request.getParameter("email");
	        String phone = request.getParameter("phone");
	
	        System.out.println(accountId + " " + firstName + " " + lastName + " " + address + " " + email + " " + phone);
	       
	        try {
	        	connection = dbManager.getConnection();
	        
				 accountDao.editProfileInformation(connection, accountId, firstName, lastName, address, email, phone);
				 // 更新session中的Account的value
				 Account account_new = accountDao.getAccount(accountId);
				 session.setAttribute("account", account_new);
				 session.setAttribute("alertMsg", "个人信息保存成功！");
					session.setAttribute("alertType", "success");	 
			} catch (Exception e) {
		        
		        throw new AppException("数据库操作异常",e); // 继续向上抛
		    }finally {
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException ignored) {}
		        }
		    }
        } else if ("avatar".equals(updateType)) {
            // 只处理头像（只读 Part）
        	 // Set default profile image for account.
	        Part part = request.getPart("profile-image");
	        InputStream inputStream = part.getInputStream();
	        
	        try {
	        	connection = dbManager.getConnection();
	        
				 accountDao.editProfileAvatar(connection, accountId, inputStream);
				 // 更新session中的Account的value
				 Account account_new = accountDao.getAccount(accountId);
				 session.setAttribute("account", account_new);
				 session.setAttribute("alertMsg", "头像保存成功！");
				 session.setAttribute("alertType", "success");
			} catch (Exception e) {
		        
		        throw new AppException("数据库操作异常",e); // 继续向上抛
		    }finally {
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException ignored) {}
		        }
		    }
        }
       
        response.sendRedirect(request.getContextPath() + "/profile-page");
    }
    
    
}
