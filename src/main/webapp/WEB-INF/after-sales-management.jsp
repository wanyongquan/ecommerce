<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/templates/head-admin.jsp"/>

<body class="hold-transition sidebar-mini">
	<div class="wrapper">
	    <!-- 顶部 navbar -->
    	<jsp:include page="/templates/navbar.jsp"/>
	    
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
	                    <li class="breadcrumb-item active">订单管理</li>
	                </ol>
	            </div>
	        </div>
	    </div>
	</section>
	
	<section class="content">
	    <div class="container-fluid">
	        <h3 class="mb-4">售后服务记录</h3>
	        
	        <div class="card">
	            <div class="card-body">
	                <table class="table table-bordered table-hover">
	                    <thead class="thead-light">
	                        <tr>
	                            <th>退货编号</th>
	                            <th>订单编号</th>
	                            <th>商品编号</th>
	                            <th>商品名称</th>
	                            <th>申请时间</th>
	                            <th>原因</th>
	                            <th>描述</th>
	                            <th>状态</th>
	                            <th>操作</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:forEach var="record" items="${after_sales_service_history}">
	                            <tr>
	                                <td>${record.serviceId}</td>
	                                <td>${record.orderId}</td>
	                                <td>${record.productId}</td>
	                                <td>${record.productName}</td>
	                                <td>${record.appliedDate}</td>
	                                <td>
	                                <c:choose>
								        <c:when test="${record.reason == 'QUALITY'}">商品质量/故障</c:when>
								        <c:when test="${record.reason == 'DAMAGED'}">不喜欢/买多/买错</c:when>
								        <c:when test="${record.reason == 'WRONG_ITEM'}">商品破损/包装问题</c:when>
								        <c:when test="${record.reason == 'MISSING'}">少件/漏发/未收到货</c:when>
								        <c:when test="${record.reason == 'NOT_AS_DESCRIBED'}">商品与页面描述不符</c:when>
								        <c:when test="${record.reason == 'OTHER'}">其他</c:when>
								        <c:otherwise>未知原因</c:otherwise>
								    </c:choose>
	                                </td>
	                                <td>${record.serviceDescription}</td>
	                                <td>
	                                 <c:choose>
								        <c:when test="${record.status == 0}">待处理</c:when>
								        <c:when test="${record.status == 1}">已退款</c:when>
								        <c:when test="${record.status == 2}">拒绝退款</c:when>
	                                 </c:choose>
	                                </td>
	                                <td>
									    <div class="dropdown">
									        <button class="btn btn-primary btn-sm dropdown-toggle 
									        		<c:choose>
											            <c:when test="${record.status == 0}">btn-primary</c:when>
											            <c:otherwise>btn-secondary</c:otherwise>
											        </c:choose>"
									                type="button"
									                 
									                data-toggle="dropdown"
									                aria-haspopup="true"
									                aria-expanded="false"
									                 <c:if test="${record.status ne 0}">disabled</c:if>>
									            处理
									        </button>
									
									        <div class="dropdown-menu">
									            <form action="${pageContext.request.contextPath}/after-sales-management"
									                  method="post" class="px-2 py-1">
									                <input type="hidden" name="serviceId" value="${record.serviceId}">
									                <input type="hidden" name="orderId" value="${record.orderId}">
									                <input type="hidden" name="productId" value="${record.productId}">
									                <input type="hidden" name="handleaction" value="approve">
									                <button type="submit" class="dropdown-item text-success">
									                    同意退款
									                </button>
									            </form>
									
									            <form action="${pageContext.request.contextPath}/after-sales-management"
									                  method="post" class="px-2 py-1">
									                <input type="hidden" name="serviceId" value="${record.serviceId}">
									                <input type="hidden" name="orderId" value="${record.orderId}">
									                <input type="hidden" name="productId" value="${record.productId}">
									                <input type="hidden" name="handleaction" value="reject">
									                <button type="submit" class="dropdown-item text-danger">
									                    拒绝退款
									                </button>
									            </form>
									        </div>
									    </div>
									</td>
	                            </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
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