import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { AlertService } from '../services/alertService';
@Component({
  selector: 'app-register-company',
  templateUrl: './registerCompany.component.html',
  styleUrls: ['./registerCompany.component.css']
})
export class RegisterCompanyComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private alertService: AlertService,
  ) {

  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      emailAddress: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required]],
      companyName: [null, Validators.required],
      nip: ['', Validators.required],
      representativeName: [null, Validators.required],
      representativeSurname: [null, Validators.required],
      representativeEmailAddress: [null, [Validators.required, Validators.email]],
      representativePhoneNumber: [null, Validators.required]
    });
  }
  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.userService.registerCompany(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => {
          this.loading = false;
          this.alertService.error(error.error.message);
        });
  }

}
