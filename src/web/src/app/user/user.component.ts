import { Observable, Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { User } from '../model';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';

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
    private userService: UserService
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
  // getUser(id: number) {
  //   this.userInfo = this.userService.getById(id);
  // }
}
