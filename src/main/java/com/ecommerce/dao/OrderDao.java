package com.ecommerce.dao;

import com.ecommerce.Exception.AppException;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderShippingAddress;
import com.ecommerce.entity.Product;
//import com.oracle.wls.shaded.org.apache.xpath.operations.Or;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Call ProductDao class to access with database.
    ProductDao productDao = new ProductDao();
    AccountDao accountDao = new AccountDao();

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
        List<CartProduct> list = orderDao.getOrderDetailHistory(1);
        for (CartProduct cartProduct : list) {
            System.out.println(cartProduct.toString());
        }
    }

    // Method to get last order id in database.
    public int getLastOrderId() {
        String query = "SELECT order_id FROM `order` ORDER BY order_id DESC LIMIT 1";
        int orderId = 0;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return orderId;
    }

    // Method to insert order detail information.
    private void createOrderDetail(Connection connection, int newOrderId, List<CartProduct> cartProducts) throws SQLException {

        String query = "INSERT INTO order_detail (fk_order_id, fk_product_id, product_quantity, product_price, product_color) VALUES (?, ?, ?, ?, ?);";

        // Get latest orderId to insert list of cartProduct to order.
//        int orderId = getLastOrderId();
        for (CartProduct cartProduct : cartProducts) {
            productDao.decreaseProductAmount(cartProduct.getProduct().getId(), cartProduct.getQuantity());
            

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, newOrderId);
                preparedStatement.setInt(2, cartProduct.getProduct().getId());
                preparedStatement.setInt(3, cartProduct.getQuantity());
                preparedStatement.setDouble(4, cartProduct.getPrice());

                preparedStatement.setString(5, cartProduct.getPickedColor());

                preparedStatement.executeUpdate();
            
        }
    }

    // Method to insert order information to database.
    public int createOrder(Connection connection, int buyer_accountId, double totalPrice, List<CartProduct> cartProducts, int seller_account_id) throws SQLException {
        
        String query = "INSERT INTO `order` (fk_account_id, order_total) VALUES (?, ?);";
        

        preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, buyer_accountId);
        preparedStatement.setDouble(2, totalPrice);
        int affectedRows = preparedStatement.executeUpdate();
        
        int newOrderId = -1;
        if (affectedRows > 0) {
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                     newOrderId= rs.getInt(1);  // 返回自增ID
                }
            }
        }
        
        //TODO: 从买家账户扣款； 增加收款到卖家账户；
        handleOrderPayment(connection, newOrderId, buyer_accountId, seller_account_id, totalPrice);
            
        // Call create order detail method.
        createOrderDetail(connection, newOrderId, cartProducts);
        
        return newOrderId;
    }

    // Method to get order detail list of a seller.
    public List<CartProduct> getSellerOrderDetail(int productId) {
        List<CartProduct> list = new ArrayList<>();
        String query = "SELECT * FROM order_detail WHERE fk_product_id = " + productId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = productDao.getProduct(resultSet.getInt(1));
                int orderId = resultSet.getInt(2);
                int productQuantity = resultSet.getInt(3);
                double productPrice = resultSet.getDouble(4);

                String color = resultSet.getString(5);
//                list.add(new CartProduct(product, productQuantity, productPrice, color));
                list.add(new CartProduct(product, orderId, productQuantity, productPrice, color));

            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Query cart product list catch:");
            System.out.println(e.getMessage());
        }
        return list;
    }

    // Method to get an Order
    public Order getOrderById(int orderId) {
    	
    	String query = "SELECT * FROM shop.order WHERE order_id = " + orderId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {                
                //int orderId = resultSet.getInt(1);
                double orderTotal = resultSet.getDouble(3);
                Date orderDate = resultSet.getDate(4);
                int status = resultSet.getInt(5);

                return new Order(orderId, orderTotal, orderDate, status);

            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Query cart product list catch:");
            System.out.println(e.getMessage());
        }
        return null;
    	
    }
    // Method to get order history of a customer.
    public List<Order> getOrderHistory(int accountId) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT * FROM `order` WHERE fk_account_id = " + accountId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                double orderTotal = resultSet.getDouble(3);
                Date orderDate = resultSet.getDate(4);
                int status = resultSet.getInt(5);

                list.add(new Order(orderId, orderTotal, orderDate, status));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Order history catch:");
            System.out.println(e.getMessage());
        }
        return list;
    }

    // Method to get order detail history.
    public List<CartProduct> getOrderDetailHistory(int orderId) {
        List<CartProduct> list = new ArrayList<>();
        String query = "SELECT * FROM order_detail WHERE fk_order_id = " + orderId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = productDao.getProduct(resultSet.getInt(1));
                int quantity = resultSet.getInt(3);
                double price = resultSet.getDouble(4);

                String color = resultSet.getString(5);
                
                list.add(new CartProduct(product, quantity ,price, color));

            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Get order detail catch:");
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    // method to get recipient information 
    public OrderShippingAddress getOrderRecipientInfo(int orderId) {
    	OrderShippingAddress recipientInfo = new OrderShippingAddress();
    	String query = "SELECT * FROM order_shipping_address WHERE order_id = " + orderId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	recipientInfo.setOrderID(orderId);
            	recipientInfo.setRecipientName(resultSet.getString(3));
            	recipientInfo.setPhone(resultSet.getString(4));
            	recipientInfo.setAddress_detail(resultSet.getString(5));
            }
       
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println("Get order shipping address catch:");
	        System.out.println(e.getMessage());
	    }
        return recipientInfo;
    }
    
    // Method to update order status 
    public int updateOrderStatus(int orderId, int status) throws SQLException  {
    	 String query = "UPDATE shop.order SET order_status = ? WHERE order_id = ?";
    	 try (Connection connection = new Database().getConnection();
    	         PreparedStatement pstmt = connection.prepareStatement(query)) {
           // 创建预编译语句并设置参数
    		 pstmt.setInt(1, status);  // 第一个问号
    		 pstmt.setInt(2, orderId); // 第二个问号
           // 执行更新，返回受影响的行数
            int affectedRows = pstmt.executeUpdate();
            
            // . 判断是否更新成功（至少有一行被影响）
            return affectedRows ;
    	}
    }
    
    // Method to update recipient  
    public int SaveRecipient(Connection connection, int orderId, String recipient_name, String address, String phone) throws SQLException  {
    	 String query = "INSERT into shop.order_shipping_address (order_id, recipient_name, address_detail, phone) values ( ?, ?, ?, ?)";
    	 
    	   PreparedStatement pstmt = connection.prepareStatement(query) ;
           // 创建预编译语句并设置参数
    		 pstmt.setInt(1, orderId);  // 第一个问号
    		 pstmt.setString(2, recipient_name); // 第二个问号
    		 pstmt.setString(3, address); // 第三个问号
    		 pstmt.setString(4, phone); // 第四个问号
           // 执行更新，返回受影响的行数
            int affectedRows = pstmt.executeUpdate();
            
            // . 判断是否更新成功（至少有一行被影响）
            return affectedRows ;
    	
    }
    // Method to update recipient  
    public int SaveRecipientAddress(Connection connection, int orderId, int addressId) throws SQLException  {
    	 StringBuilder sb_query = new StringBuilder("INSERT into shop.order_shipping_address (order_id, recipient_name, address_detail, phone) " );
    	 sb_query.append(" SELECT ?, recipient_name, ");
    	 sb_query.append(" CONCAT_WS(' ', address, address_detail) as full_address, ");
    	 sb_query.append(" phone");
    	 sb_query.append(" FROM shipping_address " ); 
    	 sb_query.append(" WHERE id = ?  ");
    	 
    	   PreparedStatement pstmt = connection.prepareStatement(sb_query.toString()) ;
           // 创建预编译语句并设置参数
    		 pstmt.setInt(1, orderId);  // 第一个问号    		
    		 pstmt.setInt(2, addressId); // 第2个问号
           // 执行更新，返回受影响的行数
            int affectedRows = pstmt.executeUpdate();
            
            // . 判断是否更新成功（至少有一行被影响）
            return affectedRows ;
    	
    }
    
    public void handleOrderPayment(Connection connection, int orderId, int buyer_account_id, int seller_account_id, double order_price) throws SQLException {
    	// 检查买家余额是否足够
        String checkBalanceQuery = "SELECT account_balance FROM ACCOUNT WHERE account_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkBalanceQuery)) {
            checkStmt.setInt(1, buyer_account_id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getDouble("account_balance") < order_price) {
                throw new AppException("买家余额不足");
            }
        }
        // 扣款
    	String deduct_query = "UPDATE ACCOUNT SET account_balance = account_balance - ?  where account_id = ? ;" ;
    	
    	 PreparedStatement deduct_pstmt = connection.prepareStatement(deduct_query) ;
    	 deduct_pstmt.setDouble(1, order_price);
    	 deduct_pstmt.setInt(2, buyer_account_id);
    	 deduct_pstmt.executeUpdate();
    	 // 加款
    	 String add_query = "UPDATE ACCOUNT SET account_balance = account_balance + ? where account_id = ?";
    	 PreparedStatement add_pstmt = connection.prepareStatement(add_query) ;
    	 add_pstmt.setDouble(1, order_price);
    	 add_pstmt.setInt(2, seller_account_id);
    	 add_pstmt.executeUpdate();
    	 
    }
}