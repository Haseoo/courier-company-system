import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn: boolean;
  isAdmin: boolean;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
    router.events.subscribe((val) => {
      this.ngOnInit();
    });
  }

  ngOnInit() {
    this.isAdmin = this.authenticationService.isAdmin();
    this.isLoggedIn = this.authenticationService.isLoggedIn();

  }

  onLogout() {
    this.authenticationService.logout();
    this.ngOnInit();
  }
  getUsername() {
    return this.authenticationService.getUsername;
  }

}
