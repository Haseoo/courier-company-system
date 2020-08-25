import { LogisticianService } from './../services/logisticianService';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn: boolean;
  isAdmin: boolean;
  isClient: boolean;
  isLogistician: boolean;
  isCourier: boolean;

  constructor(private authenticationService: AuthenticationService,
    private LogisticianService: LogisticianService,
    private router: Router) {
    router.events.subscribe((val) => {
      this.ngOnInit();
    });
  }

  ngOnInit() {
    this.isAdmin = this.authenticationService.isAdmin();
    this.isLoggedIn = this.authenticationService.isLoggedIn();
    this.isClient = this.authenticationService.isClient();
    this.isLogistician = this.authenticationService.isLogistician();
    this.isCourier = this.authenticationService.isCourier();
  }

  onLogout() {
    this.authenticationService.logout();
    this.ngOnInit();
  }
  getUsername() {
    return this.authenticationService.getUsername;
  }
  getParcelList() {
    let id: number;
    this.LogisticianService.getById(this.authenticationService.getId).subscribe(data => {
      id = data.magazine.id;
      this.router.navigate(['logistician/magazines', id]);
    });
  }
}
