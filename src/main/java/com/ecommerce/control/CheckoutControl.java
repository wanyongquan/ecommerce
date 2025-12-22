package com.ecommerce.control;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.ShippingAddress;

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

@WebServlet(name = "CheckoutControl", value = "/checkout")
public class CheckoutControl extends HttpServlet {
    // Call DAO class to access with database.
    OrderDao orderDao = new OrderDao();
    AccountDao accountDao = new AccountDao();
    Database dbManager = new Database();
    

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 读取收货信息列表
//		super.doGet(req, resp);
    	request.setCharacterEncoding("UTF-8"); 
    	HttpSession session = request.getSession();
    	Account account = (Account) session.getAttribute("account");
    	try (Connection connection = dbManager.getConnection();) {
    		 List<ShippingAddress> address_list = accountDao.getAddressList(connection, account.getId());
    		 request.setAttribute("address_list", address_list);
    	 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/checkout.jsp");
        requestDispatcher.forward(request, response);
	}




	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Get information from input field.

        if (session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
        }
        else {
            double totalPrice = (double) session.getAttribute("total_price");
            Order order = (Order) session.getAttribute("order");
            Account account = (Account) session.getAttribute("account");

            try (Connection connection = dbManager.getConnection();) {
	            //TODO：使用事务
            	dbManager.beginTransaction();
	            // Insert information to account.
	            int accountId = account.getId();
	            int addressId = Integer.parseInt(request.getParameter("addr_id"));
	            
//	            accountDao.updateProfileInformation(accountId, recipientName, lastName, address, email, phone);
	            // Insert order to database.
	            int new_orderId =  orderDao.createOrder(connection, account.getId(), totalPrice, order.getCartProducts());
	           
	            // 记录收件人信息； 
//	            int orderId = orderDao.getLastOrderId();
//	            orderDao.SaveRecipient(connection, new_orderId, recipientName, address, phone);
	            orderDao.SaveRecipientAddress(connection, new_orderId, addressId);
	            dbManager.commitTransaction();
	            session.removeAttribute("order");
	            session.removeAttribute("total_price");
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
            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("thankyou.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
