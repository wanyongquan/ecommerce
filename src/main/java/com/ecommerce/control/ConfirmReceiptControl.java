package com.ecommerce.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.OrderDao;

/**
 * Servlet implementation class ConfirmReceiptControl
 */
@WebServlet(name="ConfirmReceiptControl", value="/confirm-receipt")
public class ConfirmReceiptControl extends HttpServlet {
	
	OrderDao orderDao = new OrderDao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmReceiptControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get account from session.
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter("order-id"));
        try {
        	// 更新订单状态为 确认收货；
        	int result = orderDao.updateOrderStatus(orderId, 2); 
        
            response.sendRedirect(request.getContextPath() + "/order-detail?order-id="+orderId);
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
