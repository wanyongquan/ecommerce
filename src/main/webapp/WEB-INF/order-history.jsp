<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>

<html lang="zh-CN">

<jsp:include page="/templates/head.jsp"/>

<body>
<div class="site-wrap">
    <jsp:include page="/templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">

                <div class="col-md-12 mb-0"><a href="/">首页</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">订单历史</strong></div>

            </div>
        </div>
    </div>

    <div class="site-section" data-aos="fade-in">
        <div class="container">
            <div class="row mb-5">
                <div class="col-md-12">
                    <div class="site-blocks-table">
                        <table class="table table-bordered">
                            <thead>
                            <tr>

                                <th>订单ID</th>
                                <th>订单总额</th>
                                <th>收件人</th>
                                <th>下单时间</th>
                                <th>订单状态</th>
                                <th style="min-width: 195px">查看详情</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order_list}" var="o">
                                <tr>
                                    <td>${o.id}</td>
								
                                    <td>¥${o.total}</td>
									<td> TODO:改成收件人 ¥${o.total}</td>
                                    <td>${o.date}</td>
									<td><c:choose>
								        <c:when test="${o.status == 0}">买家已付款</c:when>
								        <c:when test="${o.status == 1}">已发货</c:when>
								        <c:when test="${o.status == 2}">已收货</c:when>
								        <c:otherwise>未知状态</c:otherwise>
								    </c:choose></td>
                                    <td>
                                        <a href="order-detail?order-id=${o.id}" class="btn btn-primary btn-sm"
                                           style="background-color: green ; border-color: green">
                                            <span class="icon icon-arrow-right"></span>
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
    </div>

    <%--     <jsp:include page="../templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->

<jsp:include page="../templates/scripts.jsp"/>
<style>
</body>
</html>