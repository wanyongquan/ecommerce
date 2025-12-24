package com.ecommerce.entity;

// 用于显示统计Top N 销售的商品
public class TopNProductSales {
	
	private int productId; 
	private String productName;
	private double price;
	private int salesAmount;

	private byte[] image;
    private String base64Image;
	    
	public TopNProductSales() {
		
	}
	public TopNProductSales(int productId, String productName, String base64Image, double price,int salesAmount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.base64Image = base64Image;
        this.price = price;
		this.salesAmount = salesAmount;
	}
		
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
	public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
	public int getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}


}
