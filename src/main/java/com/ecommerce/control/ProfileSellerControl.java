package com.ecommerce.control;

import com.ecommerce.Exception.AppException;
import com.ecommerce.dao.AccountDao;
import com.ecommerce.dao.ShopDao;
import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Shop;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "ProfileSellerControl", value = "/profile-page-seller")
@MultipartConfig
public class ProfileSellerControl extends HttpServlet {
	// Call DAO class to access with the database.
	AccountDao accountDao = new AccountDao();
	ShopDao shopDao = new ShopDao();
	Database dbManager = new Database();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置请求编码，避免中文乱码
		request.setCharacterEncoding("UTF-8"); 
		HttpSession session = request.getSession();
		String alert = (String) session.getAttribute("alert");
		if (alert != null) {
			request.setAttribute("alert", alert);
			session.removeAttribute("alert"); // 关键：只显示一次
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/profile-page-seller.jsp");
		requestDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// 设置请求编码，避免中文乱码
		request.setCharacterEncoding("UTF-8");
		Account account = (Account) session.getAttribute("account");
		String updateType = request.getParameter("updateType");
		Connection connection = null;
		int accountId = account.getId();
		if ("info".equals(updateType)) {
			String firstName = request.getParameter("first-name");

			String lastName = request.getParameter("last-name");
			String address = request.getParameter("address");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");

			

			System.out.println(accountId + " " + firstName + " " + lastName + " " + address + " " + email + " " + phone);

			try {
				connection = dbManager.getConnection();

				accountDao.editProfileInformation(connection, accountId, firstName, lastName, address, email, phone);
				// 更新session中的Account的value
				Account account_new = accountDao.getAccount(accountId);
				session.setAttribute("account", account_new);
				String alert = "<div class=\"alert alert-success wrap-input100\">\n" +
						"                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
						"                            个人信息成功保存!\n" +
						"                        </p>\n" +
						"                    </div>";
				session.setAttribute("alert", alert);	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if ("avatar".equals(updateType)) {
			// 只处理头像（只读 Part）
			// Set default profile image for account.
			Part part = request.getPart("profile-image");
			InputStream inputStream = part.getInputStream();

			try {
				connection = dbManager.getConnection();

				accountDao.editProfileAvatar(connection, accountId, inputStream);
				// 更新session中的Account的value
				Account account_new = accountDao.getAccount(accountId);
				session.setAttribute("account", account_new);
				String alert = "<div class=\"alert alert-success wrap-input100\">\n" +
						"                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
						"                            头像成功保存!\n" +
						"                        </p>\n" +
						"                    </div>";
				session.setAttribute("alert", alert);		 
			} catch (Exception e) {

				throw new AppException("数据库操作异常",e); // 继续向上抛
			}finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException ignored) {}
				}
			}
		}

		response.sendRedirect(request.getContextPath() + "/profile-page-seller");
	}
}
