package com.ecommerce.control;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.entity.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginControl", value = "/login")
public class LoginControl extends HttpServlet {
    // 调用DAO类访问数据库
    AccountDao accountDao = new AccountDao();

    /**
     * 从浏览器Cookie中获取账号信息
     * @param request 请求对象
     * @return 账号实体对象（无则返回null）
     */
    private Account getAccountCookie(HttpServletRequest request) {
        // Get list cookies of the browser.
        Cookie[] cookies = request.getCookies();

        Account account;
        String username = "";
        String password = "";

        
        // 遍历Cookie，提取用户名和密码

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                username = cookie.getValue();
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
        }

        
        // 验证Cookie中的账号信息

        account = accountDao.checkLoginAccount(username, password);
        return account;
    }


    /**
     * 执行登录逻辑（存储Session、设置Cookie）
     * @param request 请求对象
     * @param response 响应对象
     * @param account 验证通过的账号实体
     * @throws IOException IO异常
     */
    private void executeLogin(HttpServletRequest request, HttpServletResponse response, Account account) throws IOException {
        // 获取"记住我"复选框的状态
        HttpSession session = request.getSession();
        boolean rememberMe = (request.getParameter("remember-me-checkbox") != null);

        // 将账号信息存入Session
        session.setAttribute("account", account);
        
        // 如果勾选"记住我"，设置Cookie（有效期10分钟）

        if (rememberMe) {
            Cookie usernameCookie = new Cookie("username", account.getUsername());
            usernameCookie.setMaxAge(600);
            response.addCookie(usernameCookie);

            Cookie passwordCookie = new Cookie("password", account.getPassword());
            passwordCookie.setMaxAge(600);
            response.addCookie(passwordCookie);
        }

        // 3. 获取目标URL（用户在登录前想访问的页面）
        String targetUrl = (String) session.getAttribute("targetUrl");
        
        // 4. 移除session中的目标URL（避免重复使用）
        session.removeAttribute("targetUrl");
        if (targetUrl != null && !targetUrl.isEmpty()) {
        	response.sendRedirect(targetUrl);
        }
        else {
          // 登录成功后跳转到首页
          response.sendRedirect(request.getContextPath() + "");
        }

    }

    /**
     * 首次登录验证（无Cookie时）
     * @param request 请求对象
     * @param response 响应对象
     * @throws ServletException  Servlet异常
     * @throws IOException        IO异常
     */
    private void checkLoginAccountFirstTime(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        
        // 检查是否为用户输入错误的状态标识
        String status = "";
        if (request.getParameter("status") != null) {
            status = request.getParameter("status");
        }
        
        // 获取用户提交的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 验证数据库中的账号信息
        Account account = accountDao.checkLoginAccount(username, password);
        
        // 账号密码错误且有错误状态标识时，返回登录页并提示
        if (account == null && status.equals("typed")) {
            // 登录失败的提示信息（中文）
            String alert = "<div class=\"alert alert-danger wrap-input100\">\n" +
                    "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                    "                            用户名或密码错误！\n" +
                    "                        </p>\n" +
                    "                    </div>";
            
            // 将提示信息存入请求属性，供登录页展示
            request.setAttribute("alert", alert);
            // 转发回登录页面
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (account == null) {
            // 无账号信息时，直接返回登录页
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // 账号验证通过，执行登录逻辑
            executeLogin(request, response, account);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置响应编码，避免中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 先尝试从Cookie中获取并验证账号
        Account account = getAccountCookie(request);
        
        if (account == null) {
            // Cookie验证失败，执行首次登录验证
            checkLoginAccountFirstTime(request, response);
        } else {
            // Cookie验证成功，直接执行登录
		}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
        
        System.out.println("  login doGet() ");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

}


