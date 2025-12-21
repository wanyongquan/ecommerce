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

@WebServlet(name = "ShippingOrderDetailControl", value = "/shipping-order-detail")
public class ShippingOrderDetailControl extends HttpServlet {
    // Call DAO class to access with database.
    OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get order id from request.
        int orderId = Integer.parseInt(request.getParameter("order-id"));
        
        Order  order = orderDao.getOrderById(orderId);
        // Get order by id from database.
        List<CartProduct> list = orderDao.getOrderDetailHistory(orderId);
        OrderShippingAddress recipientInfo = orderDao.getOrderRecipientInfo(orderId);
        
         
        request.setAttribute("order_detail_list", list);
        // 订单详情页显示 买家账号， 收件人信息；
        request.setAttribute("order", order);
        request.setAttribute("recipientInfo", recipientInfo);
        
        // Get request dispatcher and render to order-detail page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("shipping-order-detail.jsp");
        requestDispatcher.forward(request, response);
    }
}

