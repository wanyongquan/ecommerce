package com.ecommerce.control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutControl", value = "/logout")
public class LogoutControl extends HttpServlet {
    
    // 常量定义 - 区分用户类型
    private static final int USER_TYPE_CUSTOMER = 0; // 普通用户
    private static final int USER_TYPE_SELLER = 1;   // 商家

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前会话中的用户类型（用于登出后跳转）
        Integer userType = (Integer) request.getSession().getAttribute("userType");
        
        // 销毁整个session（更彻底的方式），而不仅仅是移除account属性
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 销毁session，清除所有session属性
        }

        // 清除所有相关cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) { // 空值检查，避免空指针异常
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                // 清除用户名、密码、用户类型cookie
                if (cookieName.equals("username") || cookieName.equals("password") || cookieName.equals("userType")) {
                    cookie.setMaxAge(0); // 设置为0表示删除cookie
                    cookie.setPath("/"); // 确保整个应用域都能清除
                    response.addCookie(cookie);
                }
            }
        }

        // 根据用户类型跳转到不同的页面（可选）
        // 如果需要登出后根据原身份跳转不同页面，取消下面注释
        /*
        if (userType != null) {
            if (userType == USER_TYPE_SELLER) {
                // 商家登出后跳转到商家登录页或首页
                response.sendRedirect(request.getContextPath() + "/login.jsp?type=seller");
            } else {
                // 普通用户登出后跳转到首页
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            // 默认跳转到首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        */
        
        // 简单版本：所有用户登出后都跳转到首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 支持POST请求登出（比如通过表单提交）
        doGet(request, response);
    }
}