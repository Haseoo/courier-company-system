import { Observable } from 'rxjs';
import { ClientIndividual } from './../model/clientIndividual';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ParcelType } from '../model';


@Injectable({ providedIn: 'root' })
export class ParcelTypeService {

  constructor(private http: HttpClient) { }

  getOffer(): Observable<Array<ParcelType>> {
    return this.http.get<Array<ParcelType>>(environment.API_URL + '/parcelType/offer')
  }
}
