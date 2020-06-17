import { ParcelClientView } from './../model/views/parcelClientView';
import { AlertService } from './../services/alertService';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { ClientService } from '../services/clientService';
import { Client } from '../model/client';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  currentClient: Client;
  infoAboutParcel = false;
  currentParcel: ParcelClientView;
  constructor(private clientService: ClientService,
              private alertService: AlertService,
              private authenticationService: AuthenticationService) { }


  ngOnInit() {
    this.clientService.getClient(this.authenticationService.getId).subscribe(data => {
      this.currentClient = data;
    },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  showMoreInfo(parcel: ParcelClientView) {
    this.infoAboutParcel = !this.infoAboutParcel;
    this.currentParcel = parcel;
  }

  paypalPayment() {
    location.href = 'http://localhost:2137/payments/paypal';
  }

}
