import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class PayPalService {

    constructor(private http: HttpClient) { }

    makePayment(id: number) {
        let httpParams = new HttpParams().set('id', id.toString());
        return this.http.post(environment.API_URL + '/payments/paypal/', httpParams);
    }
}
