import { AuthenticationService } from './../services/authentication.service';
import { MagazineView, Address } from '../model/views/magazineView';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MagazineService } from '../services/magazine.service';

@Component({
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  magazine: MagazineView;
  parcelDetails: boolean;

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private magazineService: MagazineService,
              private route: ActivatedRoute) { }
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.magazineService.getById(Number(id)).subscribe(res => {
      this.magazine = res;
    });
  }
  getAddress(address: Address): string {
    return address.buildingNumber + '/' +
      address.flatNumber + ' ' +
      address.street + ' ' +
      address.postalCode + ' ' +
      address.city;
  }
  displayParcelDetails() {
    this.parcelDetails = !this.parcelDetails;
  }
  isAdmin() {
    return this.authenticationService.isAdmin();
  }

}
