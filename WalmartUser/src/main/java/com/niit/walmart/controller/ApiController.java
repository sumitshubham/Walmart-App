package com.niit.walmart.controller;

import com.niit.walmart.config.JWTUtil;
import com.niit.walmart.model.Product;
import com.niit.walmart.model.User;
import com.niit.walmart.repo.UserRepo;
import com.niit.walmart.model.Cart;
import com.niit.walmart.model.CartItem;
import com.niit.walmart.model.CartItemPK;
import com.niit.walmart.service.CartItemService;
import com.niit.walmart.service.CartService;
import com.niit.walmart.service.JWTUserDetailService;
import com.niit.walmart.service.ProductService;
import com.niit.walmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Autowired
    private JWTUserDetailService jwtUserDetailsService;
    
    @Autowired
    private CartService cartService;

    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ApiController(UserService userService, ProductService productService, CartItemService cartItemService) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @PostMapping("/create-token")
    public ResponseEntity<?> createToken (@RequestBody Map<String, String> user) throws Exception {
        Map<String, Object> tokenResponse = new HashMap<>();
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.get("username"));
        final String token = jwtUtil.generateToken(userDetails);

        tokenResponse.put("token", token);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers () {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/{newPassword}")
    public ResponseEntity<?> updateUser (@PathVariable("id") Long id, @RequestBody Map<String, Object> user,@PathVariable("newPassword") String newPassword) {
    	System.out.println("Updating user ==================================================================");
    	User newUser = null;
    	User checkUser = userService.getUser(id);
    	try {
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
    		if(encoder.matches((String) user.get("password"), checkUser.getPassword())) {
    			System.out.println("Match Found");
    			newUser = new User(
    	                (String) user.get("username"),
    	                (String) newPassword,
    	                (String) user.get("email"),
    	                (String) user.get("name"),
    	                (String) user.get("address"),
    	                (String) user.get("phone")
    	        		);
    			System.out.println(newUser);
    			System.out.println("Updating user ==================================================================");
    	        return new ResponseEntity<>(userService.updateUser(id, newUser), HttpStatus.OK);
    		}
    		return new ResponseEntity<>(newUser, HttpStatus.NOT_FOUND);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<>("Something went wrong while updating user", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

//    @GetMapping("/users/{id}/cart")
//    public ResponseEntity<List<CartItem>> getUserCart (@PathVariable("id") Long id) {
//        System.out.println(userService.getUser(id).getCartItems().size());
//        return new ResponseEntity<>(userService.getUser(id).getCartItems(), HttpStatus.OK);
//    }

//    @PostMapping("/users/{id}/cart/add/{productId}")
//    public ResponseEntity<User> addToUserCart (@PathVariable("id") Long id,
//                                               @PathVariable("productId") Long productId) {
//        User user = userService.getUser(id);
//        Product product = productService.getProduct(productId);
//
//        CartItem cartItem = new CartItem(user, product, 1);
//        cartItemService.addCartItem(cartItem);
//
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.CREATED);
//    }

//    @PutMapping("/users/{id}/cart/update/{productId}")
//    public ResponseEntity<User> updateCartItem (@PathVariable("id") Long id,
//                                                @PathVariable("productId") Long productId,
//                                                @RequestBody CartItem cartItem) {
//        User user = userService.getUser(id);
//        Product product = productService.getProduct(productId);
//
//        cartItem.setPk(new CartItemPK(user, product));
//        cartItemService.updateCartItem(cartItem);
//
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
//    }

//    @DeleteMapping("/users/{id}/cart/remove/{productId}")
//    public ResponseEntity<User> removeFromUserCart (@PathVariable("id") Long id,
//                                                    @PathVariable("productId") Long productId) {
//        cartItemService.deleteCartItem(id, productId);
//
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
//    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts () {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct (@RequestBody Product product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable("id") Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/cart-items")
//    public ResponseEntity<List<CartItem>> getCartItems () {
//        return ResponseEntity.ok(cartItemService.getCartItems());
//    }

    @CrossOrigin
    @GetMapping("/cart-items/{id}/{productId}")
    public ResponseEntity<CartItem> getCartItem (@PathVariable("id") Long id,
                                                 @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(cartItemService.getCartItem(id, productId));
    }
    
    ////////////////////////////////////////////
    
    //our add item to cart
    
    @PostMapping("/users/{id}/cart/add/{productName}/{productPrice}/{productQuantity}/{productImage}")
    public ResponseEntity<User> addToUserCart (@PathVariable("id") Long id,@PathVariable("productName") String name,@PathVariable("productPrice") String price,
    											@PathVariable("productQuantity") int quantity,@PathVariable("productImage") String image){
    	
    	
        double cost_price = Double.parseDouble(price.substring(1,price.length()));
        cartService.addCartItem(new Cart(name,quantity,cost_price*quantity,image,id));
    	System.out.println(name+cost_price+quantity+image);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.CREATED);
    }
    
    //our cart
    
    @GetMapping("/users/{id}/cart")
    public ResponseEntity<List<Cart>> getUserCart (@PathVariable("id") Long id) {
    	List<Cart> allCartData = cartService.getCart(); 
    	List<Cart> userCart = new ArrayList<>();
        for(Cart item: allCartData) {
        	if(id==item.getUserId())
        		userCart.add(item);
        }
        System.out.println(userCart);
        return new ResponseEntity<>(userCart, HttpStatus.OK);
    }
    
    //our delete product
    
    @DeleteMapping("/users/{id}/cart/remove/{productId}")
    public ResponseEntity<User> removeFromUserCart (@PathVariable("id") int id,
                                                    @PathVariable("productId") int productId) {
        cartService.deleteCartItem(id, productId);
        
        String temp = ""+id;
        return new ResponseEntity<>(userService.getUser(Long.parseLong(temp)), HttpStatus.OK);
    }
    
    //our update cart
    @PutMapping("/users/{id}/cart/update/{productId}/{quantity}")
    public ResponseEntity<User> updateCartItem (@PathVariable("id") int userId,
                                                @PathVariable("productId") int productId,
                                                @PathVariable("quantity") int quantity,
                                                @RequestBody CartItem cartItem) {
        User user = userService.getUser(Long.parseLong(""+userId));
        Cart cartFound = null;
        List<Cart> cartAll = cartService.getCart();
        for(Cart cart: cartAll) {
        	if(userId==cart.getUserId() && productId == cart.getId())
        		cartFound = cart;
        }
        
        if(quantity<=0)
        	quantity = 1;
        
        double basePrice = cartFound.getPrice()/cartFound.getQuantity();
        cartFound.setQuantity(quantity);
        cartFound.setPrice(quantity*basePrice);
        cartService.updateCartItem(cartFound);
        
        System.out.println("herererererer = "+cartFound);

        return new ResponseEntity<>(userService.getUser(Long.parseLong(""+userId)), HttpStatus.OK);
    }
    
    
    
    ////////////////////////////////////////////////
    
    
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser (HttpServletRequest request)  {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = repo.findByUsername(((UserDetails) principal).getUsername());
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser (@RequestBody Map<String, Object> user) throws Exception {
        User savedUser = new User();
        User newUser = new User(
                (String) user.get("username"),
                (String) user.get("password"),
                (String) user.get("email"),
                (String) user.get("name"),
                (String) user.get("address"),
                (String) user.get("phone")
        );

        if (newUser.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is missing.");
        }

        if (newUser.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is missing.");
        }

        if (newUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is missing.");
        } else if (newUser.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password length must be 8+.");
        }

        if (newUser.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is missing.");
        }

        if (newUser.getAddress() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is missing.");
        }

        if (newUser.getPhone() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone is missing.");
        }

        try {
            savedUser = jwtUserDetailsService.save(newUser);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause().getMessage().contains(newUser.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is not available.");
            }

            if (e.getRootCause().getMessage().contains(newUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is not available.");
            }
        }

        Map<String, Object> tokenResponse = new HashMap<>();

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(savedUser.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        tokenResponse.put("token", token);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser (@RequestBody Map<String, String> user) throws Exception {
        authenticate(user.get("username"), user.get("password"));

        Map<String, Object> tokenResponse = new HashMap<>();
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.get("username"));
        final String token = jwtUtil.generateToken(userDetails);

        tokenResponse.put("token", token);
        return ResponseEntity.ok(tokenResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }
}

