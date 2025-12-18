<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="templates/head.jsp"/>

<body>
<div class="site-wrap">
    <% request.setAttribute("about_active", "active"); %>
    <jsp:include page="templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="index.jsp">首页</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">关于我们</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section border-bottom" data-aos="fade">
        <div class="container">
            <div class="row mb-5">
                <div class="col-md-6">
                    <div class="block-16">
                        <figure>
                            <img src="static/images/blog_1.jpg" alt="店铺实景" class="img-fluid rounded">
                            <a href="https://vimeo.com/channels/staffpicks/93951774" class="play-button popup-vimeo">
                                <span class="ion-md-play"></span>
                            </a>
                        </figure>
                    </div>
                </div>

                <div class="col-md-1">

                </div>

                <div class="col-md-5">
                    <div class="site-section-heading pt-3 mb-4">
                        <h2 class="text-black">品牌起源</h2>
                    </div>
                    <p>我们始终秉持匠心精神，致力于为消费者提供高品质的服饰产品。从创立之初，我们就坚持以用户需求为核心，不断优化产品设计与服务体验，力求让每一位顾客都能感受到舒适与时尚的完美结合。</p>
                    <p>多年来，我们深耕服饰行业，积累了丰富的行业经验，建立了完善的供应链体系和质量管控标准，只为给您带来安心、满意的购物体验。</p>

                </div>
            </div>
        </div>
    </div>

    <div class="site-section border-bottom" data-aos="fade">
        <div class="container">
            <div class="row justify-content-center mb-5">
                <div class="col-md-7 site-section-heading text-center pt-4">
                    <h2>核心团队</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 col-lg-3">
                    <div class="block-38 text-center">
                        <div class="block-38-img">
                            <div class="block-38-header">
                                <img src="static/images/person_1.jpg" alt="团队成员" class="mb-4">
                                <h3 class="block-38-heading h4">伊丽莎白·格雷厄姆</h3>
                                <p class="block-38-subheading">首席执行官/联合创始人</p>
                            </div>
                            <div class="block-38-body">
                                <p>拥有超过20年服饰行业管理经验，主导公司战略规划与品牌发展，始终坚持以用户价值为导向的经营理念。</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-3">
                    <div class="block-38 text-center">
                        <div class="block-38-img">
                            <div class="block-38-header">
                                <img src="static/images/person_2.jpg" alt="团队成员" class="mb-4">
                                <h3 class="block-38-heading h4">珍妮弗·格里夫</h3>
                                <p class="block-38-subheading">联合创始人</p>
                            </div>
                            <div class="block-38-body">
                                <p>专注于产品设计与研发，擅长将时尚趋势与实用功能相结合，打造符合消费者需求的优质服饰产品。</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-3">
                    <div class="block-38 text-center">
                        <div class="block-38-img">
                            <div class="block-38-header">
                                <img src="static/images/person_3.jpg" alt="团队成员" class="mb-4">
                                <h3 class="block-38-heading h4">帕特里克·马克思</h3>
                                <p class="block-38-subheading">市场营销总监</p>
                            </div>
                            <div class="block-38-body">
                                <p>具备丰富的品牌营销经验，擅长通过多元化渠道传递品牌价值，提升品牌知名度与市场影响力。</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-3">
                    <div class="block-38 text-center">
                        <div class="block-38-img">
                            <div class="block-38-header">
                                <img src="static/images/person_4.jpg" alt="团队成员" class="mb-4">
                                <h3 class="block-38-heading h4">迈克·库尔伯特</h3>
                                <p class="block-38-subheading">销售经理</p>
                            </div>
                            <div class="block-38-body">
                                <p>深耕销售领域多年，搭建了完善的销售体系与客户服务网络，致力于为客户提供专业、高效的销售服务。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section site-section-sm site-blocks-1 border-0" data-aos="fade">
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
                        <p>自收货之日起7天内，若您对商品不满意，可享受免费退换服务，我们将全程保障您的购物权益。</p>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4 d-lg-flex mb-4 mb-lg-0 pl-4" data-aos="fade-up" data-aos-delay="200">
                    <div class="icon mr-4 align-self-start">
                        <span class="icon-help"></span>
                    </div>
                    <div class="text">
                        <h2 class="text-uppercase">客户支持</h2>
                        <p>专业的客服团队随时为您解答购物过程中的各类问题，提供贴心的售后服务。</p>
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