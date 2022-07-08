package com.niit.WalmartWishlist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.niit.WalmartWishlist.model.WishList;

@Repository
public interface WishListRepo extends MongoRepository<WishList, Integer> {

}
