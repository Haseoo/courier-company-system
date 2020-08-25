import { Component, OnInit } from '@angular/core';
import { User } from '../model';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    loading = false;
    currentUser: User;
    userFromApi: User;

    constructor(
        private userService: UserService,
        private authenticationService: AuthenticationService
    ) {
        this.currentUser = this.authenticationService.currentUserValue;
    }

    ngOnInit() {
      
    }
}
