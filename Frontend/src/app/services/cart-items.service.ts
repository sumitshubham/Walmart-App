import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { text } from '@fortawesome/fontawesome-svg-core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CartItem } from '../models/CartItem';
import { User } from '../models/User';
import { productDetails } from '../models/productDetails';

@Injectable({
  providedIn: 'root'
})
export class CartItemsService {
    constructor(private http : HttpClient) { }

    getCartItem (userId : string, productId : string) : Observable<CartItem> {
        return this.http.get<CartItem>(`${environment.API_URL}/api/cart-items/${userId}/${productId}`)
    }

    // our add to cart MYSQL
    addToUserCart (userId : string, productName : string, productPrice: string, productQuantity: number, productImage:String) : Observable<User> {
        console.log(`${environment.API_URL}/api/users/${userId}/cart/add/${productName}/${productPrice}/${productQuantity}/${productImage}`);
        console.log(productImage);
        return this.http.post<User>(`${environment.API_URL}/api/users/${userId}/cart/add/${productName}/${productPrice}/${productQuantity}/${productImage}`, {
        })
    }


    //gets user cart data MYSQL
    getUserCart (userId : string) : Observable<CartItem[]> {
        return this.http.get<CartItem[]>(`${environment.API_URL}/api/users/${userId}/cart`)
    }


    //update quantity MYSQL
    updateUserCartItem (userId : string, productId : number, quantity : Number) : Observable<User> {
        return this.http.put<User>(`${environment.API_URL}/api/users/${userId}/cart/update/${productId}/${quantity}`, {
            quantity
        })
    }

    //delete item from cart MYSQL
    deleteUserCartItem (userId : string, productId : number) : Observable<any> {
        return this.http.delete(`${environment.API_URL}/api/users/${userId}/cart/remove/${productId}`)
    }


    //Add to user cart in MONGO DB

    addToUserCartMongo (userId : string, productName : string, productPrice: string, productQuantity: number, productImage:String, productStatus:boolean) : Observable<User> {
        console.log(`${environment.WISHLIST_URL}/api/wish/user/${userId}/list/add/${productName}/${productPrice}/${productQuantity}/${productImage}/${productStatus}`);
        return this.http.post<User>(`${environment.WISHLIST_URL}/wish/user/${userId}/list/add/${productName}/${productPrice}/${productQuantity}/${productImage}/${productStatus}`, {
        })
    }

    //Get user cart in MONGO DB
    getUserCartMongo (userId : string) : Observable<any> {
        return this.http.get<any>(`${environment.WISHLIST_URL}/wish/user/${userId}/getcart`)
    }

    //update quantity in MONGO DB
    updateUserCartItemMongo (userId : string, productId : number, quantity : Number) : Observable<User> {
        return this.http.put<User>(`${environment.WISHLIST_URL}/wish/user/${userId}/cart/update/${productId}/${quantity}`, {
            quantity
        })
    }

    //delete item from cart in MONGO DB
    deleteUserCartItemMongo (userId : string, productId : number) : Observable<any> {
        return this.http.delete(`${environment.WISHLIST_URL}/wish/user/${userId}/cart/remove/${productId}`,{responseType:'text'})
    }

    //add to cart using model
    // addToUserCartMongoModel (userId : string, product : productDetails) : Observable<User> {
    //     console.log(`${environment.WISHLIST_URL}/api/user/${userId}/list/add/${product}`);
    //     return this.http.post<User>(`${environment.WISHLIST_URL}/api/user/${userId}/list/add/${product}`, {
    //     })
    // }
}
