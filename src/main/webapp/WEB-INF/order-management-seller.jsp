<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/templates/head-admin.jsp"/>

<body class="hold-transition sidebar-mini">
	<div class="wrapper">
	    <!-- Navbar -->
	    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
	        <!-- 左侧导航 -->
	        <ul class="navbar-nav">
	            <li class="nav-item">
	                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
	            </li>
	        </ul>
	        <!-- 右侧用户信息 -->
	        <ul class="navbar-nav ml-auto">
	            <li class="nav-item">
	                <a class="nav-link" href="#">欢迎, <c:out value="${sessionScope.account.firstName}"/><c:out value="${sessionScope.account.lastName}" /></a>
	            </li>
	        </ul>
	    </nav>
	    <!-- /.navbar -->
	    
    <!-- 左侧 Sidebar -->
   	<jsp:include page="../templates/sidebar.jsp"/>

	<!-- Content Wrapper -->
   	<div class="content-wrapper">
   	
    <section class="content-header">
	    <div class="container-fluid">
   	        <div class="row mb-2">
	            <div class="col-sm-6">
	                <h1>个人中心</h1>
	            </div>
	            <div class="col-sm-6">
	                <ol class="breadcrumb float-sm-right">
	                    <li class="breadcrumb-item">
	                        <a href="${pageContext.request.contextPath}/">首页</a>
	                    </li>
	                    <li class="breadcrumb-item active">个人中心</li>
	                </ol>
	            </div>
	        </div>
	    </div>
	</section>
	<section class="content">
	    <div class="container-fluid">	    
            <div class="row mb-5">
                <div class="col-md-12">
                    <div class="site-blocks-table">
                        <table class="table table-bordered">
                            <thead>
                            <tr>

                                <th>订单ID</th>
                                <th>订单总额</th>
                                <th>下单时间</th>
                                
                                <th>状态</th>
                                <th>操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order_list}" var="o">
                                <tr>
                                    <td>${o.id}</td>
								
                                    <td>¥${o.total}</td>

                                    <td>${o.date}</td>

                                    <td> <c:choose>
								        <c:when test="${o.status == 0}">买家已付款</c:when>
								        <c:when test="${o.status == 1}">已发货</c:when>
								        <c:when test="${o.status == 2}">已收货</c:when>
								        <c:otherwise>未知状态</c:otherwise>
								    </c:choose></td>
                                   <td>
									    <c:choose>
									        <%-- 状态为0（买家已付款）：显示启用的发货按钮 --%>
									        <c:when test="${o.status == 0}">
									            <a href="shipping-order-detail?order-id=${o.id}" class="btn btn-primary btn-sm"
									               style="background-color: #28a745; border-color: #28a745">
									                <span class="icon icon-send">发货</span>
									            </a>
									        </c:when>
									        
									        <%-- 状态为1（已发货）：显示禁用的发货按钮 --%>
									        <c:when test="${o.status == 1}">
									            <button class="btn btn-secondary btn-sm" disabled
									                    style="background-color: #6c757d; border-color: #6c757d; cursor: not-allowed"
									                    title="订单已发货，无法重复发货">
									                <span class="icon icon-send">已发货</span>
									            </button>
									        </c:when>
									        
									        <%-- 状态为2（已收货）：显示禁用的发货按钮 --%>
									        <c:when test="${o.status == 2}">
									            <button class="btn btn-secondary btn-sm" disabled
									                    style="background-color: #6c757d; border-color: #6c757d; cursor: not-allowed"
									                    title="订单已完成，无法发货">
									                <span class="icon icon-send">已收货</span>
									            </button>
									        </c:when>
									        
									        <%-- 其他状态（如未付款）：显示禁用的发货按钮 --%>
									        <c:otherwise>
									            <button class="btn btn-secondary btn-sm" disabled
									                    style="background-color: #6c757d; border-color: #6c757d; cursor: not-allowed"
									                    title="订单未付款，无法发货">
									                <span class="icon icon-send">不可发货</span>
									            </button>
									        </c:otherwise>
									    </c:choose>
									    
									    <%-- 可选：添加其他操作按钮，如查看详情 --%>
									    <a href="shipping-order-detail?order-id=${o.id}" class="btn btn-info btn-sm ml-2"
									       style="background-color: #17a2b8; border-color: #17a2b8">
									        <span class="icon icon-eye">详情</span>
									    </a>
									</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div> 
	</section>
	</div> <!-- /.content-wrapper -->

<%--     <jsp:include page="../templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->

<jsp:include page="../templates/scripts.jsp"/>
<style>
    .btn-disabled-custom {
        opacity: 0.65;
        pointer-events: none;
        cursor: not-allowed;
    }
    .btn-enabled {
        transition: all 0.3s ease;
    }
    .btn-enabled:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }
</style>
</body>
</html>