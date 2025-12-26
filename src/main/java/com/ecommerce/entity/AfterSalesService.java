package com.ecommerce.entity;

import java.util.Date;

// 售后服务 
public class AfterSalesService {
	private int serviceId;
	private int orderId;
	private int productId; 
	private int apply_accont_id;
	private String productName;
	private int serviceType = 0;
	private String reason;
	private String serviceDescription;
	private Date   appliedDate;
	private int status; 
	public AfterSalesService() {
		
	}
	public AfterSalesService(int serviceId, int orderId, int productId, int serviceType, String reason, String serviceDescription,
			Date date_created, int status) {
		this.serviceId = serviceId;
		this.orderId = orderId;
		this.productId = productId;
		this.serviceType = serviceType;
		this.reason = reason;
		this.serviceDescription = serviceDescription;
		this.appliedDate = date_created;
		this.status = status;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getApply_accont_id() {
		return apply_accont_id;
	}
	public void setApply_accont_id(int apply_accont_id) {
		this.apply_accont_id = apply_accont_id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
