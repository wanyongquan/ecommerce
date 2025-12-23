<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>后台 Dashboard</title>

    <!-- AdminLTE CSS -->
    <link rel="stylesheet" href="static/css/adminlte/dist/css/adminlte.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="static/css/adminlte/plugins/fontawesome-free/css/all.min.css">
   
</head>


<body class="hold-transition sidebar-mini">
<div class="wrapper">

    <!-- 顶部 navbar -->
    <jsp:include page="/templates/navbar.jsp"/>

    <!-- Main Sidebar Container -->
   <jsp:include page="/templates/sidebar.jsp"/>

    <!-- Content Wrapper -->
    <div class="content-wrapper">
        <!-- Content Header -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>仪表盘</h1>
                    </div>
                </div>
            </div>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- 信息卡片 -->
                <div class="row">
                    <div class="col-lg-4 col-6">
                        <div class="small-box bg-info">
                            <div class="inner">
                                <h3>${product_amount}</h3>
                                <p>在售商品总数</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-box"></i>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-6">
                        <div class="small-box bg-success">
                            <div class="inner">
                                <h3>${order_amount}</h3>
                                <p>订单总数</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-shopping-cart"></i>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-6">
                        <div class="small-box bg-warning">
                            <div class="inner">
                                <h3>${totalUsers}</h3>
                                <p>用户总数</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-users"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 商品展示 -->
                <div class="row">
                    <c:forEach items="${product_list}" var="o">
                        <div class="col-lg-3 col-6">
                            <div class="card">
                                <img src="data:image/jpg;base64,${o.base64Image}" class="card-img-top"
                                     alt="商品图片" style="height: 200px; object-fit: cover;">
                                <div class="card-body">
                                    <h5 class="card-title">${o.name}</h5>
                                    <p class="card-text">¥${o.price}</p>
                                    <p class="card-text">已售：${o.salesAmount}</p>
                                    <a href="product-detail?id=${o.id}" class="btn btn-primary btn-sm">查看</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Footer -->
    <footer class="main-footer">
        <div class="float-right d-none d-sm-block">
            <b>Version</b> 1.0
        </div>
        <strong>&copy; 2025 后台管理</strong>
    </footer>

</div>
<!-- ./wrapper -->

<!-- Scripts -->
<script src="static/css/adminlte/plugins/jquery/jquery.min.js"></script>
<script src="static/css/adminlte/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="static/css/adminlte/dist/js/adminlte.min.js"></script>
</body>
</html>