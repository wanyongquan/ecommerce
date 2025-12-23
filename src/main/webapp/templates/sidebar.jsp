<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="#" class="brand-link text-center">
        <span class="brand-text font-weight-light">后台管理</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- 菜单 -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu">
                <li class="nav-item">
                    <a href="seller_home" class="nav-link active">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>仪表盘</p>
                    </a>
                </li>
                <li class="nav-item">
                      <a href="profile-page-seller" class="nav-link">
                          <i class="nav-icon fas fa-user"></i>
                          <p>个人中心</p>
                      </a>
                </li>
                <li class="nav-item">
                      <a href="profile-page" class="nav-link">
                          <i class="nav-icon fas fa-store"></i>
                          <p>店铺中心</p>
                      </a>
                </li>
                <li class="nav-item ${product_management_active}">
                    <a href="product-management" class="nav-link">
                        <i class="nav-icon fas fa-boxes"></i>
                        <p>商品管理</p>
                    </a>
                </li>
                
                <li class="nav-item ${order_management_active}">
                    <a href="order-management-seller" class="nav-link">
                        <i class="nav-icon fas fa-receipt"></i>
                        <p>全部订单管理</p>
                    </a>
                </li>
                 <li class="nav-item">
                      <a href="profile-page" class="nav-link">
                          <i class="nav-icon fas fa-truck"></i>
                          <p>待发货订单</p>
                      </a>
                </li>
                <li class="nav-item">
                      <a href="order-management" class="nav-link">
                          <i class="nav-icon fas fa-chart-line"></i>
                          <p>商品销量排行</p>
                      </a>
                </li>
            </ul>
        </nav>
    </div>
    <!-- /.sidebar -->
</aside>
