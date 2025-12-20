package com.ecommerce.control;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.ShopDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Shop;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet(name = "ProfileControl", value = "/profile-page")
@MultipartConfig
public class ProfileControl extends HttpServlet {
    // Call DAO class to access with the database.
    AccountDao accountDao = new AccountDao();
    ShopDao shopDao = new ShopDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 HttpSession session = request.getSession();
         Account account = (Account) session.getAttribute("account");
         try {
	         Shop shop = shopDao.getAccountShop(account.getId());
	         request.setAttribute("account_shop", shop);
         }
         catch (SQLException e) {
             // 更详细的错误处理
             System.err.println("数据库操作失败:");
             System.err.println("SQL状态码: " + e.getSQLState());
             System.err.println("错误代码: " + e.getErrorCode());
             System.err.println("错误信息: " + e.getMessage());
         }
         catch(Exception e)
         {
         	 System.err.println("失败: " + e.getMessage());
         }
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile-page.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        int accountId = account.getId();
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Set default profile image for account.
        Part part = request.getPart("profile-image");
        InputStream inputStream = part.getInputStream();

        System.out.println(accountId + " " + firstName + " " + lastName + " " + address + " " + email + " " + phone);

        accountDao.editProfileInformation(accountId, firstName, lastName, address, email, phone, inputStream);
        response.sendRedirect(request.getContextPath() + "/profile-page.jsp");
    }
}
