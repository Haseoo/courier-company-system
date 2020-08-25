import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../model/client';
import { CompanyDetails } from '../model/commandData/CompanyDetails';


@Injectable({ providedIn: 'root' })
export class ClientService {

  constructor(private http: HttpClient) { }

  getClient(id: number) {
    return this.http.get<Client>(environment.API_URL + '/client/' + id);
  }
  getClientCompany(id: number) {
    return this.http.get<CompanyDetails>(environment.API_URL + '/client/company/'+ id);
  }
}
