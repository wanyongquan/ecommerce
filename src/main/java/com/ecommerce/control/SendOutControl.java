package com.ecommerce.control;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SendOutControl", value = "/send-out")
public class SendOutControl extends HttpServlet {
    // Call DAO class to access with database.
	// 发货 DAO for seller; 
    OrderDao orderDao = new OrderDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get account from session.
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter("order-id"));
        try {
        	// 更新订单状态为 已发货；
        	int result = orderDao.updateOrderStatus(orderId, 1); 
        
//	        Account account = (Account) session.getAttribute("account");
	        // Get order history of account from database.
//	        List<Order> orderList = orderDao.getOrderHistory(account.getId());
	
//	        request.setAttribute("order_list", orderList);
	        // Set attribute active for order management tab.
//	        request.setAttribute("order_history_active", "active");
	        // Get request dispatcher and render to order-management page.
//	        RequestDispatcher requestDispatcher = request.getRequestDispatcher("order-management-seller");
//	        requestDispatcher.forward(request, response);
        	  // 登录成功后跳转到首页
            response.sendRedirect(request.getContextPath() + "/order-management-seller");
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
    }
}
