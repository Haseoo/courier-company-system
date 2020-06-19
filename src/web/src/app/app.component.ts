import { AuthenticationService } from './services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { User, Role } from './model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  currentUser: User;
  showRegForm = false;
  showLogForm = false;


  constructor(private authenticationService: AuthenticationService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }
  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe(params => {
      const token = params.get('auth_token');
      if (token != null) {
        this.authenticationService.loginByGoogle(token);
        this.currentUser = this.authenticationService.currentUserValue;
        this.router.navigate(['/client']);
      } else {
        this.currentUser = this.authenticationService.currentUserValue;
      }
    });
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
      } else if (this.authenticationService.isCourier()) {
        this.router.navigate(['/courier']);
        return;
      } else if (this.authenticationService.isLogistician()) {
        this.router.navigate(['/logistician']);
        return;
      } else {
        this.router.navigate(['/client']);
        return;
      }
    }
    this.router.navigate(['/login']);
    return;
  }
}
