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
                <div class="col-md-12 mb-0">
                    <a href="/"> 首页 </a>
                    <span class="mx-2 mb-0">/</span>

                    <a href="order-history">订单历史</a>
                    <span class="mx-2 mb-0">/</span>               

                    <strong class="text-black">订单详情</strong>

                </div>
            </div>
        </div>
    </div>

    <div class="site-section" data-aos="fade-in">
        <div class="container">
         	<%-- 新增：收件人信息区域 --%>
        	<div class="row mb-5">
                <div class="col-md-12">
                    <h2 class="h3 mb-3 text-black">收件人信息</h2>
                    <div class="p-3 p-lg-5 border bg-white">
                        <c:choose>
                            <c:when test="${not empty recipientInfo}">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="text-black font-weight-bold">收货人：</label>
                                        <div class="form-control-plaintext">
                                            ${recipientInfo.recipientName}
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="text-black font-weight-bold">联系电话：</label>
                                        <div class="form-control-plaintext">
                                            ${recipientInfo.phone}
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 mb-3">
                                        <label class="text-black font-weight-bold">收货地址：</label>
                                        <div class="form-control-plaintext">
                                            ${recipientInfo.address_detail}
                                            
                                        </div>
                                    </div>
                                </div>

                            </c:when>

                        </c:choose>                   

                    </div>
                </div>
            </div>
            <div class="row mb-5">
                <div class="col-md-12">
                    <div class="site-blocks-table">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>商品</th>
                                <th>商品规格</th>
                                <th>购买数量</th>
                                <th>商品单价</th>
                                
                                <!-- <th>订单金额</th> -->
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order_detail_list}" var="o">
                                <tr>
                                    <td>${o.product.id}</td>
								    <td>${o.product.name}</td>
								    <td>${o.pickedColor}</td>
                                    <td>${o.quantity}</td>
                                    <td>¥${o.price}</td>				    
                                    <!-- <td>$${o.price * o.quantity}</td> -->

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>