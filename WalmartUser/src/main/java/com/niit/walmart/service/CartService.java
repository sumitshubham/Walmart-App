package com.niit.walmart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.walmart.Exception.CartItemAlreadyExistsException;
import com.niit.walmart.model.Cart;
import com.niit.walmart.model.CartItem;
import com.niit.walmart.repo.CartRepo;

@Service
@Transactional
public class CartService {
	
	@Autowired
	private CartRepo cartRepo;
	
	public CartService(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	public List<Cart> getCart(){
		return cartRepo.findAll();
	}
	
	public Cart addCartItem(Cart cart) {
        return this.cartRepo.save(cart);
    }
	
	public void deleteCartItem(int userId, int productId) {
		cartRepo.deleteById(productId);
	}

	public void updateCartItem(Cart updatedCartItem) {
		cartRepo.save(updatedCartItem);
	}
}
