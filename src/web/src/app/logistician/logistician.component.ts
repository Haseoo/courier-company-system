import { MagazineFilter } from './../model/enums/magazineFilter';
import { MagazineParcelFilerCommandData } from './../model/commandData/MagazineParcelFilerCommandData';
import { MagazineService } from './../services/magazine.service';
import { AuthenticationService } from './../services/authentication.service';
import { LogisticianService } from './../services/logisticianService';
import { AlertService } from './../services/alertService';
import { CourierService } from './../services/courierService';
import { Courier } from './../model/courier';
import { Component, OnInit } from '@angular/core';
import { Logistician } from '../model/logistician';
import { Address } from '../model/address';
import { ParcelMagazineView } from '../model/views/parcelMagazineView';

@Component({
  selector: 'app-logistician',
  templateUrl: './logistician.component.html',
  styleUrls: ['./logistician.component.css']
})
export class LogisticianComponent implements OnInit {

  displayParcelsList = false;
  displayAsignParcelForm = false;
  couriers: Array<Courier>;
  parcels: Array<ParcelMagazineView>;
  logistician: Logistician;

  constructor(private courierService: CourierService,
              private authenticationService: AuthenticationService,
              private logisticianService: LogisticianService,
              private magazineService: MagazineService,
              private alertService: AlertService) { }

  ngOnInit() {
    this.courierService.getAll().subscribe(data => {
      this.couriers = data;
    }, error => {
      this.alertService.error(error.error.message);
    });
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
      new MagazineParcelFilerCommandData(MagazineFilter.ASSIGNED_TO_MAGAZINE))
      .subscribe(data => {
        this.parcels = data;
      }, error => {
        this.alertService.error(error.error.message);
      });
  }
  addressToString(address: Address) {
    return address.buildingNumber + '/' + address.flatNumber + ' ' + address.street + ' ' + address.postalCode + ' ' + address.city;

  }
  assignParcel() {
    this.displayParcelsList = false;
    this.displayAsignParcelForm = !this.displayAsignParcelForm;
  }
  showParcels() {
    this.displayAsignParcelForm = false;
    this.displayParcelsList = !this.displayParcelsList;
  }

}
