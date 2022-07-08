import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './models/User';
import { UsersService } from './services/users.service';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit  {
    title = 'frontend';
    public term : string
    public isTokenThere : boolean

    user : User;
    editMode : boolean = false;

    name : string;
    username : string;
    email : string;
    address : string;
    phone : string;

    constructor(private router: Router,private usersService : UsersService,) {
        console.log("Token:  " + localStorage.getItem('token'));
        this.isTokenThere = localStorage.getItem('token') != null
    }
    ngOnInit(): void {
        // if (!localStorage.getItem('token')) {
        //     this.router.navigateByUrl('/login')
        //     return
        // }

        this.usersService.getUserByToken().subscribe((user : User) => {
            this.user = user
            console.log(user);
            this.name = user.name;
            this.username = user.username;
            this.email = user.email;
            this.address = user.address;
            this.phone = user.phone;
        }, (error : ErrorEvent) => {
            console.log(error)
        })
    }

    search () {
        this.router.navigate(["/shop", this.term]).then(() => window.location.reload())
    }
}
