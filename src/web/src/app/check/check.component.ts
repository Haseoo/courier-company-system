import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ParcelClientView } from '../model/views/parcelClientView';
import { ParcelService } from '../services/parcelService';
import { AlertService } from '../services/alertService';

@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.css']
})
export class CheckComponent implements OnInit {

  parcelId = 0;

  constructor(private formBuilder: FormBuilder,
              private parcelService: ParcelService,
              private alertService: AlertService,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {
    
  }
  isClient(){
    return this.authenticationService.isClient();
  }

}
