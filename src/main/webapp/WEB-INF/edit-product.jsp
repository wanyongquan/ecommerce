<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="zh-CN">

<jsp:include page="/templates/head-admin.jsp" />

<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<!-- 顶部 navbar -->
		<jsp:include page="/templates/navbar.jsp" />
		<!-- 左侧 Sidebar -->
		<jsp:include page="/templates/sidebar.jsp" />

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
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/">首页</a></li>
								<li class="breadcrumb-item active">个人中心</li>
							</ol>
						</div>
					</div>
				</div>
			</section>



			<section class="content">
				<div class="container-fluid">

					<!--     <h2 class="h3 mb-3 text-black">商品信息</h2> -->
					<ul class="nav nav-tabs mb-3" role="tablist">
						<li class="nav-item"><a class="nav-link active"
							data-toggle="tab" href="#tab-info">基本信息</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#tab-image">商品图片</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#tab-spec">商品规格</a></li>
					</ul>
		
				<div class="row">
					<div class="col-md-7">
						<div class="tab-content">
							<div class="tab-pane fade show active" id="tab-info">

								<form action="edit-product" method="post">
									<input type="hidden" name="action" value="edit-info">
									<div class="p-3 border">
										<div class="form-group row">
											<div class="col-md-12">
												<label for="id" class="text-black"> 商品ID <span
													class="text-danger">*</span>

												</label> <input name="product-id" type="text" class="form-control"
													id="id" value="${product.id}" readonly>
											</div>
										</div>

										<div class="form-group row">
											<div class="col-md-12">
												<label for="name" class="text-black"> 商品名称 <span
													class="text-danger">*</span>

												</label> <input name="product-name" type="text" class="form-control"
													id="name" value="${product.name}">
											</div>
										</div>



										<div class="form-group row">
											<div class="col-md-12">
												<label for="price" class="text-black"> 商品价格 <span
													class="text-danger">*</span>

												</label> <input name="product-price" type="number"
													class="form-control" id="price" value="${product.price}">
											</div>
										</div>

										<div class="form-group row">
											<div class="col-md-12">
												<label for="description" class="text-black"> 商品描述 <span
													class="text-danger">*</span>

												</label>

												<textarea name="product-description" id="description"
													cols="30" rows="7" class="form-control">${product.description}</textarea>
											</div>
										</div>


										<div class="form-group row">
											<%-- <div class="col-md-12">
												<label for="category" class="text-black"> 商品分类 <span
													class="text-danger">*</span>

												</label> <select name="product-category" id="category"
													class="form-control">
													<c:forEach items="${category_list}" var="o">
														<option value="${o.id}">${o.name}</option>
													</c:forEach>
												</select>
											</div> --%>
											<div class="col-md-12">
										        <label class="text-black">
										            商品分类 <span class="text-danger">*</span>
										        </label>
										
										        <div class="form-check">
										            <c:forEach items="${category_list}" var="o">
										                <div class="form-check">
										                    <input class="form-check-input"
										                           type="radio"
										                           name="product-category"
										                           id="cat-${o.id}"
										                           value="${o.id}"
										                           ${o.id == product.category.id ? "checked" : ""}>
										                    <label class="form-check-label" for="cat-${o.id}">
										                        ${o.name}
										                    </label>
										                </div>
										            </c:forEach>
										        </div>
										    </div>
										</div>

										<div class="form-group row">
											<div class="col-lg-12">

												<input type="submit"
													class="btn btn-primary btn-lg btn-block" value="保存修改">

											</div>
										</div>
									</div>
								</form>

							</div>
							<!-- end of tabPane -->
							<div class="tab-pane fade" id="tab-image">
								<form action="edit-product" method="post" enctype="multipart/form-data">
									<input type="hidden" name="action" value="edit-image">
									<input type="hidden" name="product-id" value="${product.id}">
									<div class="form-group">
										<label class="text-black">更新图片</label> 
									</div>

								<div class="border p-4 text-center">
								
								            <!-- 图片预览 -->
								            <figure class="mb-3">
								                <c:if test="${not empty product.base64Image}">
								                    <img
								                        id="productPreview"
								                        src="data:image/jpg;base64,${product.base64Image}"
								                        alt="商品图片"
								                        class="img-fluid rounded"
								                        style="max-width: 300px;">
								                </c:if>
								
								                <c:if test="${empty product.base64Image}">
								                    <img
								                        id="productPreview"
								                        src="${pageContext.request.contextPath}/static/images/no-image.png"
								                        alt="默认商品图片"
								                        class="img-fluid rounded"
								                        style="max-width: 300px;">
								                </c:if>
								            </figure>
								            
										<!-- 选择文件 -->
								            <label for="productImgInput"
								                   class="d-block mb-3 text-primary"
								                   style="cursor:pointer;">
								                点击此处选择本地图片
								            </label>
									 <input
						                type="file"
						                name="product-image"
						                id="productImgInput"
						                accept="image/*"
						                style="display:none;">
								 	</div>               
									
									<input type="submit"  id="updateImageBtn" class="btn btn btn-secondary btn-block" title="请先选择图片文件" value="更新图片" disabled>
								</form>
							</div>
							 <!-- 商品规格编辑 -->
							<div class="tab-pane fade" id="tab-spec">
							    <form action="edit-product" method="post">
							        <input type="hidden" name="action" value="edit-sku">
							        <input type="hidden" name="product-id" value="${product.id}">
							
							        <div class="p-3 border">
							
							            <label class="text-black mb-2">
							                商品规格与库存
							            </label>
							
							            <div id="sku-container">
							
							                <!-- ===== 已有 SKU（不可删除） ===== -->
							                <c:forEach items="${product_sku_list}" var="sku">
							                    <div class="row sku-row mb-2 align-items-center existing-sku">
							                        <div class="col-md-2">
							                            <input type="text"
							                                   class="form-control"
							                                   name="sku-id[]"
							                                   value="${sku.id}"
							                                   readonly>
							                        </div>
							                        <div class="col-md-5">
							                            <input type="text"
							                                   class="form-control"
							                                   name="sku-color-${sku.id}"
							                                   value="${sku.colorName}"
							                                   required>
							                        </div>
							                        <div class="col-md-3">
							                            <input type="number"
							                                   class="form-control"
							                                   name="sku-qty-${sku.id}"
							                                   value="${sku.amount}"
							                                   min="0"
							                                   required>
							                        </div>
							                        <div class="col-md-2 text-center">
							                            <span class="text-muted">不可删除</span>
							                        </div>
							                    </div>
							                </c:forEach>
							
							            </div>
							
							            <!-- ===== 新增 SKU 按钮 ===== -->
							            <div class="mt-3">
							                <button type="button" id="add-sku-row"
							                        class="btn btn-secondary btn-sm">
							                    + 添加规格
							                </button>
							            </div>
							
							            <div class="mt-4">
							                <button type="submit"
							                        class="btn btn-primary btn-block">
							                    更新商品规格
							                </button>
							            </div>
							        </div>
							    </form>
							</div>



						</div>
					</div>
					<div class="col-md-5 ml-auto">
						<div class="p-3 border text-center">

							<img src="data:image/jpg;base64,${product.base64Image}"
								alt="商品图片" class="img-fluid w-50">

						</div>
					</div>
				</div>
		   </div>
		</section>
		</div>

		<%--     <jsp:include page="../templates/footer.jsp"/> --%>
	</div><!-- /.wrapper -->

	<jsp:include page="../templates/scripts.jsp" />
	<script>
	    const productImgInput = document.getElementById('productImgInput');
	    const productPreview = document.getElementById('productPreview');
	

    const updateImageBtn = document.getElementById('updateImageBtn');

    productImgInput.onchange = () => {
        const file = productImgInput.files[0];

        if (file) {
            // 图片预览
            productPreview.src = URL.createObjectURL(file);

            // 启用按钮
            updateImageBtn.disabled = false;
         // 启用按钮（样式）
            updateImageBtn.classList.remove('btn-secondary');
            updateImageBtn.classList.add('btn-info');
        } else {
            // 未选择文件，禁止提交
            updateImageBtn.disabled = true;
            // 禁用按钮（样式）
            updateImageBtn.classList.remove('btn-info');
            updateImageBtn.classList.add('btn-secondary');
        }
    };
</script>


<c:if test="${not empty alertMsg}">
<div class="modal fade" id="alertModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">

            <div class="modal-header bg-${alertType}">
                <h5 class="modal-title text-white">
                    提示
                </h5>
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>

            <div class="modal-body text-center">
                <p>${alertMsg}</p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary"
                        data-dismiss="modal">确定</button>
            </div>

        </div>
    </div>
</div>

<script>
    $(function () {
        $('#alertModal').modal('show');
    });
</script>
</c:if>

<script>
    document.getElementById('add-sku-row').addEventListener('click', function () {

        const container = document.getElementById('sku-container');

        const row = document.createElement('div');
        row.className = 'row sku-row mb-2 align-items-center new-sku';

        row.innerHTML = `
            <div class="col-md-2">
                <input type="hidden" name="new-sku-flag[]" value="1">
                <input type="text"
                       class="form-control"
                       name="new-sku-id[]"
                       value="NEW"
                       readonly>
            </div>
            <div class="col-md-5">
                <input type="text"
                       class="form-control"
                       name="new-sku-color[]"
                       placeholder="颜色 / 规格"
                       required>
            </div>
            <div class="col-md-3">
                <input type="number"
                       class="form-control"
                       name="new-sku-qty[]"
                       min="0"
                       required>
            </div>
            <div class="col-md-2 text-center">
                <button type="button"
                        class="btn btn-danger btn-sm remove-sku">
                    删除
                </button>
            </div>
        `;

        container.appendChild(row);
    });

    document.addEventListener('click', function (e) {
        if (e.target.classList.contains('remove-sku')) {
            e.target.closest('.sku-row').remove();
        }
    });
</script>


</body>
</html>
