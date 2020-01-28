import { ParcelChangeStateForCourierCommandData } from './../model/commandData/ParcelChangeStateForCourierCommandData';
import { ParcelPickupCommandData } from './../model/commandData/parcelPickupCommandData';
import { StateType } from './../model/enums/stateType';
import { AlertService } from './../services/alertService';
import { CourierService } from './../services/courierService';
import { AuthenticationService } from './../services/authentication.service';
import { Courier } from './../model/courier';
import { Component, OnInit } from '@angular/core';
import { Address } from '../model/address';

@Component({
  selector: 'app-courier',
  templateUrl: './courier.component.html',
  styleUrls: ['./courier.component.css']
})
export class CourierComponent implements OnInit {

  courier: Courier;
  selectedState = 'TO_MAGAZINE';
  canChangePaid = false;
  isChecked = false;

  constructor(private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private courierService: CourierService) { }

  ngOnInit() {
    this.courierService.getCourierById(this.authenticationService.getId).subscribe(data => {
      this.courier = data;
    },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  addressToString(address: Address) {
    return address.buildingNumber + '/' + address.flatNumber + ' ' + address.city + ' ' + address.postalCode + ' ' + address.city;

  }
  currentState(actual: string, other: string) {
    if (actual === other) {
      return true;
    }
    return false;
  }
  pickUpParcel(id: number, wasPaid: boolean) {
    this.courierService.pickUpParcel(this.authenticationService.getId,
      new ParcelPickupCommandData(id, wasPaid))
      .subscribe(
        data => {
          this.ngOnInit();
        },
        error => {
          this.alertService.error(error.error.message);
        }
      );
    console.log('ID:' + id);
    console.log('wasPaid:' + wasPaid);
  }
  setNewState(event: string) {
    this.selectedState = event;
  }
  changeState(parcelId: number) {
    let isPaid = false;
    this.courier.assignedParcels.forEach((v) => {
      if (parcelId === v.id) {
        isPaid = v.paid;
      }
    });
    this.courierService.changeStateForCourier(parcelId,
      new ParcelChangeStateForCourierCommandData(this.selectedState, this.authenticationService.getId, isPaid)).subscribe(
        data => {
          this.ngOnInit();
        },
        error => {
          this.alertService.error(error.error.message);
        });
  }
  canBePaid() {
    if (this.selectedState === 'DELIVERED' || this.selectedState === 'RETURNED') {
      return true;
    }
    return false;
  }

  onChangeIsPaid(id: number, event: any) {
    let parcelPosition = 0;
    this.courier.assignedParcels.forEach((v) => {
      if (id === v.id) {
        return
      }
      parcelPosition++;
    });
    this.courier.assignedParcels[parcelPosition].paid = event;
  }
  existParcelsWithAssignedSate() {
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.courier.assignedParcels.length; i++) {
      if (this.courier.assignedParcels[i].currentState === StateType.ASSIGNED) {
        return true;
      }
    }
    return false;
  }
}
