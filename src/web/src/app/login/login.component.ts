import { AlertService } from './../services/alertService';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { first } from 'rxjs/operators';
import { Role } from '../model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/home']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  get f() { return this.loginForm.controls; }
  onSubmit() {
    this.submitted = true;
    this.alertService.clear();
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.userType === Role.ADMIN) {
            this.router.navigate(['/admin']);
          }
          if (data.userType === Role.COMPANY_CLIENT || data.userType === Role.INDIVIDUAL_CLIENT) {
            this.router.navigate(['/client']);
          }
          if (data.userType === Role.LOGISTICIAN) {
            this.router.navigate(['/logistician']);
          }
          if (data.userType === Role.COURIER) {
            this.router.navigate(['/courier']);
          }
        },
        error => {
          this.error = error.error.message;
          this.loading = false;
        },
      );
  }

}
