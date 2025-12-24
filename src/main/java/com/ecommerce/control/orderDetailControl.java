package com.ecommerce.control;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderShippingAddress;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "orderDetailControl", value = "/order-detail")
public class orderDetailControl extends HttpServlet {
    // Call DAO class to access with database.
    OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
    	
        
        //TODO: ========== ：检查订单是否属于当前用户 ==========
        // 防止用户查看他人订单
//        if (!orderDao.isOrderBelongsToUser(orderId, account.getId())) {
//            request.setAttribute("errorMessage", "您无权查看此订单");
//            request.getRequestDispatcher("access-denied.jsp").forward(request, response);
//            return;
//        }
        
        // Get order id from request.
        int orderId = Integer.parseInt(request.getParameter("order-id"));
        // Get order by id from database.
        List<CartProduct> list = orderDao.getOrderDetailHistory(orderId);
        OrderShippingAddress recipientInfo = orderDao.getOrderRecipientInfo(orderId);
        Order  order = orderDao.getOrderById(orderId);
         
        request.setAttribute("order_detail_list", list);
        // 订单详情页显示 买家账号， 收件人信息；
        request.setAttribute("recipientInfo", recipientInfo);
        request.setAttribute("order", order);
        // Get request dispatcher and render to order-detail page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/order-detail.jsp");
        requestDispatcher.forward(request, response);
    }
}

