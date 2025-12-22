package com.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public Connection getConnection() throws SQLException {
       
        try {
        	if (connection == null || connection.isClosed()) {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "2220336");
        	}
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(new Database().getConnection());
//    }
    
    
    private Connection connection;
	
	// 开启事务
    public void beginTransaction() throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(false);
        }
    }
    
    // 提交事务
    public void commitTransaction() throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.setAutoCommit(true); // 恢复自动提交模式
        }
    }
    
    // 回滚事务
    public void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
                connection.setAutoCommit(true); // 恢复自动提交模式
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
