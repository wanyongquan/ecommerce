<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<jsp:include page="/templates/head.jsp"/>

<body>
<div class="site-wrap">
     <jsp:include page="/templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0">
                    <a href="/">首页</a> <span class="mx-2 mb-0">/</span>
                    <strong class="text-black">申请售后</strong></div>

            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
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
		                            <th>商品名称</th>
		                            <th>申请时间</th>
		                            <th>状态</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                        <c:forEach var="record" items="${after_sales_service_history}">
		                            <tr>
		                                <td>${record.serviceId}</td>
		                                <td>${record.orderId}</td>
		                                <td>${record.productName}</td>
		                                <td>${record.appliedDate}</td>
		                                <td>
		                                <c:choose>
									        <c:when test="${record.status == 0}">待处理</c:when>
									        <c:when test="${record.status == 1}">已退款</c:when>
									        <c:when test="${record.status == 2}">拒绝退款</c:when>
		                                 </c:choose>
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

	<%--     <jsp:include page="/templates/footer.jsp"/> --%>
	</div><!-- /.wrapper -->
</div>


<jsp:include page="/templates/scripts.jsp"/>
</body>
</html>