<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <title>商家登录</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="static/images/logo.png"/>
    <link rel="stylesheet" type="text/css" href="static/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="static/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="static/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
    <link rel="stylesheet" type="text/css" href="static/vendor/animate/animate.css">
    <link rel="stylesheet" type="text/css" href="static/vendor/css-hamburgers/hamburgers.min.css">
    <link rel="stylesheet" type="text/css" href="static/vendor/animsition/css/animsition.min.css">
    <link rel="stylesheet" type="text/css" href="static/vendor/select2/select2.min.css">
    <link rel="stylesheet" type="text/css" href="static/vendor/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" type="text/css" href="static/css/util.css">
    <link rel="stylesheet" type="text/css" href="static/css/main.css">

    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/magnific-popup.css">
    <link rel="stylesheet" href="static/css/jquery-ui.css">
    <link rel="stylesheet" href="static/css/owl.carousel.min.css">
    <link rel="stylesheet" href="static/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="static/css/aos.css">
    <link rel="stylesheet" href="static/css/style.css">
    
    <style>
        .permission-denied {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>

<body>
<div class="limiter">
    <div class="container-login100">
        <div class="shadow-lg p-2 p-lg-5 rounded" data-aos="fade-up">
            <div class="wrap-login100 p-t-50 p-b-90">
                <!-- 权限不足提示 -->
                <c:if test="${param.error == 'permission_denied'}">
                    <div class="permission-denied">
                        <i class="fa fa-exclamation-circle"></i> 
                        您没有权限访问商家后台，请使用商家账号登录！
                    </div>
                </c:if>
                
                <form action="login_s?status=typed" method="post" class="login100-form validate-form flex-sb flex-w" id="loginForm">
                    <!-- 登录标题 -->
                    <span class="login100-form-title p-b-51">
                        商家登录
                    </span>

                    <!-- 登录错误提示 -->
                    ${alert}

                    <div class="wrap-input100 validate-input m-b-16" data-validate="用户名不能为空">
                        <input class="input100" type="text" name="username" placeholder="用户名" value="${cookie.username.value}">
                    </div>

                    <div class="wrap-input100 validate-input m-b-16" data-validate="密码不能为空">
                        <input class="input100" type="password" name="password" placeholder="密码">
                    </div>

                    <div class="flex-sb-m w-full p-t-3 p-b-24">
                        <div class="contact100-form-checkbox">
                            <input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me-checkbox" 
                                   <c:if test="${not empty cookie.username}">checked</c:if>>
                            <label class="label-checkbox100" for="ckb1">
                                记住我
                            </label>
                        </div>

                        <div>
                            <a href="#" class="txt1">
                                忘记密码？
                            </a>
                        </div>
                    </div>

                    <div class="container-login100-form-btn m-t-17">
                        <button type="submit" class="login100-form-btn">
                            登录
                        </button>
                    </div>
                </form>
            </div>

            <div class="text-center">
                <p class="txt1" style="color: #999999">
                    还没有账号？
                    <a href="register_seller.jsp" class="txt1">
                        立即注册
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>

<div id="dropDownSelect1"></div>

<script src="static/vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="static/vendor/animsition/js/animsition.min.js"></script>
<script src="static/vendor/bootstrap/js/popper.js"></script>
<script src="static/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="static/vendor/select2/select2.min.js"></script>
<script src="static/vendor/daterangepicker/moment.min.js"></script>
<script src="static/vendor/daterangepicker/daterangepicker.js"></script>
<script src="static/vendor/countdowntime/countdowntime.js"></script>

<script src="static/js/jquery-3.3.1.min.js"></script>
<script src="static/js/jquery-ui.js"></script>
<script src="static/js/popper.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/owl.carousel.min.js"></script>
<script src="static/js/jquery.magnific-popup.min.js"></script>
<script src="static/js/aos.js"></script>
<script src="static/js/main.js"></script>

<!-- 简化的表单验证脚本 -->
<script>
    // 表单提交验证
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        // 检查用户名密码
        const username = document.querySelector('input[name="username"]').value.trim();
        const password = document.querySelector('input[name="password"]').value.trim();
        
        if (!username) {
            e.preventDefault();
            alert('请输入用户名');
            return false;
        }
        
        if (!password) {
            e.preventDefault();
            alert('请输入密码');
            return false;
        }
        
        return true;
    });
    
    // 页面加载时自动填充记住的用户名
    window.onload = function() {
        // 保留用户名自动填充功能
    }
</script>

</body>
</html>