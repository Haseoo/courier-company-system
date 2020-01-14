import { Magazine } from './../model/magazine';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({ providedIn: 'root' })
export class MagazineService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<Array<Magazine>> {
    return this.http.get<Array<Magazine>>(environment.API_URL + '/magazine');
  }
}
