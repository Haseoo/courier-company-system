import { PeselValidator } from '../validators/peselValidator';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
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
    private authenticationService: AuthenticationService,
    private userService: UserService,
  ) {

    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/home']);
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      emailAddress: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      companyName: ['', Validators.required]
      // nip: ['', Validators.required],
      // representativeName: ['', Validators.required],
      // representativeSurname: ['', Validators.required],
      // representativeEmail: ['', Validators.required, Validators.email],
      // representativePhoneNumber: ['', Validators.required]
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
        });
  }
}
