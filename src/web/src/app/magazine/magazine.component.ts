import { Observable } from 'rxjs';
import { MagazineService } from './../services/magazine.service';
import { Component, OnInit } from '@angular/core';
import { Magazine } from '../model/magazine';
import { AlertService } from '../services/alertService';
import { AuthenticationService } from '../services/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Address } from '../model/address';
import { Router } from '@angular/router';
@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  isVisible = false;

  listOfActiveMagazines: Observable<Array<Magazine>>;
  currentMagazine: Magazine;

  magazineForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private magazineService: MagazineService,
    private router: Router
  ) {

  }


  ngOnInit() {
    this.listOfActiveMagazines = this.magazineService.getAll();

  }
  createEditForm() {
    this.magazineForm = this.formBuilder.group({
      id: [this.currentMagazine.id],
      address: this.formBuilder.group({
        city: [this.currentMagazine.address.city, Validators.required],
        street: [this.currentMagazine.address.street, [Validators.required]],
        postalCode: [this.currentMagazine.address.postalCode, [Validators.required]],
        buildingNumber: [this.currentMagazine.address.buildingNumber, Validators.required],
        flatNumber: [this.currentMagazine.address.flatNumber, Validators.required]
      }),
      active: [this.currentMagazine.active, Validators.required]
    });
  }
  get f() { return this.magazineForm.controls; }

  edit(magazine: Magazine) {
    this.isVisible = !this.isVisible;
    this.currentMagazine = magazine;
    this.createEditForm();
  }
  onSubmit() {
    this.currentMagazine = new Magazine(this.magazineForm.value);
    this.magazineService.edit(this.currentMagazine)
      .subscribe(
        data => {
          this.router.navigate(['']);
        },
        error => {
          console.log(error.error.message);
          this.loading = false;
        },
      );
    this.listOfActiveMagazines = this.magazineService.getAll();
    this.isVisible = false;
    this.currentMagazine = null;
  }
  onCancel() {
    this.isVisible = false;
    this.currentMagazine = null;
  }
  showDetails() {
    this.listOfActiveMagazines.forEach(data => {
      console.log(data);
    });
  }

}
