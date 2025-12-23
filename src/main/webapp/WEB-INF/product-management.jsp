<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
 <jsp:include page="/templates/head-admin.jsp"/> 
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
  <%--   <jsp:include page="templates/header.jsp"/> --%>
	<!-- 左侧 Sidebar -->
   <jsp:include page="/templates/sidebar.jsp"/>

   <!-- Content Wrapper -->
   <div class="content-wrapper">
    
    <section class="content-header">
	    <div class="container-fluid">
	        <div class="row mb-2">
	            <div class="col-sm-6">
	                <h1>商品管理</h1>
	            </div>
	            <div class="col-sm-6">
	                <ol class="breadcrumb float-sm-right">
	                    <li class="breadcrumb-item">
	                        <a href="${pageContext.request.contextPath}/">首页</a>
	                    </li>
	                    <li class="breadcrumb-item active">商品管理</li>
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

                                <th>商品图片</th>
                                <th>商品ID</th>
                                <th style="max-width: 120px">商品名称</th>
                                <th>商品价格</th>
                                <th>商品分类</th>
                                <th>库存总量</th>
                                <th>状态</th>
                                <th style="min-width: 195px">操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${product_list}" var="o">
                                <tr>
                                    <td class="product-thumbnail">
                                        <img src="data:image/jpg;base64,${o.base64Image}" alt="商品图片" class="img-fluid">
                                    </td>
                                    <td>${o.id}</td>
                                    <td>${o.name}</td>
                                    <td>¥${o.price}</td>
                                    <td>${o.category.name}</td>
                                    <td>${o.amount}</td>
                                    <td>${(o.isDeleted) ? "已下架" : "在售中"}</td>
                                    <td>
                                        <!-- 修正跳转路径，避免404 -->
                                        <a href="${pageContext.request.contextPath}/edit-product?product-id=${o.id}" 
                                           class="btn btn-primary btn-sm" style="background-color: green ; border-color: green">
                                            <span class="icon icon-pencil"></span> 编辑
                                        </a>
                                        <a href="${pageContext.request.contextPath}/remove-product?product-id=${o.id}"
                                           class="btn btn-primary btn-sm ${(o.isDeleted) ? "disabled" : " "}"
                                           style="background-color: red ; border-color: red">
                                            <span class="icon icon-trash"></span>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="row mb-5">
                        <div class="col-md-6">
                            <button class="btn btn-outline-primary btn-sm btn-block">批量下架</button>
                         </div>

                        <!-- Button trigger add product modal -->
                        <div class="col-md-6 mb-3 mb-md-0">
                            <button class="btn btn-primary btn-sm btn-block" data-toggle="modal"
                                    data-target="#addProductModal">添加商品
                            </button>
                        </div>

                        <!-- Add product Modal -->
                        <div class="modal fade bd-example-modal-lg" id="addProductModal" tabindex="-1" role="dialog"

                             aria-labelledby="myLargeModalLabel" aria-hidden="true" style="z-index:2600">
                            <div class="modal-dialog modal-lg modal-dialog-centered">
                                <!-- 修正表单提交路径，避免404 -->
                                <form class="modal-content" action="${pageContext.request.contextPath}/add-product" 
                                      method="post" enctype="multipart/form-data">
                                    <div class="modal-header">
                                        <h5 class="modal-title text-black" id="addProductModalLabel">
                                            商品信息
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="关闭">

                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <div class="modal-body" style="padding: 0">
                                        <div class="p-3">

                                            <!-- 新增：隐藏字段，标记商品默认未删除 -->
                                            <input type="hidden" name="product-isDeleted" value="false">

                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-name" class="text-black">
                                                        商品名称 <span class="text-danger">*</span>
                                                    </label>

                                                    <input name="product-name" type="text" class="form-control"
                                                           id="product-name">
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-image" class="text-black">
                                                        商品图片 <span class="text-danger">*</span>
                                                    </label>

                                                    <input name="product-image" type="file" class="form-control" id="product-image">
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-price" class="text-black">
                                                        商品价格 <span class="text-danger">*</span>
                                                    </label>

                                                    <input name="product-price" type="number" class="form-control"
                                                           id="product-price">
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-description" class="text-black">
                                                        商品描述 <span class="text-danger">*</span>
                                                    </label>

                                                    <textarea name="product-description" id="product-description"
                                                              cols="30" rows="7"
                                                              class="form-control"></textarea>
                                                </div>
                                            </div>
											<!-- 颜色和库存部分 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label class="text-black">
                                                        规格 <span class="text-danger">*</span>
                                                    </label>
                                                    
                                                    <div id="color-stock-container">
                                                        <!-- 默认第一行 -->
                                                        <div class="row color-stock-row mb-2">
                                                            <div class="col-md-6">
                                                                <input type="text" class="form-control color-name" 
                                                                       name="color-name[]" placeholder="SKU (例如 颜色、尺码)" required>
                                                            </div>
                                                            <div class="col-md-5">
                                                                <input type="number" class="form-control color-stock" 
                                                                       name="color-stock[]" placeholder="库存" min="0" required>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <button type="button" class="btn btn-danger btn-sm remove-color-row" disabled>
                                                                    <span class="icon icon-close"></span>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="mt-2">
                                                        <button type="button" id="add-color-row" class="btn btn-secondary btn-sm">
                                                            <span class="icon icon-plus"></span> 添加规格
                                                        </button>
                                                    </div>
  
													<!-- 总库存显示 -->
                                                    <div class="mt-3">
                                                        <label class="text-black">Total Stock:</label>
                                                        <span id="total-stock" class="font-weight-bold ml-2">0</span>
                                                    </div>
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-amount" class="text-black">
                                                        库存数量 <span class="text-danger">*</span>
                                                    </label>

                                                    <input name="product-amount" type="number" class="form-control"
                                                           id="product-amount">
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-category" class="text-black">
                                                        商品分类 <span class="text-danger">*</span>
                                                    </label>

                                                    <select name="product-category" id="product-category"
                                                            class="form-control">
                                                        <c:forEach items="${category_list}" var="o">
                                                            <option value="${o.id}">${o.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-primary btn-sm btn-block"
                                                data-dismiss="modal" style="margin-top: 0">
                                            取消
                                        </button>

                                        <button type="submit" class="btn btn-primary btn-sm btn-block"
                                                style="margin-top: 0">
                                            保存

                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
       </div>
	</section>
	</div> <!-- /.content-wrapper -->

<%--     <jsp:include page="/templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->

<jsp:include page="/templates/scripts.jsp"/>
<!-- 颜色管理脚本 -->
    <script>
    $(document).ready(function() {
        // 初始化总库存计算
        calculateTotalStock();
        
        // 添加颜色行
        $('#add-color-row').click(function() {
            const rowCount = $('.color-stock-row').length;
            const newRow = `
                <div class="row color-stock-row mb-2">
                    <div class="col-md-6">
                        <input type="text" class="form-control color-name" 
                               name="color-name[]" placeholder="Color (e.g. Red, Blue, Black)" required>
                    </div>
                    <div class="col-md-5">
                        <input type="number" class="form-control color-stock" 
                               name="color-stock[]" placeholder="Stock" min="0" required>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-danger btn-sm remove-color-row">
                            <span class="icon icon-close"></span>
                        </button>
                    </div>
                </div>
            `;
            $('#color-stock-container').append(newRow);
            
            // 如果有两行或以上，启用所有删除按钮
            if ($('.color-stock-row').length > 1) {
                $('.remove-color-row').prop('disabled', false);
            }
            
            // 绑定新行的输入事件
            $('#color-stock-container .color-stock-row:last .color-stock').on('input', calculateTotalStock);
        });
        
        // 删除颜色行（使用事件委托）
        $(document).on('click', '.remove-color-row', function() {
            if ($('.color-stock-row').length > 1) {
                $(this).closest('.color-stock-row').remove();
                calculateTotalStock();
                
                // 如果只剩一行，禁用删除按钮
                if ($('.color-stock-row').length === 1) {
                    $('.remove-color-row').prop('disabled', true);
                }
            }
        });
        
        // 计算总库存
        function calculateTotalStock() {
            let total = 0;
            $('.color-stock').each(function() {
                const stock = parseInt($(this).val()) || 0;
                total += stock;
            });
            $('#total-stock').text(total);
            $('#product-amount').val(total);
        }
        
        // 监听库存输入变化
        $(document).on('input', '.color-stock', calculateTotalStock);
        
        // 表单提交验证
        $('form').submit(function(e) {
            // 验证颜色名称是否重复
            const colorNames = [];
            let hasDuplicate = false;
            
            $('.color-name').each(function() {
                const name = $(this).val().trim().toLowerCase();
                if (name) {
                    if (colorNames.includes(name)) {
                        hasDuplicate = true;
                        $(this).addClass('is-invalid');
                    } else {
                        colorNames.push(name);
                        $(this).removeClass('is-invalid');
                    }
                }
            });
            
            if (hasDuplicate) {
                e.preventDefault();
                alert('Error: Duplicate color names are not allowed.');
                return false;
            }
            
            // 验证至少有一个颜色
            if (colorNames.length === 0) {
                e.preventDefault();
                alert('Error: At least one color is required.');
                return false;
            }
            
            // 验证总库存大于0
            const totalStock = parseInt($('#total-stock').text());
            if (totalStock <= 0) {
                e.preventDefault();
                alert('Error: Total stock must be greater than 0.');
                return false;
            }
            
            return true;
        });
        
        // 模态框显示时重置表单
        $('#addProductModal').on('show.bs.modal', function() {
            resetColorForm();
        });
        
        // 重置颜色表单
        function resetColorForm() {
            // 移除多余的行，只保留第一行
            $('.color-stock-row:gt(0)').remove();
            
            // 重置第一行的值
            $('.color-name').val('');
            $('.color-stock').val('');
            
            // 禁用第一行的删除按钮
            $('.remove-color-row').prop('disabled', true);
            
            // 重置总库存
            $('#total-stock').text('0');
        }
    });
    </script>
    
    <!-- 可选的CSS样式 -->
    <style>
    .color-stock-row {
        align-items: center;
    }
    
    .color-name {
        border-right: none;
    }
    
    .color-stock {
        border-left: none;
    }
    
    .remove-color-row {
        padding: 0.25rem 0.5rem;
        font-size: 0.75rem;
    }
    
    #total-stock {
        color: #007bff;
        font-size: 1.2rem;
    }
    
    .is-invalid {
        border-color: #dc3545;
    }
    </style>
</body>
</html>