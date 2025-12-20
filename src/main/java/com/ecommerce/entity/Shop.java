package com.ecommerce.entity;

public class Shop {
	private int shopId;
	private int accountId;
	private String shopName;
	private String shopDescription;
	
	
	public Shop() {
	
	}

	public Shop(int shopId, int accountId, String shopName, String shopDescription) {
		super();
		this.shopId = shopId;
		this.accountId = accountId;
		this.shopName = shopName;
		this.shopDescription = shopDescription;
	}
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopDescription() {
		return shopDescription;
	}
	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}
	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", accountId=" + accountId + ", shopName=" + shopName + ", shopDescription="
				+ shopDescription + "]";
	}
	
}
