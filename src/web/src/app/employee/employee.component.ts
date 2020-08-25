import { AlertService } from './../services/alertService';
import { EmployeeType } from './../model/enums/employeeType';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { EmployeeService } from './../services/employeeService';
import { Employee } from './../model/employee';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {
  addEmployeeForm: FormGroup;
  employees: Array<Employee>;
  displayEmployeeForm = false;
  submitted = false;
  typeOfEmployees: Array<EmployeeType>;

  constructor(private employeeService: EmployeeService,
    private formBuilder: FormBuilder,
    private alertService: AlertService) { }

  get f() { return this.addEmployeeForm.controls; }

  ngOnInit() {
    this.employeeService.getAll().subscribe(res => {
      this.employees = res;
    });
  }
  createAddEmployeeForm() {
    this.addEmployeeForm = this.formBuilder.group({
      userName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      name: ['', Validators.required],
      surname: ['', Validators.required],
      pesel: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      employeeType: [EmployeeType.LOGISTICIAN, Validators.required]
    });
  }
  showEmployeeForm() {
    this.displayEmployeeForm = !this.displayEmployeeForm;
    this.createAddEmployeeForm();
  }
  saveEmployee() {
    this.submitted = true;
    this.employeeService.save(this.addEmployeeForm.value).subscribe(rep => {
      this.alertService.success('Employee add correct');
      this.displayEmployeeForm = false;
      this.ngOnInit();
    },
      error => {
        this.alertService.error(error.error.message);
      }
    );

  }
  cancel() {
    this.displayEmployeeForm = false;
    this.alertService.clear();
  }
}
