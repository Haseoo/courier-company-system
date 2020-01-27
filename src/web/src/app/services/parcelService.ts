import { ParcelDateMoveCommandData } from '../model/commandData/ParcelDateMoveCommandData';
import { ParcelClientView } from './../model/views/parcelClientView';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ParcelType } from '../model';
import { CommandAddData } from '../model/commandData/commandAddData';


@Injectable({ providedIn: 'root' })
export class ParcelService {

  constructor(private http: HttpClient) { }

  getOffer(){
    return this.http.get(environment.API_URL + '/parcelType/offer');
  }
  getParcelByClient(id: number) {
    return this.http.get<ParcelClientView>(environment.API_URL + '/parcel/get/' + id);
  }
  moveDate(id: number, commandData: ParcelDateMoveCommandData) {
    commandData.parcelPin = commandData.parcelPin.split('');
    commandData.newDate = commandData.newDate.replace('/', '-');
    return this.http.post(environment.API_URL + '/parcel/' + id + '/moveDate', commandData);
  }
  getParcelType() {
    return this.http.get<ParcelType[]>(environment.API_URL + '/parcelType');
  }
  save(commandAddData: CommandAddData) {
    return this.http.put<ParcelClientView>(environment.API_URL + '/parcel', commandAddData);
  }
  markAsReturn(id: number) {
    return this.http.post(environment.API_URL + '/parcel/' + id + '/changeReceiver', null);
  }
}
