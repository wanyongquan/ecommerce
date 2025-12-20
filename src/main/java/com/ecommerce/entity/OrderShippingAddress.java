package com.ecommerce.entity;

//  Class of Order Shipping Address
public class OrderShippingAddress {
	private int addr_id ;
	private int orderID;
	private String recipientName;
	private String phone;
	private String address_detail;
	
	public OrderShippingAddress()
	{
	
	}
	
	public int getAddr_id() {
		return addr_id;
	}
	public void setAddr_id(int addr_id) {
		this.addr_id = addr_id;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}
	@Override
	public String toString() {
		return "OrderShippingAddress [addr_id=" + addr_id + ", orderID=" + orderID + ", recipientName=" + recipientName
				+ ", phone=" + phone + ", address_detail=" + address_detail + "]";
	}
	
	
}
