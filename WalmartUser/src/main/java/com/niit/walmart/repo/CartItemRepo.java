package com.niit.walmart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niit.walmart.model.CartItem;
import com.niit.walmart.model.CartItemPK;

public interface CartItemRepo extends JpaRepository <CartItem, CartItemPK> {
}
