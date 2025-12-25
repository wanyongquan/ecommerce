<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<jsp:include page="/templates/head.jsp"/>
<style>
    .star-rating i {
        font-size: 22px;
        color: #ccc;
        cursor: pointer;
        margin-right: 4px;
    }

    .star-rating i.active {
        color: #f7ba2a; /* 京东黄 */
    }
</style>

<body>
<div class="site-wrap">
     <jsp:include page="/templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0">
                    <a href="/">首页</a> <span class="mx-2 mb-0">/</span>
                    <strong class="text-black">个人资料</strong></div>

            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
	   <section class="content">
	    <div class="container-fluid">
	
	        <h3 class="mb-4">商品评价</h3>	        
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
	                <form action="${pageContext.request.contextPath}/order-comment"
	                      method="post">
	
	                    <!-- 隐藏字段 -->
	                    <input type="hidden" name="productId" value="${product.id}">
	                    <input type="hidden" name="orderId" value="${order_id}">
	                    
	
	                    <!-- 商品评分（京东风格） -->
						<div class="form-group mb-3">
						    <label class="d-block mb-1">商品评分：</label>
						
						    <div id="starRating" class="star-rating">
						        <i class="fas fa-star" data-value="1"></i>
						        <i class="fas fa-star" data-value="2"></i>
						        <i class="fas fa-star" data-value="3"></i>
						        <i class="fas fa-star" data-value="4"></i>
						        <i class="fas fa-star" data-value="5"></i>
						        
						        <span id="ratingText" class="ml-2 text-muted">
							    <c:choose>
							        <c:when test="${not empty product_comment}">
							            ${product_comment.rating} 分
							        </c:when>
							        <c:otherwise>
							            未评分
							        </c:otherwise>
							    </c:choose>
							</span>
						    </div>
						
						    <!-- 实际提交的评分值 -->
						  <!--   <input type="hidden" name="rating" id="ratingValue" required>  -->
						    <input type="hidden" name="rating" id="ratingValue"
						       value="${product_comment.rating}"
						       <c:if test="${not empty product_comment}">disabled</c:if> >
						</div>
	
	                    <!-- 评论内容 -->
	                    <div class="form-group">
	                        <textarea class="form-control text-start"
	                        			name="commentContent"
	                                  rows="3" required
	                                
	                                  placeholder="分享您的感受，对其他买家很有帮助"
								<c:if test="${not empty product_comment}">readonly</c:if>><c:out value="${product_comment.comment}" />
						    </textarea>
	                    </div>
	
	                    <!-- 提交按钮 -->
	                    <button type="submit" class="btn btn-primary btn-sm"
	                    	<c:if test="${not empty product_comment}">disabled</c:if>>
	                        提交评价
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
<script>
	const stars = document.querySelectorAll('#starRating i');
    const ratingValueInput = document.getElementById('ratingValue');
    const ratingText = document.getElementById('ratingText');

    const existingRating = ${product_comment.rating != null ? product_comment.rating : 0};

    if (existingRating > 0) {
        ratingValueInput.value = existingRating;
        ratingText.textContent = existingRating + ' 分';

        stars.forEach(s => {
            if (s.getAttribute('data-value') <= existingRating) {
                s.classList.add('active');
            }
        });
    }
    
    stars.forEach(star => {
        star.addEventListener('click', function () {
        	if (existingRating > 0) return;
            const value = this.getAttribute('data-value');

            // 设置隐藏评分值
            ratingValueInput.value = value;
            ratingText.textContent = value + ' 分';

            // 高亮前 value 颗星
            stars.forEach(s => {
                if (s.getAttribute('data-value') <= value) {
                    s.classList.add('active');
                } else {
                    s.classList.remove('active');
                }
            });
        });
    });
</script>

<jsp:include page="/templates/scripts.jsp"/>
</body>
</html>