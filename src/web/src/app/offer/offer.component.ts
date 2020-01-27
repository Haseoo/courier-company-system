import { ParcelService } from '../services/parcelService';
import { Component, OnInit } from '@angular/core';
import { ParcelType } from '../model';
import { AlertService } from '../services/alertService';

@Component({
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css']
})
export class OfferComponent implements OnInit {

  offer: any;

  constructor(private parcelTypeService: ParcelService,
    private alertService: AlertService) {
  }

  getOffer() {
    this.parcelTypeService.getOffer().subscribe(data => {
      this.offer = data;
    },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  ngOnInit() {
    this.alertService.clear();
    this.getOffer();
  }

}
