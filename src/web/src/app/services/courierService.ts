import { Courier } from './../model/courier';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({ providedIn: 'root' })
export class CourierService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<Array<Courier>>(environment.API_URL + '/employee/courier');
    }
}
