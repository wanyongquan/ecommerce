package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.database.Database;
import com.ecommerce.entity.Shop;

public class ShopDao {
	
	// method to get shop object
	public Shop getShop(String shopName)	throws SQLException{
		/*
		 * 查询店铺名称是否已经被其他用户使用；
		 * */
		Shop shop = new Shop();
		String query = "SELECT * FROM shop.shop WHERE shop_name = ?";
		try (Connection connection = new Database().getConnection();
	   	         PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, shopName);
//			pstmt.setInt(2, accountId);
			ResultSet resultSet = pstmt.executeQuery();
			if( resultSet.next()) {
				shop.setShopId(resultSet.getInt(1));
				shop.setShopName(resultSet.getString(2));
				shop.setShopDescription(resultSet.getString(3));
				shop.setAccountId(resultSet.getInt(4));
				return shop;
			}
		}
		return null;
	}
	
	// method to get shop object
	public boolean checkAccountShopExists(int accountId)	throws SQLException{
		/*
		 * 查询商家是否已经设置店铺名称
		 * */
		Shop shop = new Shop();
		String query = "SELECT * FROM shop.shop WHERE fk_account_id = ?";
		try (Connection connection = new Database().getConnection();
	   	         PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setInt(1, accountId);
			ResultSet resultSet = pstmt.executeQuery();
			return  resultSet.next() ;
		}
		
	}
	// method to get shop object
	public Shop getAccountShop(int accountId)	throws SQLException{
		/*
		 * 查询商家的店铺
		 * */
		Shop shop = new Shop();
		String query = "SELECT * FROM shop.shop WHERE fk_account_id = ?";
		try (Connection connection = new Database().getConnection();
	   	         PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setInt(1, accountId);
			ResultSet resultSet = pstmt.executeQuery();if( resultSet.next()) {
				shop.setShopId(resultSet.getInt(1));
				shop.setShopName(resultSet.getString(2));
				shop.setShopDescription(resultSet.getString(3));
				shop.setAccountId(resultSet.getInt(4));
				return shop;
			}
		}
		return null;
	}
	
	public int CreateShop(int accountId, String shopName, String shopDescription) throws SQLException{
		String query = "Insert into shop.shop ( shop_name, shop_description, fk_account_id) values (?, ?, ?) " ;
		
		try (Connection connection = new Database().getConnection();
   	         PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setString(1, shopName);
			pstmt.setString(2, shopDescription);
			pstmt.setInt(3, accountId);
	           // 执行更新，返回受影响的行数
            int affectedRows = pstmt.executeUpdate();
            
            //  判断是否更新成功（至少有一行被影响）
            return affectedRows ;
		}
		
	}
	
	public int updateShopInformation(int accountId, String shopName, String shopDescription) throws SQLException{
		String query = "Update shop.shop set " + 
				"shop_name = ? " + 
				"shop_description = ?";
		
		try (Connection connection = new Database().getConnection();
   	         PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, shopName);
			pstmt.setString(2, shopDescription);
			
	           // 执行更新，返回受影响的行数
            int affectedRows = pstmt.executeUpdate();
            
            //  判断是否更新成功（至少有一行被影响）
            return affectedRows ;
		}
		
	}
}
