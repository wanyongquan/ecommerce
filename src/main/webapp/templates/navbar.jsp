<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	     		<c:if test="${account.base64Image != null}">
                    <img class="icon" src="data:image/jpg;base64,${account.base64Image}"
                         id="dropdownMenuReference"
                         data-toggle="dropdown" alt="image"
                         style="width: 1.5em; border-radius: 50%; margin-right: 10px; margin-bottom: 10px">
                </c:if>    	
	        	<c:if test="${account.base64Image == null}">
                    <img class="icon" src="../static/images/blank_avatar.png"
                         id="dropdownMenuReference"
                         data-toggle="dropdown" alt="image"
                         style="width: 1.5em; border-radius: 50%; margin-right: 10px; margin-bottom: 10px">
                </c:if>
	            <li class="nav-item dropdown">
			        <a class="nav-link" data-toggle="dropdown" href="#" aria-haspopup="true" aria-expanded="false">
			            <i class="fas fa-user-circle"></i>
			            <span class="ml-1">
			                <c:out value="${sessionScope.account.firstName}"/>
			            </span>
			        </a>
			
			        <div class="dropdown-menu dropdown-menu-right">
			            <span class="dropdown-item-text">
			                <strong>
			                    <c:out value="${sessionScope.account.firstName}"/>
			                    <c:out value="${sessionScope.account.lastName}"/>
			                </strong>
			            </span>
			
			            <div class="dropdown-divider"></div>
			
			            <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
			                <i class="fas fa-sign-out-alt mr-2"></i> 退出
			            </a>
			        </div>
			    </li>
	        </ul>
	    </nav>
	    <!-- /.navbar -->