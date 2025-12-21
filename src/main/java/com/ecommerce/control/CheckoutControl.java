package com.ecommerce.control;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckoutControl", value = "/checkout")
public class CheckoutControl extends HttpServlet {
    // Call DAO class to access with database.
    OrderDao orderDao = new OrderDao();
    AccountDao accountDao = new AccountDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Get information from input field.
        String recipientName = request.getParameter("recipient-name");
        String lastName = request.getParameter("last-name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
        }
        else {
            double totalPrice = (double) session.getAttribute("total_price");
            Order order = (Order) session.getAttribute("order");
            Account account = (Account) session.getAttribute("account");

            try {
	            //TODO：使用事务
	            // Insert information to account.
	            int accountId = account.getId();
	            accountDao.updateProfileInformation(accountId, recipientName, lastName, address, email, phone);
	            // Insert order to database.
	            orderDao.createOrder(account.getId(), totalPrice, order.getCartProducts());
	           
	            // 记录收件人信息； 
	            int orderId = orderDao.getLastOrderId();
	            orderDao.SaveRecipient(orderId, recipientName, address, phone);
	            
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
