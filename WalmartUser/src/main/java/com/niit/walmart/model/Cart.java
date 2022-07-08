package com.niit.walmart.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private int quantity;
	private double price;
	private String imageUrl;
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Cart() {
		
	}
	
	public Cart(String title, int quantity, double price, String imageUrl,Long userId) {
		this.title = title;
		this.quantity = quantity;
		this.price = price;
		this.imageUrl = imageUrl;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", title=" + title + ", quantity=" + quantity + ", price=" + price + ", imageUrl="
				+ imageUrl + ", userId=" + userId + "]";
	}
}
