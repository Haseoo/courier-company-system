import { ClientCompany } from './../model/clientCompany';
import { Observable } from 'rxjs';
import { ClientIndividual } from './../model/clientIndividual';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee } from '../model/employee';


@Injectable({ providedIn: 'root' })
export class EmployeeService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<Array<Employee>>(environment.API_URL + '/employee');
    }
    save(employee: Employee) {
        return this.http.put(environment.API_URL + '/employee', employee);
    }
}
