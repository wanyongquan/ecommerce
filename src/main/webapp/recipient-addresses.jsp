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
                    <strong class="text-black">个人资料</strong></div>

            </div>
        </div>
    </div>


        <div class="container">
    		<div class="row mb-4">
                <div class="col-md-12">
                    <!-- 第一行：创建收货地址按钮 -->
                    <div class="text-center mb-4">
                        <button type="button" class="btn btn-primary btn-lg" 
                                data-toggle="modal" data-target="#addAddressModal">
                            <span class="icon icon-plus"></span> 创建收货地址
                        </button>
                    </div>
                </div>
            </div>

            <!-- 第二行开始：显示地址列表 -->
            <div class="row">
                <div class="col-md-12">
                    <c:if test="${empty address_list}">
                        <div class="text-center p-5 border bg-light">
                            <h4 class="text-black mb-3">暂无收货地址</h4>
                            <p class="text-muted">点击上方按钮创建第一个收货地址</p>
                        </div>
                    </c:if>
                    

                </div>
            </div>
            
            <div class="row mb-5">
            	<div class="col-md-12">
                      <c:forEach items="${address_list}" var="address" varStatus="status">
                      <form method="post" action="recipient-addresses" class="address-form">
        				<input type="hidden" name="addr_id" value="${address.addr_id}">
                        <div class="card border">
	                        <div class="card-header bg-light">
                                <div class="row align-items-center">
                                    <div class="col-md-8">
                                        <h5 class="mb-0 text-black">
                                            <c:if test="${not empty address.addressLabel}">
                                                <span class="badge badge-info mr-2">${address.addressLabel}</span>
                                            </c:if>
                                            
                                            <c:if test="${address.isDefault==1}">
                                                <span class="badge badge-success ml-2">默认地址</span>
                                            </c:if>
                                        </h5>
                                    </div>
                                    <div class="col-md-4 text-right">
                                        <c:if test="${address.isDefault != 1}">
                                            <button class="btn btn-sm btn-outline-success mr-2"  type="submit" name="action" value="setDefault" >
                                                设为默认
                                              </button>
                                        </c:if>
                                        <button class="btn btn-sm btn-outline-primary mr-2 edit-btn"  type="button"
                                                
                                                data-addr-id="${address.addr_id}"
										        data-recipient-name="${address.recipientName}"
										        data-phone="${address.phone}"
										        data-address="${address.distinct}"
										        data-address-detail="${address.address_detail}"
										        data-email="${address.email}"
										        data-address-label="${address.addressLabel}"
										        data-is-default="${address.isDefault}">
                                            编辑
                                        </button>
                                        <button class="btn btn-sm btn-outline-danger"  type="submit" name="action" value="delete"
                                                onclick="confirmDelete(${address.addr_id})">
                                            删除
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                              <div class="mb-2">
						        <span class="text-black">收件人：</span>
						        <span>${address.recipientName}</span>
						    </div>
						    <div class="mb-2">
						        <span class="text-black">电话：</span>
						        <span>${address.phone}</span>
						    </div>
						    
						    <div class="mb-2">
						        <span class="text-black">地区：</span>
						        <span>${address.distinct}</span>
						    </div>
						    
						    <div class="mb-2">
						        <span class="text-black">详细地址：</span>
						        <span>${address.address_detail}</span>
						    </div>                             
                                
                            </div>
                        </div>
                         </form>
                    </c:forEach>
                  </div>
            </div>

            <!-- 添加地址模态对话框 -->
            <div class="modal fade" id="addAddressModal" tabindex="-1" role="dialog" 
                 aria-labelledby="addAddressModalLabel" aria-hidden="true"  style="z-index:2600">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title text-black" id="addAddressModalLabel">创建收货地址</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form id="addressForm" method="post" action="recipient-addresses">
                            <div class="modal-body">
                            	<!-- 添加隐藏字段用于区分操作 -->
                                <input type="hidden" name="action" id="formAction"  value="create">
                                <input type="hidden" name="addr_id" id="formAddrId" value="">
                                <div class="form-group row">
                                    <div class="col-md-12">
                                        <label for="recipientName" class="text-black font-weight-bold">
                                            收货人姓名 <span class="text-danger">*</span>
                                        </label>
                                        <input type="text" class="form-control" id="recipientName" 
                                               name="recipientName" required>
                                    </div>
                                    
                                </div>
                                <div class="form-group row">
                                    <div class="col-md-12">
                                        <label for="address" class="text-black font-weight-bold">
                                            所在地区 <span class="text-danger">*</span>
                                        </label>
                                        <textarea class="form-control" id="address" name="address" 
                                                  rows="3" required placeholder="填写省-市-区-街道"></textarea>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-md-12">
                                        <label for="address" class="text-black font-weight-bold">
                                            详细地址 <span class="text-danger">*</span>
                                        </label>
                                        <textarea class="form-control" id="addressDetail" name="address-detail" 
                                                  rows="3" required placeholder="请填写完整的收货地址"></textarea>
                                    </div>
                                </div>
                                
                                <div class="form-group row">
                                    <div class="col-md-6">
                                        <label for="phone" class="text-black font-weight-bold">
                                            电话 <span class="text-danger">*</span>
                                        </label>
                                        <input type="tel" class="form-control" id="phone" name="phone" 
                                               required placeholder="手机或固话">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="email" class="text-black font-weight-bold">邮箱</label>
                                        <input type="email" class="form-control" id="email" name="email" 
                                               placeholder="可选填写">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    
                                    <div class="col-md-12">
                                        <label for="alias" class="text-black font-weight-bold">地址别名</label>
                                        <input type="text" class="form-control" id="alias" name="address-label" 
                                               placeholder="例如：家里、公司、父母家等">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" 
                                               id="isDefault" name="isDefault" value="1">
                                        <label class="custom-control-label text-black" for="isDefault">
                                            设为默认收货地址
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="submit" class="btn btn-primary" id="saveAddressBtn">保存收货地址</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
       
        </div>


    <jsp:include page="templates/footer.jsp"/>
</div>


<script>
    imgInp.onchange = evt => {
        const [file] = imgInp.files
        if (file) {
            blah.src = URL.createObjectURL(file)
        }
    }
</script>
<jsp:include page="templates/scripts.jsp"/>

<script>
$(document).ready(function() {
    // 表单验证
    $('#addressForm').submit(function(e) {
        var recipientName = $('#recipientName').val().trim();
        var address = $('#address').val().trim();
        var phone = $('#phone').val().trim();
        
        if (!recipientName) {
            alert('请填写收件人姓名');
            e.preventDefault();
            return false;
        }
        
        if (!address) {
            alert('请填写详细地址');
            e.preventDefault();
            return false;
        }
        
        if (!phone) {
            alert('请填写联系电话');
            e.preventDefault();
            return false;
        }
        
        // 电话格式验证
        var phonePattern = /^1[3-9]\d{9}$|^\d{3,4}-\d{7,8}$/;
        if (!phonePattern.test(phone)) {
            alert('请填写正确的电话号码格式（手机号或固话）');
            e.preventDefault();
            return false;
        }
        
        return true;
    });
    
 // 为删除按钮添加确认提示
    $('button[name="action"][value="delete"]').click(function(e) {
        if (!confirm('确定要删除这个收货地址吗？')) {
            e.preventDefault(); // 阻止表单提交
            return false;
        }
    });
    /* // 确认删除
    window.confirmDelete = function(addressId) {
        if (confirm('确定要删除这个收货地址吗？')) {
            window.location.href = 'recipient-addresses?action=delete&id=' + addressId;
        }
    };
 */    
    // 编辑地址（简化版本）
    // 编辑按钮点击事件
   $(document).on('click', '.edit-btn', function() {
        // 设置表单操作为编辑
        $('#formAction').val('edit');
        
        // 填充地址数据到表单
        $('#formAddrId').val($(this).data('addr-id'));
        $('#recipientName').val($(this).data('recipient-name'));
        $('#phone').val($(this).data('phone'));
        $('#address').val($(this).data('address'));
        $('#addressDetail').val($(this).data('address-detail'));
        $('#email').val($(this).data('email'));
        $('#alias').val($(this).data('address-label'));
        
        // 设置默认地址复选框
        var isDefault = $(this).data('is-default');
        $('#isDefault').prop('checked', isDefault == 1);
        
        // 修改模态框标题
        $('#addAddressModalLabel').text('编辑收货地址');
        $('#saveAddressBtn').text('更新收货地址');
        // 显示模态框
        $('#addAddressModal').modal('show');
    });
    
    /* // 模态框显示时重置（如果是添加模式）
    $('#addAddressModal').on('show.bs.modal', function(e) {
        // 如果不是编辑按钮触发的，则重置为添加模式
        if (!$(e.relatedTarget).hasClass('edit-btn')) {
            resetAddressForm();
        }
    }); */
    
    
    // 重置表单函数
    function resetAddressForm() {
        $('#addressForm')[0].reset();
        $('#formAction').val('add');
        $('#formAddrId').val('');
        $('#addAddressModalLabel').text('创建收货地址');
        $('#isDefault').prop('checked', false);
    }
    
/*     // 模态框显示时重置表单
    $('#addAddressModal').on('show.bs.modal', function() {
        $('#addressForm')[0].reset();
        $('input[name="action"]').val('add');
    }); */
    
 // 在模态框显示时设置提交按钮的value
    $('#addAddressModal').on('show.bs.modal', function(e) {
    	
    	 var isEdit = $('#formAction').val() === 'edit';

    	    if (!isEdit) {
    	        
    	        // 添加模式：重置表单
    	        $('#addressForm')[0].reset();
    	        $('#formAction').val('create');
    	        $('#formAddrId').val('');
    	        $('#addAddressModalLabel').text('创建收货地址');
    	        $('#saveAddressBtn').text('保存收货地址');
    	        $('#isDefault').prop('checked', false);
    	    }
    });

});
</script>
</body>
</html>