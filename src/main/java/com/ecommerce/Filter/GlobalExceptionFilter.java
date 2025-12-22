package com.ecommerce.Filter;

import java.io.IOException;
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

import com.ecommerce.Exception.AppException;

/**
 * Servlet Filter implementation class GlobalExceptionFilter
 */
@WebFilter("/*")
public class GlobalExceptionFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public GlobalExceptionFilter() {
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

		// pass the request along the filter chain
		try {
		chain.doFilter(request, response);
		}
		catch(AppException ex) {
			// 1. 记录完整日志（开发者）
            ex.printStackTrace();

            // 2. 给用户友好提示
            
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

         // 设置友好的用户消息
            String userMessage = getFriendlyMessage(ex);
            req.setAttribute("errorMessage", userMessage);
            req.setAttribute("errorCode", ex.getErrorCode());
            req.setAttribute("errorType", ex.getErrorCode());
            
            req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
		catch (Exception ex) {

            // 兜底：未知异常
            ex.printStackTrace();

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            req.setAttribute("errorMessage", "系统发生未知错误，请联系管理员");
            resp.setStatus(500);

            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	
    private String getFriendlyMessage(Exception e) {
        if (e instanceof AppException) {
            return ((AppException) e).getUserMessage();
        }
        
        // 根据不同异常类型返回不同的友好消息
        if (e instanceof javax.servlet.ServletException) {
            return "服务器处理请求时出错，请稍后再试。";
        } else if (e instanceof java.sql.SQLException) {
            return "数据库操作失败，请检查数据或联系管理员。";
        } else if (e instanceof java.lang.NullPointerException) {
            return "系统内部错误，缺少必要的数据。";
        } else if (e instanceof java.lang.NumberFormatException) {
            return "数据格式不正确，请检查输入。";
        } else {
            return "系统出现未知错误，请稍后再试。";
        }
    }
    
}
