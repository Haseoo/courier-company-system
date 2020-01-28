import { ParcelChangeStateMultipleCommandData } from './../model/commandData/parcelChangeStateMultipleCommandData';
import { MagazineParcelFilerCommandData } from './../model/commandData/MagazineParcelFilerCommandData';
import { Magazine } from './../model/magazine';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MagazineView } from '../model/views/magazineView';
import { ParcelMagazineView } from '../model/views/parcelMagazineView';


@Injectable({ providedIn: 'root' })
export class MagazineService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<Array<Magazine>> {
    return this.http.get<Array<Magazine>>(environment.API_URL + '/magazine');
  }
  edit(magazine: Magazine) {
    const param = new HttpParams().set('id', magazine.id + '');
    const header = new Headers().append('Accept', 'application/json');
    const id = magazine.id;
    delete magazine.id;
    return this.http.post(environment.API_URL + '/magazine/' + id, magazine);
  }
  save(magazine: Magazine) {
    return this.http.put(environment.API_URL + '/magazine/', magazine);
  }
  getById(id: number) {
    return this.http.get<MagazineView>(environment.API_URL + '/magazine/' + id);
  }
  getMagazineById(id: number) {
    return this.http.get(environment.API_URL + '/magazine/' + id);
  }
  getParcelsById(id: number, magazine: MagazineParcelFilerCommandData) {
    return this.http.post<Array<ParcelMagazineView>>(environment.API_URL + '/magazine/' + id + '/parcels', magazine);
  }
  addParcels(magazineId: number, parcelsIds: ParcelChangeStateMultipleCommandData){
    return this.http.post(environment.API_URL + '/magazine/' + magazineId + '/parcels/add', parcelsIds);
  }
}
