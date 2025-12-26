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
	
	        <h3 class="mb-4">申请售后</h3>	        
	            <div class="card ">
	                <div class="card-body">
	                <div class="row">
	                	<!-- 左侧：商品信息 -->
                        <div class="col-md-4">
                        	<!-- 第一行：商品图片 -->
						    <div class="mb-3 text-center">
						        <img src="data:image/jpg;base64,${product.base64Image}"
						             style="width:150px;height:150px;object-fit:cover;"
						             class="img-fluid">
						    </div>
						
						    <!-- 第二行：商品名称 -->
						    <h5 class="mb-2 text-center">
						        ${product.name}
						    </h5>
						
						    <!-- 第三行：商品颜色 -->
						    <p class="text-muted text-center mb-0">
						        ${sku}
						    </p>                            
                        </div>
                        <!-- 右侧：评论表单 -->
	                        <div class="col-md-8">
					<!-- 表单开始 -->
	                <form action="${pageContext.request.contextPath}/after-sales-apply"
	                      method="post">
	
	                    <!-- 隐藏字段 -->
	                    <input type="hidden" name="productId" value="${product.id}">
	                    <input type="hidden" name="orderId" value="${order_id}">
	                    
						<div class="form-group mb-3">
						    <label>服务类型：</label>
						    <div>
						        <div class="form-check form-check-inline">
						            <input class="form-check-input" type="radio" id="serviceReturn" name="serviceType" value="RETURN" required>
						            <label class="form-check-label" for="serviceReturn">退货</label>
						        </div>
						        <div class="form-check form-check-inline">
						            <input class="form-check-input" type="radio" id="serviceExchange" name="serviceType" value="EXCHANGE">
						            <label class="form-check-label" for="serviceExchange">换货</label>
						        </div>
						        <div class="form-check form-check-inline">
						            <input class="form-check-input" type="radio" id="serviceRepair" name="serviceType" value="REPAIR">
						            <label class="form-check-label" for="serviceRepair">维修</label>
						        </div>
						        <div class="form-check form-check-inline">
						            <input class="form-check-input" type="radio" id="serviceResend" name="serviceType" value="RESEND">
						            <label class="form-check-label" for="serviceResend">补发商品</label>
						        </div>
						    </div>
						</div>
						<div class="form-group mb-3">
						    <label>提交原因：</label>
						    <select class="form-control" name="reason" required>
						        <option value="">请选择原因</option>
						        <option value="QUALITY">商品质量/故障</option>
						        <option value="DAMAGED">不喜欢/买多/买错</option>
						        <option value="WRONG_ITEM">商品破损/包装问题</option>
						        <option value="MISSING">少件/漏发/未收到货</option>
						        <option value="MISSING">商品与页面描述不符</option>
						        <option value="OTHER">其他</option>
						    </select>
						</div>
						<div class="form-group mb-3">
						    <label>问题描述：</label>
						    <textarea class="form-control"
						              name="description"
						              rows="4"
						              placeholder="请详细描述您遇到的问题"
						              required></textarea>
						</div>
													                   
	                    <!-- 提交按钮 -->
	                    <button type="submit" class="btn btn-primary btn-sm"
	                    	<c:if test="${not empty product_comment}">disabled</c:if>>
	                        确认提交
	                    </button>
	  				</form>       <!-- 表单结束 -->
	  				</div><!-- 评论结束 -->
	  				</div>
	                </div><!-- card-body -->
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