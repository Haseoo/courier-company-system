import { Observable, Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { User } from '../model';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  currentUser: User;
  currentUserSubscription: Subscription;
  users: Observable<Array<User>>;
  userInfo: User;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private router: Router
  ) {

    this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }


  ngOnInit() {
    this.getUsers();
  }
  getUsers() {
    this.users = this.userService.getAll();
  }
  deactiveUser(user: User) {
    this.userService.setAsInActive(user).subscribe(data => {
      this.ngOnInit();
    });
  }
  activeUser(user: User) {
    this.userService.setAsActive(user).subscribe(data => {
      this.ngOnInit();
    });
  }

}
