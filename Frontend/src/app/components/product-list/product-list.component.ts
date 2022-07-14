import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { User } from 'src/app/models/User';
import { UsersService } from 'src/app/services/users.service';
import { ProductsService } from 'src/app/services/products.service';
import { CartItemsService } from 'src/app/services/cart-items.service';
import { newArray } from '@angular/compiler/src/util';
import { productDetails } from 'src/app/models/productDetails';

@Component({
    selector: 'app-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
    public term: string = "jedi fallen order"
    public products: any[]
    public user: User
    public isProductInCart: boolean
    array: any;
    productsStatus: any[]
    // checkProductStatus : any;
    loading: boolean = true;

    constructor(router: Router, route: ActivatedRoute, private productsService: ProductsService, private http: HttpClient, private usersService: UsersService,
        private cartItemsService: CartItemsService) {
        this.term = route.snapshot.paramMap.get('term') || ""
    }

    ngOnInit(): void {
        const searchTerm = encodeURIComponent(this.term);
        console.log("https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-search-by-keyword?keyword=" + searchTerm + "&page=1&sortBy=best_match");
        ///api call
        let headers = new HttpHeaders({
            'X-RapidAPI-Key': 'dc783654camsh9d91bddc0508e76p1a2272jsne2626e918347',
            'X-RapidAPI-Host': 'axesso-walmart-data-service.p.rapidapi.com'
        });
        this.http
            .get<any>("https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-search-by-keyword?keyword=" + searchTerm + "&page=1&sortBy=best_match", {
                headers: headers
            })
            .subscribe(data => {
                console.log(data);
                this.products = this.checkItemArray(data.item.props.pageProps.initialData.searchResult.itemStacks[0].items);
                this.productsStatus = this.setProductsStatus();
                this.loading = false;
                console.log(this.products);
            });

        this.usersService.getUserByToken().subscribe((user: User) => {
            this.user = user
            console.log(this.user.id);

            this.getCartItem()
        }, (error: ErrorEvent) => {
            console.log(error)
        });
    }


    addToCart(index: any, event: MouseEvent) {
        const productDetail = this.products[index].name.toString().replace(/[;]/g, "");
        let productUrl = (this.products[index].image).substring(33, this.products[index].image.length).replace("?odnHeight=180&odnWidth=180&odnBg=ffffff", "");
        productUrl = (this.products[index].image).substring(33, this.products[index].image.length).replace("?odnHeight=180&odnWidth=180&odnBg=FFFFFF", "");
        this.productsStatus[index] = true;
        console.log(this.productsStatus);

        (event.target as HTMLButtonElement).disabled = true;

        //WHEN USING MYSQL DATABASE
        // this.cartItemsService.addToUserCart(this.user.id.toString(), encodeURIComponent(productDetail), this.products[index].priceInfo.linePrice.toString(), 1, productUrl).subscribe(res => {
        //     this.getCartItem()
        // })

        console.log("before disecting = " + (this.products[index].image).substring(33, this.products[index].image.length));
        let disectUrl = (this.products[index].image).substring(33, this.products[index].image.length).split("?");
        console.log(disectUrl[0]);

        //WHEN USING MONGO DB
        this.cartItemsService.addToUserCartMongo(this.user.id.toString(), encodeURIComponent(productDetail), this.products[index].priceInfo.linePrice.toString(), 1, disectUrl[0], true).subscribe(res => {
            this.getCartItem();
        })

        // //WHEN USING MONGO DB Model
        // this.cartItemsService.addToUserCartMongoModel(this.user.id.toString(), sendProduct).subscribe(res => {
        //     this.getCartItem();
        // })
    }
    getCartItem() {

    }
    checkItemArray(items: any): any[] {
        let array: any[] = [];
        let index = 1;
        for (let item of items) {
            if (item.priceInfo.linePrice === "") {
                continue;
            }
            else {
                console.log("im here = " + index++ + " price here is = " + item.priceInfo.linePrice);
                array.push(item);
            }
        }
        return array;
    }
    setProductsStatus() {
        let array: any[] = [];
        for (let index = 0; index < this.products.length; index++) {
            array.push(false);
        }
        return array;
    }
}

