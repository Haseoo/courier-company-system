import { ParcelChangeStateMultipleCommandData } from '../model/commandData/parcelChangeStateMultipleCommandData';
import { Courier } from './../model/courier';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AssignedParcel } from '../model/assignedParcel';


@Injectable({ providedIn: 'root' })
export class CourierService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<Array<Courier>>(environment.API_URL + '/employee/courier');
    }
    assignParcel(id: number, parcels: ParcelChangeStateMultipleCommandData) {
        return this.http.post<AssignedParcel>(environment.API_URL + '/employee/courier/' + id + '/parcelAssign', parcels);
    }
}
