import { PayPalService } from './../services/paypalService';
import { ParcelDateMoveCommandData } from '../model/commandData/ParcelDateMoveCommandData';
import { ActivatedRoute } from '@angular/router';
import { AlertService } from './../services/alertService';
import { ParcelClientView } from './../model/views/parcelClientView';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ParcelService } from '../services/parcelService';
import { Address } from '../model/address';

@Component({
  selector: 'app-parcel',
  templateUrl: './parcel.component.html',
  styleUrls: ['./parcel.component.css']
})
export class ParcelComponent implements OnInit {
  parcel: ParcelClientView;
  submitted = false;
  infoAboutParcel = false;
  changeDate = false;
  loading = false;
  parcelDate = new ParcelDateMoveCommandData();
  id: string;

  constructor(private formBuilder: FormBuilder,
              private parcelService: ParcelService,
              private route: ActivatedRoute,
              private paypalService: PayPalService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id) {
      this.parcelService.getParcelByClient(Number(this.id)).subscribe((date) => {
        this.parcel = date;
        this.parcel.isPayable = this.parcel.paid === false &&
            this.parcel.parcelStatesView[this.parcel.parcelStatesView.length - 1].stateType !== 'DELIVERED' &&
            this.parcel.parcelStatesView[this.parcel.parcelStatesView.length - 1].stateType !== 'RETURNED';
        this.alertService.clear();
      },
        error => {
          this.alertService.error('Parcel not found');
        }
      );
    }
  }

  showMoreInfo() {
    this.infoAboutParcel = !this.infoAboutParcel;
  }
  showNewDateForm() {
    this.changeDate = !this.changeDate;
  }
  changeDeliveryDate() {
    this.infoAboutParcel = false;
    this.parcelService.moveDate(this.parcel.id, this.parcelDate).subscribe(date => {
      this.alertService.success('Date changed!');
      this.ngOnInit();
    },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  updatePin(event) {
    this.parcelDate.parcelPin = event.target.value;
  }
  updateDate(event) {
    this.parcelDate.newDate = event.target.value;
  }
  cancel() {
    this.changeDate = false;
    this.infoAboutParcel = false;
    this.alertService.clear();
  }
  addressToString(address: Address) {
    return address.buildingNumber + '/' + address.flatNumber + ' ' + address.street + ' ' + address.postalCode + ' ' + address.city;
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


