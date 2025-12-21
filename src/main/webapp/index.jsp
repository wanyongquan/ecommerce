<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>

<html lang="zh-CN">
<jsp:include page="templates/head.jsp"/>
<body>
<div class="site-wrap">
    <jsp:include page="templates/header.jsp"/>

    <div class="site-blocks-cover" style="background-image: url(static/images/hero_1.jpg);" data-aos="fade">
        <div class="container">
            <div class="row align-items-start align-items-md-center justify-content-end">
                <div class="col-md-5 text-center text-md-left pt-5 pt-md-0">

                    <h1 class="mb-2">找到属于你的完美鞋履</h1>

                    <div class="intro-text text-center text-md-left">
                        <p class="mb-4">我们精选各类优质鞋款，兼顾舒适与时尚，满足你不同场景的穿搭需求。</p>

                        <p>
                            <a href="shop" class="btn btn-sm btn-primary">立即选购</a>

                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section site-section-sm site-blocks-1">
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-truck"></span>
                    </div>

                    <div class="text">

                        <h2 class="text-uppercase">免费配送</h2>
                        <p>全国范围内免费配送，让你的心仪服饰快速送达。</p>

                    </div>
                </div>

                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="100">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-refresh2"></span>
                    </div>
                    <div class="text">

                        <h2 class="text-uppercase">免费退换</h2>
                        <p>7天无理由退换货，购物无忧，体验更贴心。</p>

                    </div>
                </div>

                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="200">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-help"></span>
                    </div>
                    <div class="text">

                        <h2 class="text-uppercase">客户支持</h2>
                        <p>专业客服团队解答你的购物疑问，提供贴心的售前售后全流程服务。</p>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/collections-section.jsp"/>

    <jsp:include page="templates/featured-products.jsp"/>

    <div class="site-section block-8">
        <div class="container">
            <div class="row justify-content-center  mb-5">
                <div class="col-md-7 site-section-heading text-center pt-4">

                    <h2>限时大促！</h2>

                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-md-12 col-lg-7 mb-5">

                    <a href="#"><img src="static/images/blog_1.jpg" alt="促销活动图" class="img-fluid rounded"></a>
                </div>
                <div class="col-md-12 col-lg-5 text-center pl-md-5">
                    <h2><a href="#">全场商品五折特惠</a></h2>                  
                    <p>本次大促覆盖全品类服饰，限时特惠不容错过！品质保障，价格亲民，快来挑选你的心仪好物。</p>
                    <p><a href="shop" class="btn btn-primary btn-sm">立即选购</a></p>

                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>