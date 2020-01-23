import { Magazine } from './../model/magazine';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';


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
}
