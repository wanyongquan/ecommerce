package com.ecommerce.entity;

public class CartProduct {
    private Product product;
    private int  orderId;
    private int quantity;
    private double price;

    private String pickedColor;


    public CartProduct() {
    }


    public CartProduct(Product product, int quantity, double price, String color) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.pickedColor = color;

    }
    
    public CartProduct(Product product, int orderId, int quantity, double price, String color) {
        this.product = product;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
        this.pickedColor = color;

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	
	public String getPickedColor() {
		return pickedColor;
	}

	public void setPickedColor(String pickedColor) {
		this.pickedColor = pickedColor;
	}
 

    public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	@Override
    public String toString() {
        return "CartProduct{" +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", color=" + pickedColor +

                '}';
    }
}
