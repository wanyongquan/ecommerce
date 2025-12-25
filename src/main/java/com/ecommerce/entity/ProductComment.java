package com.ecommerce.entity;

import java.util.Date;

public class ProductComment {
	private int commentId;
	private int productId;
	private int orderId; 
	private int rating;
	private String comment;
	private int userId ;
	private String userName;
	private Date createdTime;
	private String seller_reply;
	private Date replyTime;
	public ProductComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductComment(int commentId, int productId, int orderId, int rating, String comment, int userId,
			Date createdTime, String seller_reply, Date replyTime) {
		super();
		this.commentId = commentId;
		this.productId = productId;
		this.orderId = orderId;
		this.rating = rating;
		this.comment = comment;
		this.userId = userId;
		this.createdTime = createdTime;
		this.seller_reply = seller_reply;
		this.replyTime = replyTime;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getSeller_reply() {
		return seller_reply;
	}
	public void setSeller_reply(String seller_reply) {
		this.seller_reply = seller_reply;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	} 
	
	
}
