import { Logistician } from './../model/logistician';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({ providedIn: 'root' })
export class LogisticianService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<Array<Logistician>>(environment.API_URL + '/employee/logistician');
    }
    getById(id: number) {
        return this.http.get<Logistician>(environment.API_URL + '/employee/logistician/' + id);
    }
}
