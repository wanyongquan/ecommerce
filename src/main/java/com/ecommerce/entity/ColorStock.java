package com.ecommerce.entity;

public class ColorStock {
	private int productId; 
	private String colorName;
	private int amount;
	public ColorStock() {}
	
	public ColorStock(int productId, String colorname, int amount) {
		this.productId = productId;
		this.colorName = colorname;
		this.amount = amount;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
