<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="templates/head.jsp"/>

<body>
<div class="site-wrap">
    <jsp:include page="templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="/">首页</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">${product.name}</strong></div>
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

                    <p><strong class="text-primary h4">¥${product.price}</strong></p>

                    <!-- 新增尺码选择框 -->
                    <div class="mb-3">
                        <label for="size" class="form-label text-black font-weight-bold">尺码</label>
                        <select id="size" name="size" class="form-control" style="max-width: 200px;">
                            <option value="" disabled selected>选择尺码</option>
                            <option value="XS">XS</option>
                            <option value="S">S</option>
                            <option value="M">M</option>
                            <option value="L">L</option>
                            <option value="XL">XL</option>
                            <option value="XXL">XXL</option>
                        </select>
                    </div>

                    <!-- 新增颜色选择框 -->
                    <div class="mb-3">
                        <label for="color" class="form-label text-black font-weight-bold">颜色</label>
                        <select id="color" name="color" class="form-control" style="max-width: 200px;">
                            <option value="" disabled selected>选择颜色</option>
                            <option value="Black">黑色</option>
                            <option value="White">白色</option>
                            <option value="Red">红色</option>
                            <option value="Blue">蓝色</option>
                            <option value="Green">绿色</option>
                            <option value="Yellow">黄色</option>
                            <option value="Gray">灰色</option>
                        </select>
                    </div>

                    <form action="cart?product-id=&quantity=" method="get">
                        <div class="mb-3">
                            <div class="input-group mb-3" style="max-width: 200px;">
                                <input name="product-id" value="${product.id}" type="hidden">

                                <div class="input-group-prepend">
                                    <button class="btn btn-outline-primary js-btn-minus" type="button">&minus;</button>
                                </div>

                                <input id="quantity" name="quantity" type="text" class="form-control text-center"
                                       value="1" placeholder="" aria-label="商品数量"
                                       aria-describedby="button-addon1" style="max-width: 50px">

                                <div class="input-group-append">
                                    <button class="btn btn-outline-primary js-btn-plus" type="button">&plus;</button>
                                </div>

                                <label for="quantity" class="form-label text-black">
                                    库存剩余: ${product.amount}
                                </label>
                            </div>
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

    <jsp:include page="templates/featured-products.jsp"/>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>