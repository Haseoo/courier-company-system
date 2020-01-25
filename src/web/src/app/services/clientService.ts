import { ParcelDateMoveCommandData } from '../model/ParcelDateMoveCommandData';
import { ParcelClientView } from '../model/views/parcelClientView';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../model/client';


@Injectable({ providedIn: 'root' })
export class ClientService {

  constructor(private http: HttpClient) { }

  getClient(id: number) {
    return this.http.get<Client>(environment.API_URL + '/client/' + id);
  }
}
