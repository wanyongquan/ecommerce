package com.ecommerce.control;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.entity.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginControlSeller", value = "/login_s")
public class LoginControlSeller extends HttpServlet {
    // 调用DAO类访问数据库
    AccountDao accountDao = new AccountDao();

    // 常量定义 - 区分用户类型
    private static final int USER_TYPE_SELLER = 1;   // 商家

    private Account getAccountCookie(HttpServletRequest request) {
        // 获取浏览器的Cookie列表
        Cookie[] cookies = request.getCookies();
        
        // 空值检查，避免空指针异常
        if (cookies == null) {
            return null;
        }

        Account account;
        String username = "";
        String password = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                username = cookie.getValue();
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
        }
        account = accountDao.checkLoginAccount_Seller(username, password);
        return account;
    }

    private void executeLogin(HttpServletRequest request, HttpServletResponse response, Account account) throws IOException {
        // 获取"记住我"复选框状态
        HttpSession session = request.getSession();
        boolean rememberMe = (request.getParameter("remember-me-checkbox") != null);

        // 存储商家账号信息和用户类型标识
        session.setAttribute("account", account);
        session.setAttribute("username", account.getUsername()); // 方便页面获取用户名
        session.setAttribute("isSeller", true); // 标记为商家
        session.setAttribute("userType", USER_TYPE_SELLER); // 存储用户类型

        if (rememberMe) {
            Cookie usernameCookie = new Cookie("username", account.getUsername());
            usernameCookie.setMaxAge(600);
            usernameCookie.setPath("/"); // 确保整个应用都能访问cookie
            response.addCookie(usernameCookie);

            Cookie passwordCookie = new Cookie("password", account.getPassword());
            passwordCookie.setMaxAge(600);
            passwordCookie.setPath("/");
            response.addCookie(passwordCookie);
            
            // 存储用户类型cookie，标记为商家
            Cookie userTypeCookie = new Cookie("userType", String.valueOf(USER_TYPE_SELLER));
            userTypeCookie.setMaxAge(600);
            userTypeCookie.setPath("/");
            response.addCookie(userTypeCookie);
        }
        
        // 核心修改：商家登录成功后跳转到商家首页 index_seller.jsp
        response.sendRedirect(request.getContextPath() + "/index_seller.jsp");
    }

    private void checkLoginAccountFirstTime(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        
        // 检查是否是用户输入错误的状态
        String status = "";
        if (request.getParameter("status") != null) {
            status = request.getParameter("status");
        }

        // 【删除登录类型相关逻辑】移除loginType参数的获取和验证
        // 原loginType相关代码已全部删除

        // 获取提交的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 空值检查（提示文本改为中文）
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            String alert = "<div class=\"alert alert-danger wrap-input100\">\n" +
                    "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                    "                            用户名和密码不能为空！\n" +
                    "                        </p>\n" +
                    "                    </div>";
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("login_seller.jsp").forward(request, response);
            return;
        }

        // 检查数据库中的账号
        Account account = accountDao.checkLoginAccount_Seller(username, password);
        if (account == null && status.equals("typed")) {
            // 登录错误提示（改为中文）
            String alert = "<div class=\"alert alert-danger wrap-input100\">\n" +
                    "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                    "                            商家账号或密码错误！\n" +
                    "                        </p>\n" +
                    "                    </div>";
            // 设置登录页面alert标签的属性
            request.setAttribute("alert", alert);
            // 重定向到登录页面
            request.getRequestDispatcher("login_seller.jsp").forward(request, response);
        } else if (account == null) {
            // 如果用户还未输入信息，跳转到登录页面
            request.getRequestDispatcher("login_seller.jsp").forward(request, response);
        } else {
            // 信息正确时执行登录
            executeLogin(request, response, account);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 检查账号的Cookie
        Account account = getAccountCookie(request);
        if (account == null) {
            // 检查是否是首次登录
            checkLoginAccountFirstTime(request, response);
        } else {
            // 如果存在账号Cookie，执行登录
            executeLogin(request, response, account);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}