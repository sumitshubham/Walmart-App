package com.niit.walmart.Exception;

public class CartItemAlreadyExistsException extends RuntimeException {
	public CartItemAlreadyExistsException(String message) {
        super(message);
    }
}
