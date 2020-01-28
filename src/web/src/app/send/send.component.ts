import { CommandAddData } from './../model/commandData/commandAddData';
import { AlertService } from './../services/alertService';
import { ParcelService } from './../services/parcelService';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ParcelType } from '../model';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-send',
  templateUrl: './send.component.html',
  styleUrls: ['./send.component.css']
})
export class SendComponent implements OnInit {
  addParcelForm: FormGroup;
  submitted = false;
  typeOfParcels: ParcelType[];
  commandAddData: CommandAddData;

  formControlError(path: string): any {
    return this.addParcelForm.get(path).errors;
  }
  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private parcelService: ParcelService,
              private alertService: AlertService,
              private router: Router) { }

  ngOnInit() {
    this.parcelService.getParcelType().subscribe(date => {
      this.typeOfParcels = date;
      this.alertService.clear();
    },
      error => {
        this.alertService.error(error.error.message);
      }
    );

    this.addParcelForm = this.formBuilder.group({
      parcelTypeId: ['', Validators.required],
      deliveryAddress: this.formBuilder.group({
        city: ['', Validators.required],
        street: ['', Validators.required],
        postalCode: ['', Validators.required],
        buildingNumber: ['', Validators.required],
        flatNumber: ['', Validators.required]
      }),
      senderAddress: this.formBuilder.group({
        city: ['', Validators.required],
        street: ['', Validators.required],
        postalCode: ['', Validators.required],
        buildingNumber: ['', Validators.required],
        flatNumber: ['', Validators.required]
      }),
      receiverContactData: this.formBuilder.group({
        name: ['', Validators.required],
        surname: ['', Validators.required],
        emailAddress: ['', Validators.required],
        phoneNumber: ['', Validators.required]
      }),
      parcelFee: ['', Validators.required],
      priority: ['false', Validators.required],
      senderId: [this.authenticationService.getId]
    });

  }
  getPrice(id: number): number {
    this.typeOfParcels.forEach(data => {
      if (data.id === id) {
        return data.price;
      }
    });
    return this.typeOfParcels[0].price;
  }
  onSubmit() {
    this.submitted = true;
    if (this.addParcelForm.invalid) {
      return;
    }
    this.commandAddData = this.addParcelForm.value;

    this.parcelService.save(this.commandAddData).subscribe(data => {
      this.router.navigate(['/client/check/parcel', data.id]);
    },
      error => {
        this.alertService.error(error.error.message);
      });
  }
}
