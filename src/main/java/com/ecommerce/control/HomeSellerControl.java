package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.TopNProductSales;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomeSellerControl", value = "/seller_home")
public class HomeSellerControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();
    OrderDao orderDao = new OrderDao();
    Database dbManager = new Database();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8"); 
    	HttpSession session = request.getSession();
         Account account = (Account) session.getAttribute("account");
         // 检查当前用户是否卖家，如果不是， 转到 卖家登录页面； 
         System.out.println("Seller Home : " + " 检查是否已经登录 ，是否商家账号");
         if (account == null || account.getIsSeller() != 1) {
        	 request.getRequestDispatcher("login-seller.jsp").forward(request, response);
        	 return ;
         }
    	// get the amount of the all products; 
         Connection connection= null;
        try {
	        connection = dbManager.getConnection();
	    	int product_amount = productDao.getAmountOfProduct(connection, account.getId());
	    	
	    	int order_amount  = orderDao.getAmountOfAllOrders(connection, account.getId());
	    	int pending_order_amount = orderDao.getAmountOfPendingOrders(connection, account.getId());
	    	BigDecimal  sales_amount = orderDao.getSaleAmountForSeller(connection, account.getId());
	    	List<TopNProductSales> top_product_list = productDao.getTop5ProductList(connection, account.getId());
	    	request.setAttribute("product_amount", product_amount);
	    	request.setAttribute("order_amount", order_amount);
	    	request.setAttribute("pending_order_amount", pending_order_amount);
	    	request.setAttribute("total_sales_amount", sales_amount);
	    	request.setAttribute("top_product_list", top_product_list);
	    	
	    	getSalesTrend(request,account);
	    	
	    	request.setAttribute("dashboard_active", "active");
	       
	    	 
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
    	// Get all products published by this seller from database.

//        int sellerId = account.getId();
//        List<Product> productList = productDao.getSellerProducts(sellerId);
        // Get all categories from database.
//        List<Category> categoryList = categoryDao.getAllCategories();


//        request.setAttribute("product_list", productList);
//        request.setAttribute("category_list", categoryList);
        // Set attribute active class for home in header.
        request.setAttribute("home_active", "active");
        // Get request dispatcher and render to index page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/index_seller.jsp");
        requestDispatcher.forward(request, response);
    }
    
    public void getSalesTrend(HttpServletRequest request, Account account) throws SQLException {
    	
        // 1. 生成最近7天的完整日期列表（X轴）
        List<String> fullDateList = generateLast7Days();
        
        // 2. 查询数据库，获取有销售额的数据
        Connection connection = dbManager.getConnection();
        Map<String, BigDecimal> salesListFromDB = orderDao.getLastNSalesAmount(connection, account.getId());
        
        // 3. 准备Y轴数据列表，初始全部为0
        List<BigDecimal> salesAmountList = new ArrayList<>();
        
        // 4. 填充数据：如果数据库有该日期的数据就用，否则用0
        for (String date : fullDateList) {
            // getOrDefault是关键：有数据取数据，没数据取0.0
        	salesAmountList.add(salesListFromDB.getOrDefault(date, BigDecimal.ZERO));
        }
        // 5. 构建返回结果
        Gson gson = new Gson();
        String xAxisData = gson.toJson(fullDateList);
        String salesList = gson.toJson(salesAmountList);
        request.setAttribute("xAxisData", xAxisData);
        request.setAttribute("seriesData", salesList);
        
    }
    // 生成最近7天日期字符串的方法（格式：MM-dd）
    private List<String> generateLast7Days() {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        
        // 生成从6天前到今天的所有日期
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date()); // 重置为今天
            calendar.add(Calendar.DAY_OF_YEAR, -i); // 减去i天
            dateList.add(sdf.format(calendar.getTime()));
        }
        
        return dateList;
    }
}