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
                    <div class="card-header bg-light">
                            <h4 class="mb-0 text-black">
                                <i class="icon icon-info-circle"></i> 收件人信息
                            </h4>
                     </div>
                      <div class="card-body">
                    <div class="p-3 p-lg-2 border bg-white">
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
            </div>
            
             <%-- 新增：订单状态显示行 --%>
            <div class="row mb-4">
                <div class="col-md-12">
                    <div class="card border">
                        <div class="card-header bg-light">
                            <h4 class="mb-0 text-black">
                                <i class="icon icon-info-circle"></i> 订单状态
                            </h4>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <h5 class="text-black">
                                        当前状态：
                                        <span class="badge 
                                            <c:choose>
                                                <c:when test="${order.status == 0}">badge-warning</c:when>
                                                <c:when test="${order.status == 1}">badge-info</c:when>
                                                <c:when test="${order.status == 2}">badge-success</c:when>
                                                <c:otherwise>badge-secondary</c:otherwise>
                                            </c:choose>
                                            font-weight-normal" style="font-size: 1.1rem;">
                                            <c:choose>
                                                <c:when test="${order.status == 0}">
                                                    <i class="icon icon-clock"></i> 买家已付款
                                                </c:when>
                                                <c:when test="${order.status == 1}">
                                                    <i class="icon icon-truck"></i> 已发货
                                                </c:when>
                                                <c:when test="${order.status == 2}">
                                                    <i class="icon icon-check-circle"></i> 已收货
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="icon icon-question-circle"></i> 未知状态 (${order.status})
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </h5>
                                    <p class="text-muted mb-0 mt-2">
                                        <small>
                                            <i class="icon icon-calendar"></i> 
                                            订单号：${order.id} | 
                                            下单时间：${order.date}
                                        </small>
                                    </p>
                                </div>
                                <div class="col-md-4 text-right">
                                    <c:if test="${order.status == 1}">
                                        <small class="text-info">
                                            <i class="icon icon-bell"></i> 商品已发出，请注意查收
                                        </small>
                                    </c:if>
                                    <c:if test="${order.status == 0}">
                                        <small class="text-warning">
                                            <i class="icon icon-bell"></i> 商家正在处理订单
                                        </small>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            
            <div class="row mb-5">
                <div class="col-md-12">
                	<div class="card-header bg-light">
                            <h4 class="mb-0 text-black">
                                <i class="icon icon-info-circle"></i> 商品列表
                            </h4>
                     </div>
                    <div class="card-body"> 
                      
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
            
            <%-- 新增：确认收货按钮区域 --%>
            <div class="row mb-4">
                <div class="col-md-12 text-right">
                    <c:choose>
                        <c:when test="${order.status == 1}">
                            <form action="confirm-receipt" method="post" style="display: inline;">
                                <input type="hidden" name="order-id" value="${order.id}">
                                <button type="submit" class="btn btn-success btn-lg">
                                    <i class="icon icon-send"></i> 确认收货
                                </button>
                            </form>
                        </c:when>
                        <c:when test="${order.status == 1}">
                            <button class="btn btn-secondary btn-lg" disabled>
                                <i class="icon icon-check"></i> 已发货
                            </button>
                        </c:when>
                        <c:when test="${order.status == 0}">
                            <button class="btn btn-info btn-lg" disabled>
                                <i class="icon icon-check-circle"></i> 已付款
                            </button>
                        </c:when>
                        <c:when test="${order.status == 2}">
                            <button class="btn btn-info btn-lg" disabled>
                                <i class="icon icon-check-circle"></i> 已收货
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-warning btn-lg" disabled>
                                <i class="icon icon-clock"></i> 其他
                            </button>
                        </c:otherwise>
                    </c:choose>
                    <a href="order-history" class="btn btn-outline-secondary btn-lg ml-2">
                        <i class="icon icon-arrow-left"></i> 返回订单列表
                    </a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>