<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/templates/head-admin.jsp"/>

<body class="hold-transition sidebar-mini">
	<div class="wrapper">
	<!-- 顶部 navbar -->
   <jsp:include page="/templates/navbar.jsp"/>
	    
    <!-- 左侧 Sidebar -->
   	<jsp:include page="/templates/sidebar.jsp"/>
    

	<!-- Content Wrapper -->
   	<div class="content-wrapper">
    
    <section class="content-header">
	    <div class="container-fluid">
   	        <div class="row mb-2">
	            <div class="col-sm-6">
	                <h1>店铺信息</h1>
	            </div>
	            <div class="col-sm-6">
	                <ol class="breadcrumb float-sm-right">
	                    <li class="breadcrumb-item">
	                        <a href="${pageContext.request.contextPath}/">首页</a>
	                    </li>
	                    <li class="breadcrumb-item active">店铺中心</li>
	                </ol>
	            </div>
	        </div>
	    </div>
	</section>
	<section class="content">
	    <div class="container-fluid">
 
            <!-- 新增：店铺信息管理区域 -->
             <c:if test="${sessionScope.account.isSeller == 1}">
            <div class="row mb-5">
                <div class="col-md-12">
                    <h2 class="h3 mb-3 text-black">店铺信息管理</h2>
                     ${alert}
                    <form action="shop-management" method="post" class="border p-4 bg-light">
                        <div class="form-group">
                            <label for="shop_name" class="text-black font-weight-bold">
                                店铺名称 <span class="text-danger">*</span>
                            </label>
                            <input type="text" class="form-control" id="shop_name" name="shop_name" 
                                   value="${account_shop.shopName}" required
                                   placeholder="请输入您的店铺名称">
                        </div>
                        
                        <div class="form-group">
                            <label for="shop_description" class="text-black font-weight-bold">
                                店铺说明
                            </label>
                            <textarea class="form-control" id="shop_description" name="shop_description" 
                                      rows="4" placeholder="请简要描述您的店铺特色、主营商品等...">${account_shop.shopDescription}</textarea>
                            <small class="form-text text-muted">店铺说明将显示在店铺首页，建议简洁明了。</small>
                        </div>
                        
                        <div class="form-group mt-4">
                            <button type="submit" class="btn btn-success btn-lg">
                                <i class="icon icon-save"></i> 更新店铺信息
                            </button>
                            
                        </div>
                        
                        <!-- 可选：显示操作反馈 -->
                        <c:if test="${not empty shop_update_msg}">
                            <div class="alert alert-info mt-3">
                                ${shop_update_msg}
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
            </c:if>
       </div>
	</section>
	</div> <!-- /.content-wrapper -->

<%--     <jsp:include page="/templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->

<jsp:include page="/templates/scripts.jsp"/>
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
</body>
</html>