import { environment } from './../../environments/environment.prod';
import { AlertService } from './../services/alertService';
import { PeselValidator } from '../validators/peselValidator';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first, map } from 'rxjs/operators';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './registerIndividual.component.html',
  styleUrls: ['./registerIndividual.component.css']
})
export class RegisterIndividualComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService
  ) {

  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      userName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      emailAddress: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      name: ['', Validators.required],
      surname: ['', Validators.required],
      pesel: ['', [Validators.required, PeselValidator.validatePesel]]
    });
  }

  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;


    if (this.registerForm.invalid) {
      return;
    }
    this.loading = true;
    this.userService.registerIndividual(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error.error.message);
          this.loading = false;
        });
  }
}
