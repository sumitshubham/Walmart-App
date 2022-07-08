package com.niit.WalmartWishlist.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.WalmartWishlist.model.WishList;
import com.niit.WalmartWishlist.repository.WishListRepo;

@RestController
@RequestMapping("/wish")
public class WishController {
	@Autowired
	private WishListRepo wishList;
	
	//Get Auto Generated ID
	public int getId(List<WishList> wish) {
		if(wish.size()==0)
			return 1;
		return wish.get(wish.size()-1).getId()+1;
	}
	
	//Add New Item To WishList
	@PostMapping("/user/{id}/list/add/{productName}/{productPrice}/{productQuantity}/{productImage}/{exists}")
    public ResponseEntity<?> addToList (@PathVariable("id") Integer id,@PathVariable("productName") String name,@PathVariable("productPrice") String price,
    											@PathVariable("productQuantity") int quantity,@PathVariable("productImage") String image,@PathVariable("exists") boolean exists){
    	
        double cost_price = Double.parseDouble(price.substring(1,price.length()));
        wishList.save(new WishList(getId(wishList.findAll()),image,cost_price*quantity,quantity,name,id,exists));
    	System.out.println(new WishList(getId(wishList.findAll()),image,cost_price,quantity,name,id,exists));
        return new ResponseEntity<>("done", HttpStatus.OK);
    }
	
	//Get User Cart
	@GetMapping("/user/{id}/getcart")
	public ResponseEntity<?> getAllWishList(@PathVariable("id") Integer id){
		List<WishList> list = wishList.findAll();
		List<WishList> sendList = new ArrayList<WishList>();

		for(WishList wish : list) {
			if(id==wish.getUser_id())
				sendList.add(wish);
		}
		
		if(list.size()<=0 || sendList.size()<=0)
			return new ResponseEntity<>(sendList,HttpStatus.OK);
		
		
		return new ResponseEntity<List<WishList>>(sendList,HttpStatus.OK);
	}
	
	//Update User Cart
	@PutMapping("/user/{id}/cart/update/{productId}/{quantity}")
    public ResponseEntity<?> updateCartItem (@PathVariable("id") int userId,
                                                @PathVariable("productId") int productId,
                                                @PathVariable("quantity") int quantity) {
		List<WishList> list = wishList.findAll();
		WishList wishFound = null;
        for(WishList wish: list) {
        	if(userId==wish.getUser_id() && productId == wish.getId())
        		wishFound = wish;
        }
        
        if(quantity<=0)
        	quantity = 1;
        
        double basePrice = wishFound.getPrice()/wishFound.getQuantity();
        wishFound.setQuantity(quantity);
        wishFound.setPrice(quantity*basePrice);
        wishList.save(wishFound);

        return new ResponseEntity<>(wishFound, HttpStatus.OK);
    }
	
	//Delete Cart Item
	@DeleteMapping("/user/{id}/cart/remove/{productId}")
    public ResponseEntity<?> removeFromUserCart (@PathVariable("id") int id,
                                                    @PathVariable("productId") int productId) {
		try {
			List<WishList> wish = new ArrayList<>();
	        wishList.deleteById(productId);
	        return new ResponseEntity<>(wish, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
		}
    }
	
	//Add New Item To WishList
		@PostMapping("/user/{id}/list/add/{product}")
	    public ResponseEntity<?> addToListObject (@PathVariable("id") Integer id,@PathVariable("product") WishList wish){
	    	
			System.out.println("herere ======== "+wish+" "+id);
//	        double cost_price = Double.parseDouble(.substring(1,price.length()));
//	        wishList.save(new WishList(getId(wishList.findAll()),image,cost_price*quantity,quantity,name,id,exists));
//	    	System.out.println(new WishList(getId(wishList.findAll()),image,cost_price,quantity,name,id,exists));
	        return new ResponseEntity<>("done", HttpStatus.OK);
	    }
}
