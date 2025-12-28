package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
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
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/checkout.jsp");
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
            Order order = (Order) session.getAttribute("order");
            Account account = (Account) session.getAttribute("account");
            Connection connection = null;
            try {
            	connection = dbManager.getConnection(); 
            
	            //使用事务
            	dbManager.beginTransaction();
	            // Insert information to account.
	            int accountId = account.getId();
	            int addressId = Integer.parseInt(request.getParameter("addr_id"));
	            

	            // Insert order to database.
	            int new_orderId =  orderDao.createOrder(connection, account.getId(), order.getTotal(), order.getCartProducts(), order.getSeller_account_id());
	           
	            // 记录收件人信息； 

	            orderDao.SaveRecipientAddress(connection, new_orderId, addressId);
	            dbManager.commitTransaction();
	            session.removeAttribute("order");
	            session.removeAttribute("total_price");
            }catch (Exception e) {
                dbManager.rollbackTransaction();
                throw new AppException("数据库操作异常",e); // 继续向上抛
            }finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ignored) {}
                }
            }
            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/thankyou.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
