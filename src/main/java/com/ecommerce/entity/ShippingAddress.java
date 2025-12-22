package com.ecommerce.entity;

public class ShippingAddress {
	private int addr_id ;
	private int accountId;
	private String recipientName;
	private String distinct;	
	private String address_detail;
	private String phone;
	private String email;
	private String addressLabel;
	private int isDefault;
	
	public ShippingAddress() {

	}
	
	
	public ShippingAddress(int addr_id, int accountId, String recipientName, String distinct, String address_detail, String phone,
			String email, int isDefault, String addressLabel) {
		super();
		this.addr_id = addr_id;
		this.accountId = accountId;
		this.recipientName = recipientName;
		this.distinct = distinct;
		this.address_detail = address_detail;
		this.phone = phone;
		this.email = email;
		this.addressLabel = addressLabel;
		this.isDefault = isDefault;
	}


	public int getAddr_id() {
		return addr_id;
	}
	public void setAddr_id(int addr_id) {
		this.addr_id = addr_id;
	}
	
	public int getAccountId() {
		return accountId;
	}


	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getDistinct() {
		return distinct;
	}
	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}
	public String getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddressLabel() {
		return addressLabel;
	}
	public void setAddressLabel(String addressLabel) {
		this.addressLabel = addressLabel;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}


	@Override
	public String toString() {
		return "ShippingAddress [addr_id=" + addr_id + ", recipientName=" + recipientName + ", distinct=" + distinct
				+ ", address_detail=" + address_detail + ", phone=" + phone + ", email=" + email + ", addressLabel="
				+ addressLabel + ", isDefault=" + isDefault + "]";
	}
	
	
}
