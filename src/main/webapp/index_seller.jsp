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

    <!-- Main Sidebar Container -->
   <jsp:include page="templates/sidebar.jsp"/>

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
<!-- </body> -->
<%-- <body>
<div class="site-wrap">
    <jsp:include page="templates/header.jsp"/>

    <div class="site-blocks-cover" style="background-image: url(static/images/hero_1.jpg);" data-aos="fade">
        <div class="container">
            <div class="row align-items-start align-items-md-center justify-content-end">
                <div class="col-md-5 text-center text-md-left pt-5 pt-md-0">
                    <h1 class="mb-2">找到属于你的完美鞋履</h1>

                    <div class="intro-text text-center text-md-left">
                        <p class="mb-4">我们精选各类高品质鞋款，从休闲到商务，从舒适到时尚，满足你不同场景的穿搭需求，让每一步都自在从容。
                        </p>

                        <p>
                            <a href="shop" class="btn btn-sm btn-primary">立即选购</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section site-section-sm site-blocks-1">
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-truck"></span>
                    </div>

                    <div class="text">
                        <h2 class="text-uppercase">免费配送</h2>
                        <p>全国范围内免费配送，让你的心仪服饰快速送达。</p>
                    </div>
                </div>

                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="100">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-refresh2"></span>
                    </div>
                    <div class="text">
                        <h2 class="text-uppercase">免费退换</h2>
                        <p>7天无理由退换货，购物无忧，体验更贴心。</p>
                    </div>
                </div>

                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="200">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-help"></span>
                    </div>
                    <div class="text">
                        <h2 class="text-uppercase">客户支持</h2>
                        <p>专业客服团队解答你的购物疑问，提供贴心的售前售后全流程服务。</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/collections-section.jsp"/>

    <jsp:include page="templates/featured-products.jsp"/>

    <div class="site-section block-8">
        <div class="container">
            <div class="row justify-content-center  mb-5">
                <div class="col-md-7 site-section-heading text-center pt-4">
                    <h2>限时大促！</h2>
                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-md-12 col-lg-7 mb-5">
                    <a href="#"><img src="static/images/blog_1.jpg" alt="促销活动图"
                                     class="img-fluid rounded"></a>
                </div>
                <div class="col-md-12 col-lg-5 text-center pl-md-5">
                    <h2><a href="#">全场商品五折特惠</a></h2>
                    <p class="post-meta mb-4">作者 <a href="#">卡尔·史密斯</a> <span class="block-8-sep">&bullet;</span>
                        2018年9月3日</p>
                    <p>本次大促覆盖全品类鞋款，限时特惠低至五折，错过再等一年！品质不变，价格惊喜，快来挑选你的心仪款式。</p>
                    <p><a href="shop" class="btn btn-primary btn-sm">立即选购</a></p>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div> --%>
<%-- 
<jsp:include page="templates/scripts.jsp"/>

<!-- jQuery (AdminLTE 依赖) -->
<script src="static/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap JS (AdminLTE 依赖) -->
<script src="static/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE JS -->
<script src="static/css/adminlte/js/adminlte.min.js"></script>

</body> --%>
</html>