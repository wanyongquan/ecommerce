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
                    <a href="/">首页</a> <span class="mx-2 mb-0">/</span>
                    <strong class="text-black">系统错误</strong>
                </div>
            </div>
        </div>
    </div>
    
    <div class="site-section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card border-danger">
                        <div class="card-header bg-danger text-white">
                            <h3 class="mb-0"><i class="fas fa-exclamation-triangle mr-2"></i>系统错误</h3>
                        </div>
                        <div class="card-body">
                            <!-- 错误代码行 -->
                            <div class="row mb-3">
                                <div class="col-md-3 font-weight-bold text-right">
                                    <span class="text-muted">错误代码:</span>
                                </div>
                                <div class="col-md-9">
                                    <span class="badge badge-danger p-2">
                                        <c:choose>
                                            <c:when test="${not empty errorCode}">
                                                ${errorCode}
                                            </c:when>
                                            <c:otherwise>
                                                未知错误
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>
                            
                            <!-- 错误信息行 -->
                            <div class="row mb-4">
                                <div class="col-md-3 font-weight-bold text-right">
                                    <span class="text-muted">错误信息:</span>
                                </div>
                                <div class="col-md-9">
                                    <div class="alert alert-warning p-3" role="alert">
                                        <c:choose>
                                            <c:when test="${not empty errorMessage}">
                                                ${errorMessage}
                                            </c:when>
                                            <c:otherwise>
                                                系统发生未知错误，请稍后重试。
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- 错误详情（开发环境显示） -->
                            <c:if test="${not empty errorDetail}">
                                <div class="row mb-3">
                                    <div class="col-md-3 font-weight-bold text-right">
                                        <span class="text-muted">错误详情:</span>
                                    </div>
                                    <div class="col-md-9">
                                        <div class="bg-light p-3 border rounded" style="max-height: 200px; overflow-y: auto;">
                                            <small class="text-muted">${errorDetail}</small>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            
                            <!-- 时间戳 -->
                            <div class="row mb-4">
                                <div class="col-md-3 font-weight-bold text-right">
                                    <span class="text-muted">发生时间:</span>
                                </div>
                                <div class="col-md-9">
                                    <span class="text-secondary">
                                        <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) %>
                                    </span>
                                </div>
                            </div>
                            
                            <!-- 操作按钮 -->
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <div class="btn-group" role="group">
                                        <a href="javascript:history.back()" class="btn btn-outline-primary">
                                            <i class="fas fa-arrow-left mr-1"></i>返回上一页
                                        </a>
                                        <a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-primary">
                                            <i class="fas fa-home mr-1"></i>返回首页
                                        </a>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 错误说明 -->
                    <div class="mt-4 p-3 bg-light rounded">
                        <h5 class="text-muted mb-2"><i class="fas fa-info-circle mr-1"></i>说明:</h5>
                        <ul class="list-unstyled mb-0">
                            <li><small class="text-muted">• 如果是系统错误，请联系管理员。</small></li>
                            <li><small class="text-muted">• 如果是操作错误，请检查输入后重试。</small></li>
                            <li><small class="text-muted">• 错误代码可以帮助技术人员快速定位问题。</small></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%--      <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0">
                    <a href="/">首页</a> <span class="mx-2 mb-0">/</span>
                    <strong class="text-black">系统错误</strong></div>

	            </div>
	        </div>
	    </div>
	
	    <div class="site-section">
	        <div class="container">
	        
	    <h2>系统错误</h2>
	     <p>${errorCode}</p>
	    <p>${errorMessage}</p>
	
	    <a href="javascript:history.back()">返回上一页</a>
	    |
	    <a href="<%=request.getContextPath()%>/index.jsp">返回首页</a>
	  </div>
    </div> --%>
    
    <jsp:include page="templates/footer.jsp"/>
</div>



<jsp:include page="templates/scripts.jsp"/>
</body>
</html>
