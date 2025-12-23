package com.ecommerce.entity;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Account account;
    private List<CartProduct> cartProducts;
    private double total;
    private Date date;
    private int status;
    private int seller_account_id = -1;
    
    public Order() {
    }

    // Constructor to get order information of customer.
    public Order(int id, double total, Date date, int seller_id) {
        this.id = id;
        this.total = total;
        this.date = date;
        this.seller_account_id = seller_id;
    }
    // Constructor to get order information of customer.
    public Order(int id, double total, Date date, int seller_id, int status) {
        this.id = id;
        this.total = total;
        this.date = date;
        this.seller_account_id = seller_id;
        this.status = status;
    }

    public Order(int id, Account account, List<CartProduct> item, double total, Date date) {
        this.id = id;
        this.account = account;
        this.cartProducts = item;
        this.total = total;
        this.date = date;
    }
    public Order(int id, Account account, List<CartProduct> item, double total, Date date, int status) {
        this.id = id;
        this.account = account;
        this.cartProducts = item;
        this.total = total;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	public int getSeller_account_id() {
		return seller_account_id;
	}

	public void setSeller_account_id(int seller_account_id) {
		this.seller_account_id = seller_account_id;
	}

	@Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", account=" + account +
                ", cartProducts=" + cartProducts +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
