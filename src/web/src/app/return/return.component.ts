import { ParcelService } from './../services/parcelService';
import { Component, OnInit } from '@angular/core';
import { Logistician } from '../model/logistician';
import { ParcelMagazineView } from '../model/views/parcelMagazineView';
import { ParcelChangeStateMultipleCommandData } from '../model/commandData/parcelChangeStateMultipleCommandData';
import { ActivatedRoute } from '@angular/router';
import { LogisticianService } from '../services/logisticianService';
import { AlertService } from '../services/alertService';
import { MagazineService } from '../services/magazine.service';
import { AuthenticationService } from '../services/authentication.service';
import { MagazineParcelFilerCommandData } from '../model/commandData/MagazineParcelFilerCommandData';
import { MagazineFilter } from '../model/enums/magazineFilter';
import { Address } from '../model/address';

@Component({
  selector: 'app-return',
  templateUrl: './return.component.html',
  styleUrls: ['./return.component.css']
})
export class ReturnComponent implements OnInit {

  logistician: Logistician;
  parcels: Array<ParcelMagazineView>;
  toAssign = new ParcelChangeStateMultipleCommandData();

  constructor(private activeRoute: ActivatedRoute,
              private logisticianService: LogisticianService,
              private alertService: AlertService,
              private magazineService: MagazineService,
              private parcelService: ParcelService,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.logisticianService.getById(this.authenticationService.getId).subscribe(
      data => {
        this.logistician = data;
        this.getParcelsFromMagazine();
      },
      error => {
        this.alertService.error(error.error.message);
      });
  }
  private getParcelsFromMagazine() {
    this.magazineService.getParcelsById(this.logistician.magazine.id,
      new MagazineParcelFilerCommandData(MagazineFilter.TO_RETURN))
      .subscribe(data => {
        this.parcels = data;
      },
      error =>{
        this.alertService.error(error.error.message);
      });

  }
  addressToString(address: Address) {
    return address.buildingNumber + '/' + address.flatNumber + ' ' + address.street + ' ' + address.postalCode + ' ' + address.city;

  }
  markAsReturned(id: number) {
    this.parcelService.markAsReturn(id).subscribe(data => {
      this.alertService.success('Marked as return');
      this.ngOnInit();
    },
      error => {
        this.alertService.error(error.error.message);

      });
  }

}
