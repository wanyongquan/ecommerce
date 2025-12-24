<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/templates/head-admin.jsp"/>

<body>
<div class="site-wrap">
    <jsp:include page="../templates/sidebar.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="/">首页</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">${product.account.shopName}</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <img src="data:image/jpg;base64,${product.base64Image}" alt="商品图片" class="img-fluid">
                </div>

                <div class="col-md-6">
                    <h2 class="text-black">${product.name}</h2>

                    <p>${product.description}</p>

                    <p><strong class="text-primary h4">$${product.price}</strong></p>

                    <form action="cart?product-id=&quantity=&color=" method="get">
                        <div class="mb-3">
                            <div class="input-group mb-3" style="max-width: 200px;">
                                <input name="product-id" value="${product.id}" type="hidden">
                                
                                
                                <div class="input-group-prepend">
                                    <button class="btn btn-outline-primary js-btn-minus" type="button">&minus;</button>
                                </div>

                                <input id="quantity" name="quantity" type="text" class="form-control text-center"
                                       value="1" placeholder="" aria-label="Example text with button addon"
                                       aria-describedby="button-addon1" style="max-width: 50px">

                                <div class="input-group-append">
                                    <button class="btn btn-outline-primary js-btn-plus" type="button">&plus;</button>
                                </div>

                              <%--   <label for="quantity" class="form-label text-black">
                                    Available products: ${product.amount}
                                </label> --%>
                            </div>
                        </div>
                        <div class="mb-3">
                        	<!-- 添加颜色选择部分 -->
			                    <c:if test="${not empty product_color_list}">
			                        <div class="mb-4">
			                            <label class="text-black font-weight-bold">颜色</label>
			                            <div class="mt-2">
			                                <c:forEach items="${product_color_list}" var="colorStock" varStatus="status">
			                                    <div class="form-check mb-2">
			                                        <input class="form-check-input" type="radio" 
			                                               name="color" 
			                                               id="color-${status.index}" 
			                                               value="${colorStock.colorName}"
			                                               ${status.first ? 'checked' : ''}>
			                                        <label class="form-check-label text-black" for="color-${status.index}">
			                                            ${colorStock.colorName}
			                                        </label>
			                                    </div>
			                                </c:forEach>
			                            </div>
			                        </div>
			                    </c:if>
                        	
                        </div>

                        <p>
                            <button type="submit" class="buy-now btn btn-sm btn-primary" ${disabled}>
                                加入购物车
                            </button>
                        </p>
                    </form>
                </div>
            </div>

            <c:if test="${alert}">
                <div class="row justify-content-center mt-3">
                    <div class="alert alert-danger d-flex justify-content-center" role="alert"
                         data-aos="fade-up" style="max-width: 800px; min-width: 600px">
                        <strong class="font-weight-bold">

                            库存不足！当前仅剩余 ${product.amount} 件商品！

                        </strong>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <jsp:include page="../templates/sidebar.jsp"/>

    <%--     <jsp:include page="../templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->

<jsp:include page="../templates/scripts.jsp"/>
<style>
</body>
</html>