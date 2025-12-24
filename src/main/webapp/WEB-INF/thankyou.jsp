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
                        class="text-black">下单成功</strong></div>

            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12 text-center">
                    <span class="icon-check_circle display-3 text-success"></span>

                    <h2 class="display-3 text-black">感谢您的购买！</h2>
                    <p class="lead mb-5">您的订单已成功提交，我们会尽快处理。</p>
                    <p><a href="shop" class="btn btn-sm btn-primary">返回商城</a></p>

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