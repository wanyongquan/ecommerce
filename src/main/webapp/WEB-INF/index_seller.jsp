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
                    <div class="col-lg-3 col-6">
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
                    <div class="col-lg-3 col-6">
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
                   <div class="col-lg-3 col-6">
			        <div class="small-box bg-warning">
			            <div class="inner">
			                <h3>${pending_order_amount}</h3>
			                <p>待发货订单</p>
			            </div>
			            <div class="icon">
			                <i class="fas fa-truck"></i>
			            </div>
			        </div>
			    </div>
			
			    <div class="col-lg-3 col-6">
			        <div class="small-box bg-danger">
			            <div class="inner">
			                <h3>¥${total_sales_amount}</h3>
			                <p>累计销售额</p>
			            </div>
			            <div class="icon">
			                <i class="fas fa-yen-sign"></i>
			            </div>
			        </div>
			    </div>
            </div>
            <div class="row">
			    <div class="col-lg-12">
			        <div class="card">
			            <div class="card-header">
			                <h3 class="card-title">热销商品 TOP 5</h3>
			            </div>
			            <div class="card-body">
			                <div class="row">
			                    <c:forEach items="${top_product_list}" var="o">
			                        <div class="col-lg-3 col-6">
			                            <div class="card">
			                                <img src="data:image/jpg;base64,${o.base64Image}"
			                                     class="card-img-top"
			                                     style="height:160px;object-fit:cover;">
			                                <div class="card-body">
			                                    <h6>${o.productName}</h6>
			                                    <p class="mb-1">¥${o.price}</p>
			                                    <small>销量：${o.salesAmount}</small>
			                                </div>
			                            </div>
			                        </div>
			                    </c:forEach>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
			<div class="row">
			    <div class="col-lg-12">
			        <div class="card">
			            <div class="card-header">
			                <h3 class="card-title">近 7 日累计销售额</h3>
			                <div class="card-tools">
			                    <a href="sales-statistics" class="btn btn-sm btn-primary">
			                       <!--  查看详细 -->
			                    </a>
			                </div>
			            </div>
			            <div class="card-body" style="height: 800px;">
			                <canvas id="salesChart" height="800"></canvas>
			            </div>
			        </div>
			    </div>
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
<script src="static/css/adminlte/plugins/chart.js/Chart.min.js"></script>

<script>
    const ctx = document.getElementById('salesChart').getContext('2d');

    const salesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ${xAxisData},   // 例如 ["06-01","06-02",...]
            datasets: [{
                label: '销售额（元）',
                data: ${seriesData}, // 例如 [1200, 900, 1500, ...]
                borderColor: '#007bff',
                fill: false,
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
</script>

</body>
</html>