package com.niit.walmart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niit.walmart.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer>{

}
