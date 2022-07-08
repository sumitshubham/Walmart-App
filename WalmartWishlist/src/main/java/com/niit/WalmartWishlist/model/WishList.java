package com.niit.WalmartWishlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="wishlist")
public class WishList {
	@Id
	private int id;
	private String image_url;
	private double price;
	private int quantity;
	private String title;
	private int user_id;
	private boolean exists;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
	public WishList() {
		
	}
	
	public WishList(int id, String image_url, double price, int quantity, String title, int user_id, boolean exists) {
		super();
		this.id = id;
		this.image_url = image_url;
		this.price = price;
		this.quantity = quantity;
		this.title = title;
		this.user_id = user_id;
		this.exists = exists;
	}
	@Override
	public String toString() {
		return "WishList [id=" + id + ", image_url=" + image_url + ", price=" + price + ", quantity=" + quantity
				+ ", title=" + title + ", user_id=" + user_id + ", exists=" + exists + "]";
	}
	
	
}
