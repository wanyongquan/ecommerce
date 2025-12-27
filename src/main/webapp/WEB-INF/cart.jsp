<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>

<html lang="zh-CN">

<jsp:include page="/templates/head.jsp"/>

<body>
<div class="site-wrap">
     <jsp:include page="/templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">

                <div class="col-md-12 mb-0"><a href="/">首页</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">购物车</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <form class="container" method="post" action="checkout">
            <div class="row mb-5">
                <div class="col-md-12">
                    <div class="site-blocks-table">
                        <table class="table table-bordered">
                            <thead>
                            <tr>

                                <th class="product-thumbnail">商品图片</th>
                                <th class="product-name">商品名称</th>
                                <th class="product-name">规格</th>
                                <th class="product-price">单价</th>
                                <th class="product-quantity">购买数量</th>
                                <th class="product-total">小计</th>
                                <th class="product-remove">移除</th>

                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${order.cartProducts}" var="o">
                                <tr>
                                    <td class="product-thumbnail">

                                        <img src="data:image/jpg;base64,${o.product.base64Image}" alt="商品图片"


                                             class="img-fluid">
                                    </td>

                                    <td>
                                        <input name="product-name" class="form-control-plaintext h5 text-black"
                                               value="${o.product.name}" style="text-align: center" readonly>
                                    </td>
									<td>
                                        <input name="color" class="form-control-plaintext h5 text-black"
                                               value="${o.pickedColor}" style="text-align: center" readonly>
                                    </td>
                                    <td>
                                        <input name="product-price" class="form-control-plaintext h5 text-black"
                                               value="${o.price}" style="text-align: center" readonly>
                                    </td>

                                    <td style="min-width: 180px">
                                        <div class="input-group" style="max-width: fit-content; margin: 0;">
                                            <div class="input-group-prepend">
                                                <button class="btn btn-outline-primary js-btn-minus" type="button">
                                                    &minus;
                                                </button>
                                            </div>

                                            <input name="product-quantity" type="text" class="form-control text-center"
                                                   value="${o.quantity}"
                                                   placeholder=""
                                                   aria-label="Example text with button addon"
                                                   aria-describedby="button-addon1">

                                            <div class="input-group-append">
                                                <button class="btn btn-outline-primary js-btn-plus" type="button">
                                                    &plus;
                                                </button>
                                            </div>
                                        </div>
                                    </td>

                                    <td>
                                        <input name="product-price-total" class="form-control-plaintext h5 text-black"
                                               value="${o.price * o.quantity}" style="text-align: center" readonly>
                                    </td>


                                    <td><a href="cart?remove-product-id=${o.product.id}&color=${o.pickedColor}" class="btn btn-primary btn-sm">X</a></td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="row mb-5">
                        <div class="col-md-6 mb-3 mb-md-0">

                            <a href="shop" class="btn btn-outline-primary btn-sm btn-block">继续购物</a>

                        </div>
                    </div>
                    
                </div>
                <div class="col-md-6 pl-5">
                    <div class="row justify-content-end">
                        <div class="col-md-7">
                            <div class="row">
                                <div class="col-md-12 text-right border-bottom mb-5">

                                    <h3 class="text-black h4 text-uppercase">购物车总计</h3>

                                </div>
                            </div>
                            <div class="row mb-5">
                                <div class="col-md-6">

                                    <span class="text-black" style="font-size: 1.5em">总计</span>

                                </div>

                                <div class="col-md-6 text-right">
                                    <input name="order-price-total" class="form-control-plaintext h5 text-black"
                                           value="${order.total}" style="text-align: center" readonly>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary btn-lg py-3 btn-block">

                                        去结算

                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

     <%--     <jsp:include page="../templates/footer.jsp"/> --%>
</div><!-- /.wrapper -->

<jsp:include page="../templates/scripts.jsp"/>
<style>
<script>
$(document).ready(function() {
    // 计算单行小计
    function calculateRowSubtotal(row) {
        var price = parseFloat(row.find('input[name="product-price"]').val()) || 0;
        var quantity = parseInt(row.find('input[name="product-quantity"]').val()) || 0;
        return price * quantity;
    }
    
    // 更新总计价格
    function updateTotalPrice() {
        var total = 0;
        
        // 遍历每一行商品
        $('tbody tr').each(function() {
            total += calculateRowSubtotal($(this));
        });
        
        // 更新总计显示
        $('input[name="order-price-total"]').val(total.toFixed(2));
    }
    
 // 更新单行小计
    function updateRowSubtotal(row) {
        var subtotal = calculateRowSubtotal(row);
        row.find('input[name="product-price-total"]').val(subtotal.toFixed(2));
        updateTotalPrice();
    }
    // 监听数量输入框的变化（包括手动输入和按钮修改）
    $(document).on('change input', 'input[name="product-quantity"]', function() {
        
        var $input = $(this);
        var quantity = parseInt($input.val()) || 0;
        var $row = $input.closest('tr');
        
        // 如果输入的数量为0，执行删除操作
        if (quantity === 0) {
            var $removeBtn = $row.find('a[href*="remove-product-id="]');
            if ($removeBtn.length > 0) {
                window.location.href = $removeBtn.attr('href');
                return; // 不继续执行价格更新
            }
        }
    	updateRowSubtotal($(this).closest('tr'));
    });
    
 // ===== 关键修改：监控加减按钮点击事件 =====
    $(document).on('click', '.js-btn-plus, .js-btn-minus', function() {
 
        var $button = $(this);
        var isMinusBtn = $button.hasClass('js-btn-minus');
        
        // 给加减按钮点击事件一个小的延迟，确保数量输入框的值已经更新
        setTimeout(function() {
            // 对于减号按钮，需要检查数量是否为0
            if (isMinusBtn) {
                var $input = $button.closest('.input-group').find('input[name="product-quantity"]');
                var quantity = parseInt($input.val()) || 0;
                
                // 如果数量为0，执行删除操作
                if (quantity === 0) {
                    // 找到同行的删除按钮并触发点击
                    var $removeBtn = $button.closest('tr').find('a[href*="remove-product-id="]');
                    if ($removeBtn.length > 0) {
                        // 跳转到删除URL，移除商品
                        window.location.href = $removeBtn.attr('href');
                        return; // 不需要再更新价格，因为页面会刷新
                    }
                }
            }
         	// 正常情况：找到最近的商品行并更新价格
            $('input[name="product-quantity"]').each(function() {
                updateRowSubtotal($(this).closest('tr'));
            });
        }, 10);
    });
 
    // 页面加载时计算一次
    updateTotalPrice();
});
</script>
</body>
</html>