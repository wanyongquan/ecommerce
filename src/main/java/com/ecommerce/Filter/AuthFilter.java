package com.ecommerce.Filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.Exception.AppException;
import com.ecommerce.entity.Account;

/**
 * Servlet Filter implementation class AuthFilter
 */
//@WebFilter("/*")

public class AuthFilter extends HttpFilter implements Filter {
	static String USER = "USER";
	static String SELLER = "SELLER";
			
	/** URL → 权限规则 */
	private static final Map<String, Set<String>> AUTH_RULES = new HashMap<>();
	 /** 角色 → 登录页 */
    private static final Map<String, String> ROLE_LOGIN_PAGE = new HashMap<>();

    /** 默认角色（多角色页面未登录时使用） */
    private static final String DEFAULT_ROLE = AuthFilter.USER;

    static {
    	 /* ========== 登录页配置（只配一次） ========== */
        ROLE_LOGIN_PAGE.put(AuthFilter.USER, "/login.jsp");
        ROLE_LOGIN_PAGE.put(AuthFilter.SELLER, "/login-seller.jsp");
        // 普通用户页面
        AUTH_RULES.put("/order-history", Set.of(AuthFilter.USER));
        AUTH_RULES.put("/order-detail", Set.of(AuthFilter.USER));
        AUTH_RULES.put("/checkout", Set.of(AuthFilter.USER));
        AUTH_RULES.put("/cart", Set.of(AuthFilter.USER));
        AUTH_RULES.put("/after-sales", Set.of(AuthFilter.USER));
        AUTH_RULES.put("/profile-page",  Set.of(AuthFilter.USER));
        AUTH_RULES.put("/after-sales-history",  Set.of(AuthFilter.USER));
        AUTH_RULES.put("/order-comment", Set.of(AuthFilter.USER));
        
        // 商家管理页面
        AUTH_RULES.put("/seller_home", Set.of(AuthFilter.SELLER));
        AUTH_RULES.put("/order-management-seller", Set.of(AuthFilter.SELLER));
        AUTH_RULES.put("/product-management", Set.of(AuthFilter.SELLER));
        AUTH_RULES.put("/shipping-order-detail", Set.of(AuthFilter.SELLER));
        AUTH_RULES.put("/after-sales-management", Set.of(AuthFilter.SELLER));
        AUTH_RULES.put("/profile-page-seller",  Set.of(AuthFilter.SELLER));
        AUTH_RULES.put("/shop-management",  Set.of(AuthFilter.SELLER));
        
        // 多角色共用页面
        
        AUTH_RULES.put("/recipient-addresses", Set.of(AuthFilter.USER, AuthFilter.SELLER));
    }
    
  
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
		String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());
        
        // 静态资源 & 登录页直接放行
        if (isPublicResource(uri)) {
            chain.doFilter(req, resp);
            return;
        }
        Set<String> rule = null;
        for(String key : AUTH_RULES.keySet()) {
            if(path.equals(key)) {
                rule = AUTH_RULES.get(key);
                break;
            }
        }
        // 不需要权限控制
        if (rule == null) {
            chain.doFilter(req, resp);
            return;
        }
        System.out.println("\n当前访问的路径：" + uri); 
        HttpSession session = req.getSession(false);
        Account account = (session == null)
                ? null
                : (Account) session.getAttribute("account");
        System.out.println("AUTH : " + " 检查是否已经登录：   "  + (account != null ? "已登录": "未登录" ));
        if (account == null 
        	|| !rule.contains(account.getRole().toUpperCase())) {
        	// 未登录 或 已登录但无权限
        	redirectToLogin(req, resp, rule);
            return;
        }
         
        // 放行
        chain.doFilter(req, resp);
           

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	
	/** 公共资源判断 */
    private boolean isPublicResource(String uri) {
        return uri.startsWith("/login")
                || uri.startsWith("/css/")
                || uri.startsWith("/js/")
                || uri.startsWith("/images/")
                || uri.equals("/test/index.jsp");
    }
    /** 根据角色配置跳转登录页 */
    private void redirectToLogin(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Set<String> rule) throws IOException {

    	// 保存原始请求的URL（包含查询参数）
        String targetUrl = request.getRequestURI();
        if (request.getQueryString() != null) {
            targetUrl += "?" + request.getQueryString();
        }
        
        // 保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("targetUrl", targetUrl);
        System.out.println(" 当前要访问的 URL：  " + targetUrl); 
    	// 取第一个角色
        String role = rule.iterator().next();
        String loginPage = ROLE_LOGIN_PAGE.get(role);
        
        System.out.println(" 要求先登录： " + loginPage); 
        if(loginPage == null) {
            loginPage = "/login.jsp"; // 默认兜底
        }

        response.sendRedirect(request.getContextPath() + loginPage);
    }
    
}
