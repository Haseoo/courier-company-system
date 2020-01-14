import { AuthenticationService } from './services/authentication.service';
import { Component } from '@angular/core';
import { User } from './model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentUser: User;
  showRegForm = false;
  showLogForm = false;


  constructor(private authenticationService: AuthenticationService) {
    this.currentUser = this.authenticationService.currentUserValue;
  }
  isLogin() {
    if (this.currentUser) {
      return true;
    } else {
      return false;
    }
  }
  displayRegisterForm() {
    this.showLogForm = false;
    this.showRegForm = true;
  }
  displayLoginForm() {
    this.showRegForm = false;
    this.showLogForm = true;
  }
  logout() {
    this.currentUser = null;
    this.authenticationService.logout();
  }
}
