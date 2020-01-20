import { Observable } from 'rxjs';
import { ParcelTypeService } from './../services/parcelTypeService';
import { Component, OnInit } from '@angular/core';
import { ParcelType } from '../model';

@Component({
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css']
})
export class OfferComponent implements OnInit {

  offer: Observable<Array<ParcelType>>;

  constructor(private parcelTypeService: ParcelTypeService) {
  }

  getOffer() {
    this.offer = this.parcelTypeService.getOffer();
  }
  ngOnInit() {
    this.getOffer();
  }

}
