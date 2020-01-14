import { Observable } from 'rxjs';
import { MagazineService } from './../services/magazine.service';
import { Component, OnInit } from '@angular/core';
import { Magazine } from '../model/magazine';
import { AlertService } from '../services/alertService';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  isVisible = false;

  listOfActiveMagazines: Observable<Array<Magazine>>;
  currentMagazine: Magazine;

  registerForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private magazineService: MagazineService
  ) {

  }


  ngOnInit() {
    this.listOfActiveMagazines = this.magazineService.getAll();
    if (this.isVisible === true) {

    }
  }
  edit(magazine: Magazine) {
    this.isVisible = !this.isVisible;
    this.currentMagazine = magazine;
  }
  onSubmit(){

  }

}
