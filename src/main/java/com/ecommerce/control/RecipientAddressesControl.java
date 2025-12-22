package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.ShopDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.ShippingAddress;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RecipientAddressesControl", value = "/recipient-addresses")
@MultipartConfig
public class RecipientAddressesControl extends HttpServlet {
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
         try (Connection connection = dbManager.getConnection();) {
	         List<ShippingAddress> address_list = accountDao.getAddressList(connection, account.getId()); 
        	 
	         request.setAttribute("address_list", address_list);
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
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/recipient-addresses.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        
     // 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        if ("create".equals(action)) {
        	createRecipientAddress(request);
        }
        else if ("delete".equals(action)) {
        	deleteRecipientAddress(request);
            
        } else if ("edit".equals(action)) {
        	editRecipientAddress(request);
        }
        else if ("setDefault".equals(action)) {
        	setDefaultRecipientAddress(request);
        }
       
       
        response.sendRedirect(request.getContextPath() + "/recipient-addresses");
    }
    
    private void createRecipientAddress(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	Account account = (Account) session.getAttribute("account");

    	int accountId = account.getId();
        String recipientName = request.getParameter("recipientName");
        String distinct = request.getParameter("address");
        String addressDetail = request.getParameter("address-detail");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String addr_label = request.getParameter("address-label");
        String is_default_addr = request.getParameter("isDefault");
        
        int is_default = 0;
        if (is_default_addr != null && !is_default_addr.isEmpty()) { is_default = Integer.parseInt(is_default_addr);}

                
        try (Connection connection = dbManager.getConnection();) {
        	 accountDao.createRecipientAddress(connection, accountId, recipientName, distinct, addressDetail, phone, email, is_default, addr_label);
		}
        
        catch (SQLException e) {			
			throw new AppException(e.getMessage(), e.getErrorCode(), e);
		}
        catch(Exception e) {
        	e.printStackTrace();
        }	
    }
    
    private void editRecipientAddress(HttpServletRequest request) {
    	// 处理编辑
    	
        int addressId = Integer.parseInt( request.getParameter("addr_id"));
        // 编辑逻辑
        String recipientName = request.getParameter("recipientName");
        String distinct = request.getParameter("address");
        String addressDetail = request.getParameter("address-detail");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String addr_label = request.getParameter("address-label");
        String is_default_addr = request.getParameter("isDefault");
        int is_default = 0;
        if (is_default_addr != null && !is_default_addr.isEmpty()) { is_default = Integer.parseInt(is_default_addr);}
        
        try (Connection connection = dbManager.getConnection();) {
        	
        	accountDao.editRecipientAddress(connection, addressId, recipientName, distinct, addressDetail, phone, email, is_default, addr_label);
        	
        } catch (SQLException e) {
			
			throw new AppException(e.getMessage(), e.getErrorCode(), e);
		}
    }
    
    private void deleteRecipientAddress(HttpServletRequest request) {
    	 // 处理删除
        int addressId = Integer.parseInt( request.getParameter("addr_id"));
        // 调用删除逻辑
        try (Connection connection = dbManager.getConnection();) {
        	accountDao.deleteRecipientAddress(connection, addressId);
        } catch (SQLException e) {
        	throw new AppException(e.getMessage(), e.getErrorCode(), e);
		}
    }
    
    private void setDefaultRecipientAddress(HttpServletRequest request) {
    	 int addressId = Integer.parseInt( request.getParameter("addr_id"));
    	 try (Connection connection = dbManager.getConnection();) {
    		 dbManager.beginTransaction();
    		 accountDao.removeRecipientAddressDefault(connection);
    		 accountDao.setRecipientAddressDefault(connection, addressId);
    		 dbManager.commitTransaction();
    		 
    	 } catch (SQLException e) {
			throw new AppException(e.getMessage(), e.getErrorCode(), e);
		}
    }
}
