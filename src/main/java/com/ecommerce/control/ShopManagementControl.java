package com.ecommerce.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.ecommerce.dao.ShopDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Shop;

@WebServlet(name="shopManagementControl", value="/shop-management")
public class ShopManagementControl  extends HttpServlet{
	

	ShopDao shopDao = new ShopDao();

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8"); 
    	HttpSession session = request.getSession();
         Account account = (Account) session.getAttribute("account");
         String alertMsg = (String) session.getAttribute("alertMsg");
         String alertType = (String) session.getAttribute("alertType");
         if (alertMsg != null) {
        	    request.setAttribute("alertMsg", alertMsg);
        	    request.setAttribute("alertType", alertType);

        	    session.removeAttribute("alertMsg");
        	    session.removeAttribute("alertType");
        	}
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
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/shop-profile-page.jsp");
        requestDispatcher.forward(request, response);
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		String shopName = request.getParameter("shop_name");
		String shopDescription = request.getParameter("shop_description");
		
		// 3. 参数校验
        if (shopName == null || shopName.trim().isEmpty()) {
        	session.setAttribute("alertMsg", "店铺名称不能为空");
        	session.setAttribute("alertType", "danger");
		    
            response.sendRedirect(request.getContextPath() + "/shop-management");
            return;
        }
        
        shopName = shopName.trim();
        if (shopDescription != null) {
            shopDescription = shopDescription.trim();
        }
        
		try {			
			// 4. 检查当前账户是否已有店铺
			boolean hasShop = shopDao.checkAccountShopExists(account.getId());
			// 5. 根据店名查询店铺
			Shop existShop = shopDao.getShop(shopName);
			/*
			 * 情况分析：
			 * 1）无 shop + 名字未被占用 → 创建
			 * 2）无 shop + 名字被占用 → 提示
			 * 3）有 shop + 名字未被占用 → 更新
			 * 4）有 shop + 名字被占用（非自己）→ 提示
			 */
			// ===== 情况 2 / 4：名字被占用且不是自己的 =====
			if (existShop != null && existShop.getAccountId() != account.getId()) {

				session.setAttribute("alertMsg", "店铺名称已被使用，请选择其他名称");
				session.setAttribute("alertType", "danger");
			    

			    response.sendRedirect(request.getContextPath() + "/shop-management");	
			    return;
			}
			// ===== 情况 1：无 shop + 名字可用 → 创建 =====
			if (!hasShop) {

			    shopDao.CreateShop(account.getId(), shopName, shopDescription);

			}
			// ===== 情况 3：有 shop + 名字可用 → 更新 =====
			else {

			    shopDao.updateShopInformation(account.getId(), shopName, shopDescription);
			}
			// ===== 成功提示 =====
			session.setAttribute("alertMsg", "保存成功！");
			session.setAttribute("alertType", "success");

               
            response.sendRedirect(request.getContextPath() + "/shop-management");	
	        return; 
            
		} catch (SQLException e) {
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
		 response.sendRedirect(request.getContextPath() + "/shop-management");	
	}
	

}
