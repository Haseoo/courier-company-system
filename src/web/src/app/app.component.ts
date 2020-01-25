import { AuthenticationService } from './services/authentication.service';
import { Component } from '@angular/core';
import { User, Role } from './model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentUser: User;
  showRegForm = false;
  showLogForm = false;


  constructor(private authenticationService: AuthenticationService,
    private router: Router) {
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
  navigateTo() {
    if (this.authenticationService.isLoggedIn) {
      if (this.authenticationService.isAdmin()) {
        this.router.navigate(['/admin']);
        return;
      }
      else if (this.authenticationService.isCourier()) {
        this.router.navigate(['/courier']);
        return;
      }
      else if (this.authenticationService.isLogistician()) {
        this.router.navigate(['/logistician']);
        return;
      } else{
        this.router.navigate(['/client']);
        return;
      }
    }
    this.router.navigate(['/login']);
    return;
  }
}
