<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="templates/head.jsp"/>

<body>
<div class="site-wrap">
    <% request.setAttribute("contact_active", "active"); %>
    <jsp:include page="templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="index.jsp">首页</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">联系我们</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2 class="h3 mb-3 text-black">与我们取得联系</h2>
                </div>

                <div class="col-md-7">
                    <form action="#" method="post">
                        <div class="p-3 p-lg-5 border">
                            <div class="form-group row">
                                <div class="col-md-6">
                                    <label for="c_fname" class="text-black">名 <span
                                            class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="c_fname" name="c_fname">
                                </div>

                                <div class="col-md-6">
                                    <label for="c_lname" class="text-black">姓 <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="c_lname" name="c_lname">
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="c_email" class="text-black">电子邮箱 <span
                                            class="text-danger">*</span></label>
                                    <input type="email" class="form-control" id="c_email" name="c_email" placeholder="请输入您的邮箱地址">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="c_subject" class="text-black">主题 </label>
                                    <input type="text" class="form-control" id="c_subject" name="c_subject" placeholder="请输入咨询主题">
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="c_message" class="text-black">留言内容 </label>
                                    <textarea name="c_message" id="c_message" cols="30" rows="7"
                                              class="form-control" placeholder="请详细描述您的问题或建议"></textarea>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-lg-12">
                                    <input type="submit" class="btn btn-primary btn-lg btn-block" value="发送留言">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="col-md-5 ml-auto">
                    <div class="p-4 border mb-3">
                        <span class="d-block text-primary h6 text-uppercase">纽约分部</span>
                        <p class="mb-0">美国加利福尼亚州旧金山市山景城假街203号</p>
                    </div>

                    <div class="p-4 border mb-3">
                        <span class="d-block text-primary h6 text-uppercase">伦敦分部</span>
                        <p class="mb-0">美国加利福尼亚州旧金山市山景城假街203号</p>
                    </div>

                    <div class="p-4 border mb-3">
                        <span class="d-block text-primary h6 text-uppercase">加拿大分部</span>
                        <p class="mb-0">美国加利福尼亚州旧金山市山景城假街203号</p>
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