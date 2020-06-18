import { PayPalService } from './../services/paypalService';
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
  loading = false;
  constructor(private clientService: ClientService,
              private alertService: AlertService,
              private paypalService: PayPalService,
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

  paypalPayment(parcelId: number) {
    this.loading = true;
    this.paypalService.makePayment(parcelId).subscribe(
      response => {
    },error => {
      location.href = error.error.text;
      this.loading = false;
      }
    );
  }

}
