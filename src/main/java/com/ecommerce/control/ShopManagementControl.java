package com.ecommerce.control;

import java.io.IOException;
import java.sql.SQLException;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置请求编码，避免中文乱码
        request.setCharacterEncoding("UTF-8");
        
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		String shopName = request.getParameter("shop_name");
		String shopDescription = request.getParameter("shop_description");
		
		// 3. 参数校验
        if (shopName == null || shopName.trim().isEmpty()) {
            request.setAttribute("shop_update_msg", 
                "<div class='alert alert-danger'>店铺名称不能为空！</div>");
            request.getRequestDispatcher("/WEB-INF/profile-page.jsp").forward(request, response);
            return;
        }
        
        shopName = shopName.trim();
        if (shopDescription != null) {
            shopDescription = shopDescription.trim();
        }
        
		try {
			// 4. 检查店铺名称是否已存在
			Shop existShop = shopDao.getShop(shopName);
            if (existShop == null) {
            	// 不存在，可用；
            	if  (shopDao.checkAccountShopExists(account.getId())) {
            		//更新店铺；
            		shopDao.updateShopInformation(account.getId(), shopName, shopDescription);
            	}
            	else {
            		
	            	// 不存在，新建店铺；
	            	shopDao.CreateShop(account.getId(), shopName, shopDescription);
            	
            	}
            	String alert = "<div class=\"alert alert-success wrap-input100\">\n" +
                        "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                        "                            保存成功!\n" +
                        "                        </p>\n" +
                        "                    </div>";
                request.setAttribute("alert", alert);
                request.getRequestDispatcher("/WEB-INF/profile-page.jsp").forward(request, response);
            }else if ( existShop.getAccountId() != account.getId()) {
            	// 存在，属于其他用户，不可用；
	                String alert = "<div class='alert alert-danger'>"
	                        + "    <strong>更新失败！</strong>店铺名称 \"" + shopName + "\" 已存在，请使用其他名称。"
	                        + "</div>";
	           request.setAttribute("shop_update_msg", alert);
	           
	           request.getRequestDispatcher("/WEB-INF/profile-page.jsp").forward(request, response);
	           
            }
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
	}
	

}
