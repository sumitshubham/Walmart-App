import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faCaretUp, faCaretDown } from "@fortawesome/free-solid-svg-icons";
import { CartItem } from 'src/app/models/CartItem';
import { User } from 'src/app/models/User';
import { CartItemsService } from 'src/app/services/cart-items.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
    selector: 'app-cart',
    templateUrl: './cart.component.html',
    styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
    caretUp = faCaretUp;
    caretDown = faCaretDown;
    noItemFlag : boolean = false;

    user: User
    cartItems: any[]

    constructor(
        private router: Router,
        private usersService: UsersService,
        private cartItemsService: CartItemsService
    ) { }

    ngOnInit(): void {
        if (!localStorage.getItem('token')) {
            this.router.navigateByUrl('/login')
            return
        }

        this.usersService.getUserByToken().subscribe((user: User) => {
            this.user = user;
            this.getItems()
        }, (error: ErrorEvent) => {
            console.log(error)
        })
    }
    //Get CART Items
    getItems() {
        //WHEN USING MONGO DB
        this.cartItemsService.getUserCartMongo(this.user.id.toString()).subscribe((cartItems: CartItem[]) => {
            if(cartItems.length>0)
                this.noItemFlag = true;
            else
                this.noItemFlag = false;
            this.cartItems = cartItems;
            this.restoreImages();
            console.log(this.cartItems);
        })
        //WHEN USING MYSQL DATABASE
        // this.cartItemsService.getUserCart(this.user.id.toString()).subscribe((cartItems : CartItem[]) => {
        //     this.cartItems = cartItems;
        //     console.log(cartItems);
        // })
    }

    //restore wallmart images with correct prefix and suffix
    restoreImages() {
        for (let index = 0; index < this.cartItems.length; index++) {
            this.cartItems[index].image_url = "https://i5.walmartimages.com/asr/" + this.cartItems[index].image_url + "?odnHeight=180&odnWidth=180&odnBg=ffffff";
            console.log(this.cartItems[index].image_url);
        }
    }

    //delete cart item
    delete(id: number) {
        //WHEN USING MONGO DB
        this.cartItemsService.deleteUserCartItemMongo(this.user.id.toString(), id).subscribe(res => {
            console.log(res)
            this.getItems()
        })
        console.log(id);
        //WHEN USING MYSQL DATABASE
        // this.cartItemsService.deleteUserCartItem(this.user.id.toString(), id).subscribe(res => {
        //     console.log(res)
        //     this.getItems()
        // })
        // console.log(id);
    }

    //////////////////////////////////////////////////

    // getTotal () : Number {
    //     var reducer = (acc, val) => acc + val;
    //     return this.cartItems ? this.cartItems.map((item) => item.totalPrice).reduce(reducer) : 0.0
    // }

    //update cart item quantity
    updateQuantity(productId: number, quantity: number) {

        //WHEN USING MONGO DB
        this.cartItemsService.updateUserCartItemMongo(this.user.id.toString(), productId, quantity).subscribe(res => {
            console.log(res)
            this.getItems()
        })

        //WHEN USING MYSQL DATABASE
        // this.cartItemsService.updateUserCartItem(this.user.id.toString(),productId,quantity).subscribe(res => {
        //     console.log(res)
        //     this.getItems()
        // })
    }
}
