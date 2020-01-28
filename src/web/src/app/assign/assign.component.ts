import { CourierService } from './../services/courierService';
import { ParcelMagazineView } from './../model/views/parcelMagazineView';
import { MagazineService } from './../services/magazine.service';
import { LogisticianService } from './../services/logisticianService';
import { AuthenticationService } from './../services/authentication.service';
import { MagazineParcelFilerCommandData } from './../model/commandData/MagazineParcelFilerCommandData';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AlertService } from '../services/alertService';
import { Logistician } from '../model/logistician';
import { MagazineFilter } from '../model/enums/magazineFilter';
import { Address } from '../model/address';
import { ParcelChangeStateMultipleCommandData } from '../model/commandData/parcelChangeStateMultipleCommandData';

@Component({
  selector: 'app-asign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {
  parameter: any;
  logistician: Logistician;
  magazine: any;
  toAssign = new  ParcelChangeStateMultipleCommandData();

  constructor(private activeRoute: ActivatedRoute,
              private logisticianService: LogisticianService,
              private alertService: AlertService,
              private magazineService: MagazineService,
              private courierService: CourierService,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.parameter = this.activeRoute.snapshot.paramMap.get('id');
    this.logisticianService.getById(this.authenticationService.getId).subscribe(
      data => {
        this.logistician = data;
        this.getParcelsFromMagazine();
      },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  isParameter() {
    if (this.parameter) {
      return true;
    }
    return false;
  }
  private getParcelsFromMagazine() {
    this.magazineService.getMagazineById(this.logistician.magazine.id).subscribe(data => {
        this.magazine = data;
      }, error => {
        this.alertService.error(error.error.message);
      });
  }
  addressToString(address: Address) {
    return address.buildingNumber + '/' + address.flatNumber + ' ' + address.street + ' ' + address.postalCode + ' ' + address.city;

  }
  assignToList(id: string) {
    this.toAssign.parcelsIds.push(Number(id));
  }
  assignAllParcel() {
    this.courierService.assignParcel(Number(this.parameter), this.toAssign).subscribe(data => {
      this.ngOnInit();
    },
      error => {
        this.alertService.error(error.error.message);
      }
    );
  }

}
