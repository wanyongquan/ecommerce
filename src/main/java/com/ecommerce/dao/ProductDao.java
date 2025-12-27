package com.ecommerce.dao;

import com.ecommerce.Exception.AppException;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.AfterSalesService;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Category;

import com.ecommerce.entity.ColorStock;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductComment;
import com.ecommerce.entity.TopNProductSales;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ProductDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Call DAO class to access other entities' database.
    AccountDao accountDao = new AccountDao();
    CategoryDao categoryDao = new CategoryDao();

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        List<Product> list = productDao.getSellerProducts(1);
        for (Product product : list) {
            System.out.println(product.toString());
        }
    }

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

    // Method to execute query to get list products.
    private List<Product> getListProductQuery(String query) {
        List<Product> list = new ArrayList<>();
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
//                double price = resultSet.getDouble(4);
//                String description = resultSet.getString(5);
//                Category category = categoryDao.getCategory(resultSet.getInt(6));
//                Account account = accountDao.getAccount(resultSet.getInt(7));
//                boolean isDelete = resultSet.getBoolean(8);
//                int amount = resultSet.getInt(9);
//
//                // Get base64 image to display.
//                Blob blob = resultSet.getBlob(3);
//                String base64Image = getBase64Image(blob);
            	Product product = new Product();
            	product.setId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getDouble("product_price"));
                product.setDescription(resultSet.getString("product_description"));
                product.setDeleted(resultSet.getBoolean("product_is_deleted"));
                product.setAmount(resultSet.getInt("product_amount"));
                
                Blob blob = resultSet.getBlob("product_image");
                product.setBase64Image(getBase64Image(blob));

                product.setCategory(
                    categoryDao.getCategory(resultSet.getInt("fk_category_id"))
                );
                product.setAccount(
                    accountDao.getAccount(resultSet.getInt("fk_account_id"))
                );
             // ====== 关键：销量字段 ======
                try {
                    product.setSalesAmount(resultSet.getInt("sales"));
                } catch (SQLException ignore) {
                    product.setSalesAmount(0);
                }

                list.add(product);
                
//                list.add(new Product(id, name, base64Image, price, description, category, account, isDelete, amount));
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    // Method to execute query to get list products.
    private List<Product> getListofFullProductQuery(String query) {
        List<Product> list = new ArrayList<>();
        Map<Integer, Product> productMap = new LinkedHashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                
                Product product = productMap.get(id);
                // 判断产品是否已创建
                if (product == null ) {                	
	                String name = resultSet.getString(2);
	                double price = resultSet.getDouble(4);
	                String description = resultSet.getString(5);
	                Category category = categoryDao.getCategory(resultSet.getInt(6));
	                Account account = accountDao.getAccount(resultSet.getInt(7));
	                boolean isDelete = resultSet.getBoolean(8);
	                int amount = resultSet.getInt(9);
	
	                // Get base64 image to display.
	                Blob blob = resultSet.getBlob(3);
	                String base64Image = getBase64Image(blob);
	                
	                product = new Product(id, name, base64Image, price, description, category, account, isDelete, amount);
	                productMap.put(id, product);
	                list.add(product);
                }
                // 2 每一行只创建一个color_amount; 
//                、、int colorId = resultSet.getInt("product_id");
                ColorStock clrStock = new ColorStock();
                clrStock.setProductId(id);
                clrStock.setColorName(resultSet.getString("color_name"));
                clrStock.setAmount(resultSet.getInt("stock_amount"));
                product.get_ColorStocks().add(clrStock);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    

    // Method to get all products from database.
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM product WHERE product_is_deleted = false";
        return getListProductQuery(query);
    }


    // Method to get all products from database.
    public List<Product> getAllFullProducts() {
        String query = "SELECT * FROM product left join product_color_amount on product.product_id = product_color_amount.product_id " + 
        			" WHERE  product_is_deleted = false";
        return getListofFullProductQuery(query);
    }


    // Method to get a product by its id from database.
    public Product getProduct(int productId) {
        Product product = new Product();
        String query = "SELECT * FROM product WHERE product_id = " + productId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setBase64Image(getBase64Image(resultSet.getBlob(3)));
                product.setPrice(resultSet.getDouble(4));
                product.setDescription(resultSet.getString(5));
                product.setCategory(categoryDao.getCategory(resultSet.getInt(6)));
                product.setAccount(accountDao.getAccount(resultSet.getInt(7)));
                product.setDeleted(resultSet.getBoolean(8));
                product.setAmount(resultSet.getInt(9));
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    // Method to get a product by its id from database.
    public List<ColorStock> getColorStockByProductId(int productId) {
//        Product product = new Product();
        String query = "SELECT * from product_color_amount " +
        			" WHERE product_id = " + productId;
        List<ColorStock> colorList = new ArrayList<ColorStock>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	ColorStock colorEntity = new ColorStock();
            	colorEntity.setProductId(resultSet.getInt(2));
            	colorEntity.setColorName(resultSet.getString(3));
            	colorEntity.setAmount(resultSet.getInt(4));
            	colorList.add(colorEntity);
            }
        } catch (SQLException | ClassNotFoundException  e) {
            System.out.println(e.getMessage());
        }
        return colorList;
    }


    // Method to get a categories by its id from database.
    public List<Product> getAllCategoryProducts(int category_id) {
        String query = "SELECT * FROM product WHERE fk_category_id = " + category_id + " AND product_is_deleted = false";
        return getListProductQuery(query);
    }

    // Method to search a product by a keyword.
    public List<Product> searchProduct(String keyword) {
        String query = "SELECT * FROM product WHERE product_name like '%" + keyword + "%' AND product_is_deleted = false";
        return getListProductQuery(query);
    }

    // Method to get all products of a seller.
    public List<Product> getSellerProducts(int sellerId) {
        String query = "SELECT * FROM product WHERE fk_account_id = " + sellerId;
        return getListProductQuery(query);
    }

    // Method to remove a product from database by its id.
    public void removeProduct(Product product) {
        // Get id of the product.
        int productId = product.getId();

        String query = "UPDATE product SET product_is_deleted = true WHERE product_id = " + productId;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to add product to database.

    public int addProduct(String productName, InputStream productImage, double productPrice, String productDescription, int productCategory, int sellerId, int productAmount) {
        String query = "INSERT INTO product (product_name, product_image, product_price, product_description, fk_category_id, fk_account_id, product_is_deleted, product_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1; // 默认返回-1表示插入失败
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = new Database().getConnection();
//            preparedStatement = connection.prepareStatement(query);
         // 关键修改：使用RETURN_GENERATED_KEYS参数
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, productName);
            preparedStatement.setBinaryStream(2, productImage);
            preparedStatement.setDouble(3, productPrice);
            preparedStatement.setString(4, productDescription);
            preparedStatement.setInt(5, productCategory);
            preparedStatement.setInt(6, sellerId);
            preparedStatement.setBoolean(7, false);
            preparedStatement.setInt(8, productAmount);

            int affectedRows = preparedStatement.executeUpdate();
         // 如果插入成功，获取生成的ID
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return generatedId;
    }
    
    public int addProductAndReturnID(String productName, InputStream productImage, double productPrice, 
            String productDescription, int productCategory, int sellerId, int productAmount) {
    	return addProduct(productName, productImage, productPrice, productDescription, 
                productCategory, sellerId, productAmount);
    }
    

    // Method to edit product in database.
    public void editProduct(int productId, String productName, InputStream productImage, double productPrice, String productDescription, int productCategory, int productAmount) {
        String query = "UPDATE product SET product_name = ?, product_image = ?, product_price = ?, product_description = ?, fk_category_id = ?, product_amount = ? WHERE product_id = ?";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setBinaryStream(2, productImage);
            preparedStatement.setDouble(3, productPrice);
            preparedStatement.setString(4, productDescription);
            preparedStatement.setInt(5, productCategory);
            preparedStatement.setInt(6, productId);
            preparedStatement.setInt(7, productAmount);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get 12 products to display on each page.
    public List<Product> get12ProductsOfPage(int index, String sort_method) {
    	
        StringBuilder sb_query = new StringBuilder( ""); 
        sb_query.append("  SELECT  "
                + "            p.product_id, "
                + "            p.product_name, "
                + "            p.product_image, "
                + "            p.product_price, "
                + "            p.product_description, "
                + "            p.fk_category_id, "
                + "            p.fk_account_id, "
                + "            p.product_is_deleted, "
                + "            p.product_amount, "
                + "            p.product_created_date, "
                + "            IFNULL(SUM(od.product_quantity), 0) AS sales "
                + "        FROM product p "
                + "        LEFT JOIN order_detail od  "
                + "            ON od.fk_product_id = p.product_id "
                + "        WHERE p.product_is_deleted = false ");

            sb_query.append(" GROUP BY p.product_id ");

            switch (sort_method) {
            case "time_asc":
            	sb_query.append(" ORDER BY p.product_created_date ASC ");
                break;
            case "time_desc":
            	sb_query.append(" ORDER BY p.product_created_date DESC ");
                break;
            case "sales_asc":
            	sb_query.append(" ORDER BY sales ASC ");
                break;
            case "sales_desc":
            	sb_query.append(" ORDER BY sales DESC ");
                break;
            case "price_asc":
            	sb_query.append(" ORDER BY p.product_price ASC ");
                break;
            case "price_desc":
            	sb_query.append(" ORDER BY p.product_price DESC ");
                break;
            default:
            	sb_query.append(" ORDER BY p.product_id DESC ");
        }

            sb_query.append(" LIMIT " + ((index - 1) * 12) + ", 12");

       String query = sb_query.toString();
        return getListProductQuery(query);
    }

    // Method to get total products in database.
    public int getTotalNumberOfProducts() {
        int totalProduct = 0;
        String query = "SELECT COUNT(*) FROM product WHERE product_is_deleted = false";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalProduct = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return totalProduct;
    }

    // Method to decrease new amount of products.
    public void decreaseProductAmount(int productId, int productAmount) {
        String query = "UPDATE product SET product_amount = product_amount - ? WHERE product_id = ?";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productAmount);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /************  12.17 ************/
    // 在ProductDao类中添加以下方法
    public void addProductColors(int productId, Map<String, Integer> colorStocks) {
        String query = "INSERT INTO product_color_amount (product_id, color_name, stock_amount) VALUES (?, ?, ?)";
        
        try (Connection conn = new Database().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            for (Map.Entry<String, Integer> entry : colorStocks.entrySet()) {
                ps.setInt(1, productId);
                ps.setString(2, entry.getKey());
                ps.setInt(3, entry.getValue());
                ps.addBatch();
            }
            
            ps.executeBatch();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    //  返回指定商家的在售商品总数
    public int getAmountOfProduct(Connection connection, int account_id) throws SQLException {
    	String query = "select count(*) from product where fk_account_id = ? and product_is_deleted = false ";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, account_id);
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		return resultSet.getInt(1);
    	}
    	return 0;
    }
    
 // 返回全部的TOP 5 商品
    public List<TopNProductSales> getTop5SalesProductList(Connection connection) throws SQLException, IOException {
    	String query = "SELECT "
    			+ "    p.product_id, "
    			+ "    p.product_name,  p.product_price,  p.product_image, "
    			+ "    SUM(od.product_quantity) AS total_sales "
    			+ "FROM order_detail od "
    			+ "JOIN `order` o "
    			+ "    ON od.fk_order_id = o.order_id "
    			+ "JOIN product p "
    			+ "    ON od.fk_product_id = p.product_id "
    			+ "WHERE "
    			+ "     o.order_status = '1'        "
    			+ "    AND o.order_date_create >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) "
    			+ "GROUP BY "
    			+ "    p.product_id "
    			//+ "    p.product_name, p.product_image, p.product_price "
    			+ "ORDER BY "
    			+ "    total_sales DESC "
    			+ "LIMIT 5; ";
    	
    	 
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	
    	resultSet = pstmt.executeQuery();
    	
    	 List<TopNProductSales> list = new ArrayList<TopNProductSales>();
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		TopNProductSales top5Product =  new TopNProductSales();
    		int productId =  resultSet.getInt(1);
    		String productName = resultSet.getString(2);
    		double price = resultSet.getDouble(3);
    		int salesAmount = resultSet.getInt(5);
    		
    		top5Product.setProductId(productId);
    		top5Product.setProductName(productName);
    		top5Product.setPrice(price);
    		 Blob blob = resultSet.getBlob("product_image");
    		 top5Product.setBase64Image(getBase64Image(blob));
    		 
    		top5Product.setSalesAmount(salesAmount);
    		list.add(top5Product);
    	}
    	return list;
    }
    
 // 返回指定商家的TOP 5 商品
    public List<TopNProductSales> getTop5ProductList(Connection connection, int account_id) throws SQLException, IOException {
    	String query = "SELECT "
    			+ "    p.product_id, "
    			+ "    p.product_name,  p.product_price,  p.product_image, "
    			+ "    SUM(od.product_quantity) AS total_sales "
    			+ "FROM order_detail od "
    			+ "JOIN `order` o "
    			+ "    ON od.fk_order_id = o.order_id "
    			+ "JOIN product p "
    			+ "    ON od.fk_product_id = p.product_id "
    			+ "WHERE "
    			+ "    p.fk_account_id = ?             "
    			+ "    AND o.order_status = '1'        "
    			+ "    AND o.order_date_create >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) "
    			+ "GROUP BY "
    			+ "    p.product_id "
    			//+ "    p.product_name, p.product_image, p.product_price "
    			+ "ORDER BY "
    			+ "    total_sales DESC "
    			+ "LIMIT 5; ";
    	
    	 
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, account_id);
    	resultSet = pstmt.executeQuery();
    	
    	 List<TopNProductSales> list = new ArrayList<TopNProductSales>();
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		TopNProductSales top5Product =  new TopNProductSales();
    		int productId =  resultSet.getInt(1);
    		String productName = resultSet.getString(2);
    		double price = resultSet.getDouble(3);
    		int salesAmount = resultSet.getInt(5);
    		
    		top5Product.setProductId(productId);
    		top5Product.setProductName(productName);
    		top5Product.setPrice(price);
    		 Blob blob = resultSet.getBlob("product_image");
    		 top5Product.setBase64Image(getBase64Image(blob));
    		 
    		top5Product.setSalesAmount(salesAmount);
    		list.add(top5Product);
    	}
    	return list;
    }
    
    // 创建评论
    public void createComment(Connection connection, int orderId, int productId, int userId, String comment, int rating) throws SQLException {
      
    	String query =" INSERT INTO  product_comment (fk_order_id, fk_product_id, fk_user_id, rating, comment_content) "
    			+  " values( ?, ?, ?, ? ,?)";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, orderId);
    	pstmt.setInt(2, productId);
    	pstmt.setInt(3, userId);
    	pstmt.setInt(4, rating);
    	pstmt.setString(5, comment);
   	
    	pstmt.executeUpdate();
    	
    }
    
    // 查询评论
    public ProductComment getProductComment(Connection connection, int orderId, int productId, int userId ) throws SQLException {
    	String query = "SELECT * from product_comment where fk_order_id =? and fk_product_id = ? and fk_user_id = ? ";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, orderId);
    	pstmt.setInt(2, productId);
    	pstmt.setInt(3, userId);
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		String comment_content = resultSet.getString("comment_content");
    		int commentId = resultSet.getInt("comment_id");
    		int rating = resultSet.getInt("rating");
    		Date date_comment = resultSet.getDate("comment_time");
    		
    		ProductComment comment =  new  ProductComment();
    		comment.setCommentId(commentId);
    		comment.setOrderId(orderId);
    		comment.setProductId(productId);
    		comment.setUserId(userId);
    		comment.setComment(comment_content);
    		comment.setRating(rating);
    		comment.setCreatedTime(date_comment);
    		return comment;
    	}
    	return null;
    }
    
    // 查询指定产品的评论的列表
    public List<ProductComment> getProductCommentList(Connection connection, int productId ) throws SQLException{
    	String query = "SELECT *  FROM  product_comment oc " + 
    				" join product p on oc.fk_product_id = p.product_id  " +
    				" join ACCOUNT ac on ac.account_id = oc.fk_user_id " +
    				" where oc.fk_product_id = ?";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, productId);
    	resultSet  = pstmt.executeQuery();
    	List<ProductComment>  commentList = new ArrayList<ProductComment>();
    	while (resultSet.next()) {
    		String comment_content = resultSet.getString("comment_content");
    		int commentId = resultSet.getInt("comment_id");
    		int orderId = resultSet.getInt("fk_order_id");
    		int userId = resultSet.getInt("fk_user_id");
    		int rating = resultSet.getInt("rating");
    		Date date_comment = resultSet.getDate("comment_time");
    		String username = resultSet.getString("account_first_name");
    		
    		ProductComment comment =  new  ProductComment();
    		comment.setCommentId(commentId);
    		comment.setOrderId(orderId);
    		comment.setProductId(productId);
    		comment.setUserId(userId);
    		comment.setComment(comment_content);
    		comment.setRating(rating);
    		comment.setCreatedTime(date_comment);
    		comment.setUserName(username);
    		commentList.add(comment);
    	}
    	return commentList;
    }
    
    // 创建售后申请；
    public void createAfterSalesApply(Connection connection, int orderId, int productId, int apply_account_id, int serviceType, String reason, String description) throws SQLException{
    	String query = "INSERT INTO  after_sales_apply (fk_order_id, fk_product_id, apply_account_id, service_type, reason, service_description )" 
    			+ " VALUES (?, ?, ?, 0, ?, ?)";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, orderId);
    	pstmt.setInt(2, productId);
    	pstmt.setInt(3, apply_account_id);
    	pstmt.setString(4, reason);
    	pstmt.setString(5, description);
    	
    	pstmt.executeUpdate();
    }
    
    public CartProduct getOrderDetail(Connection connection, int orderId, int productId) throws SQLException, IOException{
    	String query = "SELECT * FROM shop.order_detail  od, product p where od.fk_product_id = p.product_id "
    			+ " and od.fk_order_id= ? and od.fk_product_id = ?";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, orderId);
    	pstmt.setInt(2, productId);
    	
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		int quantity = resultSet.getInt("product_quantity");
            double price = resultSet.getDouble("product_price");

            String color = resultSet.getString("product_color");
            
            Product product = new Product();
            product.setId(resultSet.getInt("product_id"));
            product.setName(resultSet.getString("product_name"));
            product.setPrice(resultSet.getDouble("product_price"));
            product.setDescription(resultSet.getString("product_description"));
            product.setDeleted(resultSet.getBoolean("product_is_deleted"));
            product.setAmount(resultSet.getInt("product_amount"));
            Blob blob = resultSet.getBlob("product_image");
            product.setBase64Image(getBase64Image(blob));
            
    		CartProduct cartProduct = new CartProduct(product, quantity, price, color);
    		return cartProduct;
    	}
    	return null;
    }
    
    // 查询是否存在售后申请；
    public boolean checkAfterSalesApplyExists(Connection connection, int orderId, int productId) throws SQLException{
    	String query = "SELECT * FROM  after_sales_apply WHERE fk_order_id = ? and  fk_product_id = ?";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, orderId);
    	pstmt.setInt(2, productId);
    	
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		return true;
    	}
    	return false;
    }
    
    // 查询买家的售后申请历史
    public List<AfterSalesService> getAfterSalesServiceHistory(Connection connection, int apply_account_id) throws SQLException{
    	String query = "SELECT * FROM  after_sales_apply asa , product p  WHERE asa.fk_product_id = p.product_id and apply_account_id = ? order by service_status";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, apply_account_id);
        	
    	List<AfterSalesService>  list= new ArrayList<>();
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		int id = resultSet.getInt("after_sales_apply_id");
    		int orderId = resultSet.getInt("fk_order_id");
    		int productId = resultSet.getInt("fk_product_id");
    		String productName = resultSet.getString("product_name");
    		String reason = resultSet.getString("reason");
    		String serviceDescription = resultSet.getString("service_description");
    		Date  date_created = resultSet.getDate("date_created");
    		int status = resultSet.getInt("service_status");
    		AfterSalesService afterSales = new AfterSalesService();
    		afterSales.setServiceId(id);
    		afterSales.setOrderId(orderId);
    		afterSales.setProductId(productId);
    		afterSales.setApply_accont_id(apply_account_id);
    		afterSales.setProductName(productName);
    		afterSales.setReason(reason);
    		afterSales.setServiceDescription(serviceDescription);
    		afterSales.setAppliedDate(date_created);
    		afterSales.setStatus(status);
    		
    		list.add(afterSales);
    	}
    	return list;
    }
    
    // 查询卖家的售后服务历史；
    public List<AfterSalesService> getAfterSalesServiceHistoryForSeller(Connection connection, int seller_account_id) throws SQLException{
    	String query = "SELECT * FROM  after_sales_apply asa , product p , account a "
    			+ " WHERE asa.fk_product_id = p.product_id and "
    			+ " p.fk_account_id = a.account_id "
    			+ " and a.account_id = ? "
    			+ " order by service_status" ;
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, seller_account_id);
        	
    	List<AfterSalesService>  list= new ArrayList<>();
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		int id = resultSet.getInt("after_sales_apply_id");
    		int orderId = resultSet.getInt("fk_order_id");
    		int productId = resultSet.getInt("fk_product_id");
    		int apply_accountId = resultSet.getInt("apply_account_id");
    		String productName = resultSet.getString("product_name");
    		String reason = resultSet.getString("reason");
    		String serviceDescription = resultSet.getString("service_description");
    		Date  date_created = resultSet.getDate("date_created");
    		int status = resultSet.getInt("service_status");
    		AfterSalesService afterSales = new AfterSalesService();
    		afterSales.setServiceId(id);
    		afterSales.setOrderId(orderId);
    		afterSales.setProductId(productId);
    		afterSales.setApply_accont_id(apply_accountId);
    		afterSales.setProductName(productName);
    		afterSales.setReason(reason);
    		afterSales.setServiceDescription(serviceDescription);
    		afterSales.setAppliedDate(date_created);
    		afterSales.setStatus(status);
    		
    		list.add(afterSales);
    	}
    	return list;
    }
    
    // 拒绝退款；
    public void rejectRefund(Connection connection, int serviceId) throws SQLException {
    	String query = "UPDATE after_sales_apply set service_status = 2 where after_sales_apply_id = ?";
    	
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, serviceId);
    	
    	pstmt.executeUpdate();
    }
  
    // 统一退款
    public void approveRefund(Connection connection, int serviceId, int orderId, int productId,  int seller_account_id) throws SQLException {
    	// 查询 商品当初的售价； 
    	BigDecimal product_price = getProductPrice(connection, orderId, productId);
    	
    	// Step 1.  order_detail 表 ，更新商品的状态为 已退款；
    	
    	// step 2.  order 表， order_total 中扣除 该商品的售价； 
    	
    	updateOrderTotal(connection, orderId, product_price);
    	// step 3.  account 表， 给卖家 扣除 售价， 买家 加上 售价; 
    	refund(connection, 0, seller_account_id, 10.0);
    	// step 4. after_sales表， 设置状态为已退款；
    	updateAfterSalesStatus(connection, serviceId, AfterSalesApplyStatus.APPROVED);
    }
    
    public BigDecimal getProductPrice(Connection connection, int orderId, int productId) throws SQLException{
    	String query = "select product_price from order_detail where fk_order_id = ? and fk_product_id = ? ";
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, orderId);
    	pstmt.setInt(2, productId);
    	resultSet = pstmt.executeQuery();
    	while (resultSet.next()) {
    		return resultSet.getBigDecimal("product_price");
    	}
    	return BigDecimal.ZERO;
    }
    
    public void  updateOrderTotal(Connection connection, int orderId, BigDecimal amount) throws SQLException{
    	String query = "UPDATE shop.order set order_total = order_total - ? where order_id = ?";
    	
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setBigDecimal(1, amount);
    	pstmt.setInt(2, orderId);
    	
    	pstmt.executeUpdate();
    }
    
    public void  updateAfterSalesStatus(Connection connection, int serviceId, AfterSalesApplyStatus service_status) throws SQLException{
    	String query = "UPDATE after_sales_apply set service_status = ? where after_sales_apply_id = ?";
    	
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setInt(1, service_status.getValue());
    	pstmt.setInt(2, serviceId);
    	
    	pstmt.executeUpdate();
    }
    
    public void refund(Connection connection, int buyer_account_id, int seller_account_id, double balance) throws SQLException {
    	
        // 卖家 扣款
    	String deduct_query = "UPDATE ACCOUNT SET account_balance = account_balance - ?  where account_id = ? ;" ;
    	
    	 PreparedStatement deduct_pstmt = connection.prepareStatement(deduct_query) ;
    	 deduct_pstmt.setDouble(1, balance);
    	 deduct_pstmt.setInt(2, seller_account_id);
    	 deduct_pstmt.executeUpdate();
    	 //  买家 加款
    	 String add_query = "UPDATE ACCOUNT SET account_balance = account_balance + ? where account_id = ?";
    	 PreparedStatement add_pstmt = connection.prepareStatement(add_query) ;
    	 add_pstmt.setDouble(1, balance);
    	 add_pstmt.setInt(2, buyer_account_id);
    	 add_pstmt.executeUpdate();
    	 
    }
    
    public enum AfterSalesApplyStatus {
        APPLIED(0),   // 已申请
        APPROVED(1),   // 已退款
        REJECTED(2);  // 拒绝退款

        private final int value;

        AfterSalesApplyStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
