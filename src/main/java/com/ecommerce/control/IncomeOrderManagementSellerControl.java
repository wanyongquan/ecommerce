package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "IncomeOrderManagementSellerControl", value = "/income-order-management")
public class IncomeOrderManagementSellerControl extends HttpServlet {
    // Call DAO class to access with database.
	// Oder management DAO for seller; 
    OrderDao orderDao = new OrderDao();
    Database dbManager = new Database();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get account from session.
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        Connection connection = null;
        try {
        	
        	connection = dbManager.getConnection();
        	List<Order> orderList = orderDao.getIncomeOrderListOfSeller(connection, account.getId());
        	request.setAttribute("order_list", orderList);
            // Set attribute active for order management tab.
            request.setAttribute("income_order_management_active", "active");
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
        // Get request dispatcher and render to order-management page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/order-management-seller.jsp");
        requestDispatcher.forward(request, response);
    }
}
