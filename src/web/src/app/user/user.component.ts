import { CompanyDetails } from './../model/commandData/CompanyDetails';
import { AlertService } from 'src/app/services/alertService';
import { Observable, Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { User, Role } from '../model';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';
import { ClientService } from '../services/clientService';

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
  displayDetails = false;
  currentCompany: CompanyDetails;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private clientService: ClientService,
    private alertService: AlertService,
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
  displayUserDetails(user: User) {
      if (user.userType === Role.COMPANY_CLIENT) {
        this.displayDetails = !this.displayDetails;
        if (this.displayDetails) {
          this.clientService.getClientCompany(user.id).subscribe(company => {
            this.currentCompany = company;
            this.scrollToBottom();
          },
            error => {
              this.alertService.error(error.error.message);
            });
        }
      }
  }
  scrollToBottom() {
    console.log("XDDDD");
    const element = document.getElementById('details');
    element.scrollIntoView(false);
  }

}
