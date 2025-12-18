<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="templates/head.jsp"/>

<body>
<div class="site-wrap">
    <jsp:include page="templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0">
                    <!-- 修正Home跳转路径，避免404 -->
                    <a href="${pageContext.request.contextPath}/">首页</a> 
                    <span class="mx-2 mb-0">/</span> 
                    <strong class="text-black">商品管理</strong>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
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
                                <th>尺码</th> <!-- 新增：尺码列 -->
                                <th>颜色</th> <!-- 新增：颜色列 -->
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
                                    <td>${o.size}</td> <!-- 新增：展示商品尺码 -->
                                    <td>${o.color}</td> <!-- 新增：展示商品颜色 -->
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
                                            <span class="icon icon-trash"></span> 下架
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
                             aria-labelledby="myLargeModalLabel" aria-hidden="true">
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
                                            <!-- 原有：商品名称 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-name" class="text-black">
                                                        商品名称 <span class="text-danger">*</span>
                                                    </label>
                                                    <input name="product-name" type="text" class="form-control"
                                                           id="product-name" required>
                                                </div>
                                            </div>

                                            <!-- 原有：商品图片 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-image" class="text-black">
                                                        商品图片 <span class="text-danger">*</span>
                                                    </label>
                                                    <input name="product-image" type="file" class="form-control"
                                                           id="product-image" accept="image/*" required>
                                                </div>
                                            </div>

                                            <!-- 原有：商品价格 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-price" class="text-black">
                                                        商品价格 <span class="text-danger">*</span>
                                                    </label>
                                                    <input name="product-price" type="number" class="form-control"
                                                           id="product-price" step="0.01" min="0.01" required>
                                                </div>
                                            </div>

                                            <!-- 原有：商品描述 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-description" class="text-black">
                                                        商品描述 <span class="text-danger">*</span>
                                                    </label>
                                                    <textarea name="product-description" id="product-description"
                                                              cols="30" rows="3" class="form-control" required></textarea>
                                                </div>
                                            </div>

                                            <!-- 新增：商品尺码选择 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-size" class="text-black">
                                                        尺码 <span class="text-danger">*</span>
                                                    </label>
                                                    <select name="product-size" id="product-size" class="form-control" required>
                                                        <option value="">-- 选择尺码 --</option>
                                                        <option value="XS">XS</option>
                                                        <option value="S">S</option>
                                                        <option value="M">M</option>
                                                        <option value="L">L</option>
                                                        <option value="XL">XL</option>
                                                        <option value="XXL">XXL</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- 新增：商品颜色选择 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-color" class="text-black">
                                                        颜色 <span class="text-danger">*</span>
                                                    </label>
                                                    <select name="product-color" id="product-color" class="form-control" required>
                                                        <option value="">-- 选择颜色 --</option>
                                                        <option value="Black">黑色</option>
                                                        <option value="White">白色</option>
                                                        <option value="Red">红色</option>
                                                        <option value="Blue">蓝色</option>
                                                        <option value="Green">绿色</option>
                                                        <option value="Yellow">黄色</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- 原有：商品库存 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-amount" class="text-black">
                                                        库存数量 <span class="text-danger">*</span>
                                                    </label>
                                                    <input name="product-amount" type="number" class="form-control"
                                                           id="product-amount" min="0" required>
                                                </div>
                                            </div>

                                            <!-- 原有：商品分类 -->
                                            <div class="form-group row">
                                                <div class="col-md-12">
                                                    <label for="product-category" class="text-black">
                                                        商品分类 <span class="text-danger">*</span>
                                                    </label>
                                                    <select name="product-category" id="product-category" class="form-control" required>
                                                        <option value="">-- 选择分类 --</option>
                                                        <option value="1">男装</option>
                                                        <option value="2">女装</option>
                                                        <%--
                                                        <c:forEach items="${category_list}" var="o">
                                                            <option value="${o.id}">${o.name}</option>
                                                        </c:forEach>
                                                        --%>
                                                    </select>                                                
                                                </div>
                                            </div>

                                            <!-- 新增：隐藏字段，标记商品默认未删除 -->
                                            <input type="hidden" name="product-isDeleted" value="false">
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
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>