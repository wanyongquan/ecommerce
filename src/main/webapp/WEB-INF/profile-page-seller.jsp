<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/templates/head-admin.jsp"/>

<body>
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
	                <h1>个人中心</h1>
	            </div>
	            <div class="col-sm-6">
	                <ol class="breadcrumb float-sm-right">
	                    <li class="breadcrumb-item">
	                        <a href="${pageContext.request.contextPath}/">首页</a>
	                    </li>
	                    <li class="breadcrumb-item active">个人中心</li>
	                </ol>
	            </div>
	        </div>
	    </div>
	</section>
	<section class="content">
	    <div class="container-fluid">
                     
            <!-- Tab 导航 -->
			<ul class="nav nav-tabs mb-4" role="tablist">
			    <li class="nav-item">
			        <a class="nav-link active" data-toggle="tab" href="#tab-info" role="tab">个人信息</a>
			    </li>
			    <li class="nav-item">
			        <a class="nav-link" data-toggle="tab" href="#tab-avatar" role="tab">头像设置</a>
			    </li>
			</ul>
			
			<div class="tab-content">       
                        
                <!-- ================= 个人信息 Tab ================= -->
				<div class="tab-pane fade show active col-md-12" id="tab-info" role="tabpanel">
				    <form action="profile-page-seller" method="post">
	        		<input type="hidden" name="updateType" value="info">
	
	        		<h2 class="h3 mb-3 text-black">个人信息</h2>

                    <div class="p-3 p-lg-5 border">
                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="first-name" class="text-black">
                                    名 <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="first-name" name="first-name"
                                       value="${account.firstName}">
                            </div>

                            <div class="col-md-6">
                                <label for="last-name" class="text-black">
                                    姓 <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="last-name" name="last-name"
                                       value="${account.lastName}">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="address" class="text-black">
                                    地址 <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="address" name="address"
                                       value="${account.address}">
                            </div>
                        </div>

                        <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="email" class="text-black">
                                    电子邮箱 <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="email" name="email"
                                       value="${account.email}">
                            </div>
                            <div class="col-md-6">
                                <label for="phone" class="text-black">
                                    手机号码 <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="phone" name="phone"
                                       value="${account.phone}">
                            </div>                      
                        </div>
                        <div class="form-group">
                            <label for="summary" class="text-black">
                                个人简介
                            </label>
                            <textarea name="summary" id="summary" cols="30" rows="5" class="form-control"
                                      placeholder="在此填写您的个人备注..."></textarea>
                        </div>

                        <div class="form-group">
                            <button type="submit" onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'" class="btn btn-primary btn-lg py-3 btn-block">更新资料</button>
						 </div>
                         </div>
				</form>
				<!-- 账户信息（移入个人信息 Tab，独立区域） -->
                        <c:if test="${sessionScope.account.isSeller == 0}">
                            <div class="mt-4 p-3 p-lg-4 border">
                                <h3 class="h5 mb-3 text-black">账户信息</h3>
                                <div class="form-group col-md-6 pl-0">
                                    <label class="text-black">账户余额</label>
                                    <input type="text"
                                           readonly
                                           class="form-control"
                                           value="${account.balance}">
                                </div>
                            </div>
                        </c:if>
               </div>
            
             <!-- ================= 头像设置 Tab ================= -->
               <div class="tab-pane fade col-md-12" id="tab-avatar" role="tabpanel">
                   <form action="profile-page-seller" method="post" enctype="multipart/form-data">
                       <input type="hidden" name="updateType" value="avatar">

                       <h2 class="h3 mb-3 text-black">头像</h2>
						<!-- 居上对齐容器 --> 
                       <div class="border p-4 text-center">                   
                             <figure class="mb-3">
                                 <c:if test="${not empty account.base64Image}">
                                     <img id="blah"
                                          src="data:image/jpg;base64,${account.base64Image}"
                                          alt="个人头像"
                                          style="width:15em;height:15em;border-radius:50%;">
                                 </c:if>
                                 <c:if test="${empty account.base64Image}">
                                     <img id="blah"
                                          src="${pageContext.request.contextPath}/static/images/blank_avatar.png"
                                          alt="默认头像"
                                          style="width:15em;height:15em;border-radius:50%;">
                                 </c:if>
                             </figure>
                             <label class="d-block mb-3" for="imgInp"
                                     style="cursor:pointer;">
                                 点击此处更换头像                         
                         	</label>
                           <input name="profile-image" type="file"
                                  id="imgInp" style="display:none;">
                       </div>

                       <div class="text-center mt-3">
                           <button type="submit" class="btn btn-primary mt-2">
                               更新头像
                           </button>
                       </div>
                   </form>
               </div>
       
            </div>  
                        
       </div>
	</section>
	</div> <!-- /.content-wrapper -->

<%--     <jsp:include page="/templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->


<script>
    imgInp.onchange = evt => {
        const [file] = imgInp.files
        if (file) {
            blah.src = URL.createObjectURL(file)
        }
    }
</script>
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