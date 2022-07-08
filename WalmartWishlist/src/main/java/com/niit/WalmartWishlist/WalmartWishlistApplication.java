package com.niit.WalmartWishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WalmartWishlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalmartWishlistApplication.class, args);
	}

}
