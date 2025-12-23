package com.ecommerce.dao;

import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ecommerce.entity.ShippingAddress;

public class AccountDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Method to get blob image from database.
    private String getBase64Image(Blob blob) throws SQLException, IOException {
        InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Method to execute get account query.
    private Account queryGetAccount(String query) {
        Account account = new Account();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
                account.setUsername(resultSet.getString(2));
                account.setPassword(resultSet.getString(3));
                account.setIsSeller(resultSet.getInt(4));
                account.setIsAdmin(resultSet.getInt(5));
                account.setAddress(resultSet.getString(7));
                account.setFirstName(resultSet.getString(8));
                account.setLastName(resultSet.getString(9));
                account.setEmail(resultSet.getString(10));
                account.setPhone(resultSet.getString(11));
                account.setShopName(resultSet.getString("shop_name"));
                // Get profile image from database.
                if (resultSet.getBlob(6) == null) {
                    account.setBase64Image(null);
                } else {
                    account.setBase64Image(getBase64Image(resultSet.getBlob(6)));
                }
                account.setBalance(resultSet.getBigDecimal("account_balance"));
                return account;
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Method to get account by id.
    public Account getAccount(int accountId) {
        String query = "SELECT * FROM account  left join shop.shop on account.account_Id = shop.shop.fk_account_id  WHERE account_id = " + accountId;
        return queryGetAccount(query);
    }

    // Method to get login account from database.
    public Account checkLoginAccount(String username, String password) {

        String query = "SELECT * FROM account  left join shop.shop on account.account_Id = shop.shop.fk_account_id  WHERE  account_name = '" + username + "' AND account_password = '" + password + "' and account_is_seller = 0";

        return queryGetAccount(query);
    }
    // Method to get login account from database.
    public Account checkLoginAccount_Seller(String username, String password) {

        String query = "SELECT * FROM account left join shop.shop on account.account_Id = shop.shop.fk_account_id  WHERE  account_name = '" + username + "' AND account_password = '" + password + "' and account_is_seller=1";

        return queryGetAccount(query);
    }

    // Method to check is username exist or not.
    public boolean checkUsernameExists(String username) {
        String query = "SELECT * FROM account  left join shop.shop on account.account_Id = shop.shop.fk_account_id  WHERE  account_name = '" + username + "'";
        return (queryGetAccount(query) != null);
    }

    // Method to create an account.
    public void createAccount(String username, String password, InputStream image) {
        String query = "INSERT INTO account (account_name, account_password, account_image, account_is_seller, account_is_admin) VALUES (?, ?, ?, 0, 0)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setBinaryStream(3, image);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createAccountSeller(String username, String password, InputStream image) {
        String query = "INSERT INTO account (account_name, account_password, account_image, account_is_seller, account_is_admin) VALUES (?, ?, ?, 1, 0)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setBinaryStream(3, image);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Method to edit profile information.
    public void editProfileInformation(Connection connection, int accountId, String firstName, String lastName, String address, String email, String phone, InputStream image) {
        String query = "UPDATE account SET " +
                "account_first_name = ?, " +
                "account_last_name = ?, " +
                "account_address = ?, " +
                "account_email = ?, " +
                "account_phone = ?, " +
                "account_image = ?" +
                "WHERE account_id = ?";
        try {
            
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);
            preparedStatement.setBinaryStream(6, image);
            preparedStatement.setInt(7, accountId);
            preparedStatement.executeUpdate();
        } catch ( SQLException e) {
            System.out.println("Update profile catch: " + e.getMessage());
        }
    }

    // Method to update profile information.
    public void updateProfileInformation(int accountId, String firstName, String lastName, String address, String email, String phone) {
        String query = "UPDATE account SET " +
                "account_first_name = ?, " +
                "account_last_name = ?, " +
                "account_address = ?, " +
                "account_email = ?, " +
                "account_phone = ? " +
                "WHERE account_id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);
            preparedStatement.setInt(6, accountId);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Update profile catch: " + e.getMessage());
        }
    }
    
    public void createRecipientAddress(Connection connection, int accountId, String recipientName, String distinct, String addressDetail, String phone, String email, int isDefault, String addressLabel) throws SQLException{
    	String query = "INSERT  into shop.shipping_address (account_id, recipient_name, address, address_detail, phone, email, is_default, address_label) values (?,?,?,?,?,?,?,?) ";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, accountId);
    	pstmt.setString(2, recipientName);
    	pstmt.setString(3, distinct);
    	pstmt.setString(4, addressDetail);
    	pstmt.setString(5, phone);
    	pstmt.setString(6, email);
    	pstmt.setInt(7, isDefault);
    	pstmt.setString(8, addressLabel);
    	pstmt.executeUpdate();
    }
    
    // Method  to remove the flag of  the current default address;
    public void removeRecipientAddressDefault(Connection connection) throws SQLException {
    	StringBuilder  sb_query = new StringBuilder("UPDATE shop.shipping_address set is_default = 0 where is_default=1");
    	PreparedStatement pstmt = connection.prepareStatement(sb_query.toString());
    	pstmt.executeUpdate();
    }
    
    //  Method to set a address as default;
    public void setRecipientAddressDefault(Connection connection, int addressId) throws SQLException {
    	StringBuilder  sb_query = new StringBuilder("UPDATE shop.shipping_address set is_default = 1 where id= ?");
    	PreparedStatement pstmt = connection.prepareStatement(sb_query.toString());
    	pstmt.setInt(1, addressId);
    	pstmt.executeUpdate();
    }
    
    // Method to edit existing address;
    public void editRecipientAddress(Connection connection, int addressId, String recipientName, String distinct, String addressDetail, String phone, String email, int isDefault, String addressLabel) throws SQLException{
    	StringBuilder  sb_query = new StringBuilder("UPDATE  shop.shipping_address SET " );
    	sb_query.append(" recipient_name = ?, ");
    	sb_query.append(" address = ?, ");
    	sb_query.append(" address_detail = ?, ");
    	sb_query.append(" phone = ?, ");
    	sb_query.append(" email = ?, ");
    	sb_query.append(" is_default = ?, ");
    	sb_query.append(" address_label = ? ");
    	sb_query.append(" where id = ? ");
    			
    	PreparedStatement pstmt = connection.prepareStatement(sb_query.toString());
    	
    	pstmt.setString(1, recipientName);
    	pstmt.setString(2, distinct);
    	pstmt.setString(3, addressDetail);
    	pstmt.setString(4, phone);
    	pstmt.setString(5, email);
    	pstmt.setInt(6, isDefault);
    	pstmt.setString(7, addressLabel);
    	pstmt.setInt(8,	addressId);
    	pstmt.executeUpdate();
    }
    
    // Method to delete a address
    public void deleteRecipientAddress(Connection connection, int addressId) throws SQLException {
    	StringBuilder  sb_query = new StringBuilder("DELETE from shop.shipping_address where id= ? ");
    	PreparedStatement pstmt = connection.prepareStatement(sb_query.toString());
    	pstmt.setInt(1, addressId);
    	pstmt.executeUpdate();
    }
    
    public List<ShippingAddress> getAddressList(Connection connection, int accountId)  throws SQLException{
    	List<ShippingAddress> list = new ArrayList<>();
    	String query = "select * from shop.shipping_address  where account_id = ? order by is_default desc, recipient_name asc";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, accountId);
    	resultSet = pstmt.executeQuery();
    	while(resultSet.next()) {
    		int addressId = resultSet.getInt(1);
    		
    		String recipientName = resultSet.getString(3);
    		String distinct = resultSet.getString(4);
    		String addressDetail = resultSet.getString(5);
    		String phone = resultSet.getString(6);
    		String email = resultSet.getString(7);
    		int is_default = resultSet.getInt(8);
    		String addressLabel = resultSet.getString(9);
    		ShippingAddress address = new ShippingAddress(addressId, accountId, recipientName, distinct, addressDetail, phone, email, is_default, addressLabel);
    		list.add(address);
    	}
    	return list;
    	
    }
}
