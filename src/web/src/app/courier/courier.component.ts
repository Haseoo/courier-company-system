import { ParcelChangeStateForCourierCommandData } from './../model/commandData/ParcelChangeStateForCourierCommandData';
import { ParcelChangeStateMultipleCommandData } from './../model/commandData/parcelChangeStateMultipleCommandData';
import { ParcelPickupCommandData } from './../model/commandData/parcelPickupCommandData';
import { StateType } from './../model/enums/stateType';
import { AlertService } from './../services/alertService';
import { CourierService } from './../services/courierService';
import { AuthenticationService } from './../services/authentication.service';
import { Courier } from './../model/courier';
import { Component, OnInit } from '@angular/core';
import { Address } from '../model/address';
import { MagazineService } from '../services/magazine.service';
import { Magazine } from '../model/magazine';

@Component({
  selector: 'app-courier',
  templateUrl: './courier.component.html',
  styleUrls: ['./courier.component.css']
})
export class CourierComponent implements OnInit {
  courier: Courier;
  selectedState = new Map();
  canChangePaid = false;
  isPaidMap = new Map();
  isChecked = false;
  chosenMagazineIdMap = new Map();
  magazineList: Array<Magazine>;

  constructor(private authenticationService: AuthenticationService,
              private alertService: AlertService,
              private magazineService: MagazineService,
              private courierService: CourierService) { }

  ngOnInit() {
    this.courierService.getCourierById(this.authenticationService.getId).subscribe(data => {
      this.courier = data;
      this.courier.assignedParcels.forEach((v) => {
        this.isPaidMap.set(v.id, v.paid);
        this.selectedState.set(v.id, 'DELIVERED');
        // tslint:disable-next-line: no-shadowed-variable
        this.magazineService.getAll().subscribe(data => {
          this.magazineList = data;
          data.forEach(mag => {
            this.chosenMagazineIdMap.set(v.id, mag.id);
          });
        },
          error => {
            this.alertService.error(error.error.message);
          });
      });
    },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  addressToString(address: Address) {
    return address.buildingNumber + '/' + address.flatNumber + ' ' + address.street + ' ' + address.postalCode + ' ' + address.city;

  }
  currentState(actual: string, other: string) {
    if (actual === other) {
      return true;
    }
    return false;
  }
  pickUpParcel(id: number, wasPaid: boolean) {
    let paid;
    if(wasPaid){
      paid = false;
    }else{
      paid = this.isPaidMap.get(id);
    }
    this.courierService.pickUpParcel(this.authenticationService.getId,
      new ParcelPickupCommandData(id, paid))
      .subscribe(
        data => {
          this.ngOnInit();
        },
        error => {
          this.alertService.error(error.error.message);
        }
      );
  }
  setNewState(parcelId: number, event: string) {
    this.selectedState.set(parcelId, event);
  }
  changeState(parcelId: number) {
    if (this.selectedState.get(parcelId) === 'DELIVERED' || this.selectedState.get(parcelId) === 'RETURNED') {
      const changeState = new ParcelChangeStateForCourierCommandData(this.selectedState.get(parcelId),
        this.authenticationService.getId,
        this.isPaidMap.get(parcelId));
      this.courierService.changeStateForCourier(parcelId, changeState).subscribe((data) => {
        this.ngOnInit();
      },
        error => {
          this.alertService.error(error.error.message);
        });

    } else if (this.selectedState.get(parcelId) === 'IN_MAGAZINE') {
      const parcelsId = new ParcelChangeStateMultipleCommandData();
      parcelsId.addToList(parcelId);
      this.magazineService.addParcels(this.chosenMagazineIdMap.get(parcelId), parcelsId).subscribe(
        data => {
          this.ngOnInit();
        },
        error => {
          this.alertService.error(error.error.message);
        });
    }
  }

  onChangeIsPaid(id: number, event: any) {
    this.isPaidMap.set(id, !this.isPaidMap.get(id));
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
  setMagazineIdForParcel(parcelId: number, event: number) {
    this.chosenMagazineIdMap.set(parcelId, event);
  }
}
