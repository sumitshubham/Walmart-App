import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from '../models/Product';

@Injectable({
    providedIn: 'root'
})
export class ProductsService {

    constructor(private http: HttpClient) { }

    getProducts(): Observable<any[]> {
        let headers = new HttpHeaders({
            'x-rapidapi-host': 'axesso-walmart-data-service.p.rapidapi.com',
            'x-rapidapi-key': '24a71a8629msh2f708689034a67cp12d39ejsn26c8d733a85f'
        });
        this.http
            .get<any[]>('https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-search-by-keyword?keyword=Lego%20Star%20Wars&page=1&sortBy=best_match', {
                headers: headers
            })
            .subscribe(data => {
                console.log(data);
            });
        return this.http.get<Product[]>(`${environment.API_URL}/api/products`);
        // const observable: Observable<any[]> = this.http.get<any[]>("https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-search-by-keyword?keyword=Lego%20Star%20Wars&page=1&sortBy=best_match");
        // return observable;
    }

    getProduct(id: string): Observable<Product> {
        const data = null;

        const xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === this.DONE) {
                console.log(this.responseText);
            }
        });

        xhr.open("GET", "https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-search-by-keyword?keyword=Lego%20Star%20Wars&page=1&sortBy=best_match");
        xhr.setRequestHeader("X-RapidAPI-Key", "24a71a8629msh2f708689034a67cp12d39ejsn26c8d733a85f");
        xhr.setRequestHeader("X-RapidAPI-Host", "axesso-walmart-data-service.p.rapidapi.com");

        xhr.send(data);
        return this.http.get<Product>(`${environment.API_URL}/api/products/${id}`);
    }
}
