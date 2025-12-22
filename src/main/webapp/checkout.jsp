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

                    <a href="cart.jsp">购物车</a>
                    <span class="mx-2 mb-0">/</span>

                    <strong class="text-black">结算</strong>

                </div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <form class="row" method="post" action="checkout">
                  <div class="col-md-12 mb-3">
	                <h4 class="text-black font-weight-bold">收货人信息</h4>
	                <hr class="mb-4">
	            </div>
	                      <c:forEach items="${address_list}" var="address" varStatus="status">
	                      
	        				<div class="col-md-4 mb-3">
				            
				                <input type="hidden" name="addr_id" value="${address.addr_id}">
				                <div class="card border h-100">
				                    <div class="card-header bg-light">
				                        <div class="custom-control custom-radio">
					                        <input type="radio" id="address${address.addr_id}" 
					                               name="selectedAddress" value="${address.addr_id}"
					                               class="custom-control-input" 
					                               <c:if test="${address.isDefault==1}">checked</c:if>>
					                        <label class="custom-control-label" for="address${address.addr_id}">
					                            <c:if test="${not empty address.addressLabel}">
					                                <span class="badge badge-info mr-2">${address.addressLabel}</span>
					                            </c:if>
					                            <c:if test="${address.isDefault==1}">
					                                <span class="badge badge-success ml-2">默认</span>
					                            </c:if>
					                        </label>
					                    </div>
				                    </div>
				                    <div class="card-body p-3">
				                        <div class="mb-2">
				                            <span class="text-black">收件人：</span>
				                            <span>${address.recipientName}</span>
				                        </div>
				                        <div class="mb-2">
				                            <span class="text-black">电话：</span>
				                            <span>${address.phone}</span>
				                        </div>
				                        <div class="mb-2">
				                            <span class="text-black">地区：</span>
				                            <span>${address.distinct}</span>
				                        </div>
				                        <div class="mb-2">
				                            <span class="text-black">详细地址：</span>
				                            <span>${address.address_detail}</span>
				                        </div>
				                    </div>
				                </div>
				            
				        </div>
	                         
	                    </c:forEach>
	               
	         

                <div class="col-md-12">
                    <div class="row mb-5">
                        <div class="col-md-12">

                            <h2 class="h3 mb-3 text-black">订单信息</h2>


                            <div class="p-3 p-lg-5 border">
                                <table class="table site-block-order-table mb-5">
                                    <thead>
                                    <tr>

                                        <th style="text-align: center">商品</th>
                                        <th style="text-align: center">单价</th>
                                        <th style="text-align: center">数量</th>
                                        <th style="text-align: center">小计</th>

                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${order.cartProducts}" var="o">
                                        <tr>
                                            <td>
                                                <input name="product-name" class="form-control-plaintext h5 text-black"
                                                       value="${o.product.name}" style="text-align: center" readonly>
                                            </td>

                                            <td>
                                                <input name="product-price" class="form-control-plaintext h5 text-black"
                                                       value="${o.price}" style="text-align: center" readonly>
                                            </td>

                                            <td>
                                                <input name="product-quantity"
                                                       class="form-control-plaintext h5 text-black"
                                                       value="${o.quantity}" style="text-align: center" readonly>
                                            </td>

                                            <td>
                                                <input name="product-total" class="form-control-plaintext h5 text-black"
                                                       value="${o.price * o.quantity}" style="text-align: center"
                                                       readonly>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <tr>
                                        <td></td>
                                        <td></td>

                                        <td class="text-black font-weight-bold"><strong>订单总计</strong></td>

                                        <td class="text-black font-weight-bold">
                                            <input name="order-total-price" class="form-control-plaintext h5 text-black"
                                                   value="${total_price}" style="text-align: center" readonly>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>

                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-lg py-3 btn-block">

                                        提交订单

                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>