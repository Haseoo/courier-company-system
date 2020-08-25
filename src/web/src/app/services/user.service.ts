import { ClientCompany } from './../model/clientCompany';
import { Observable } from 'rxjs';
import { ClientIndividual } from './../model/clientIndividual';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';


@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<Array<User>> {
    return this.http.get<Array<User>>(environment.API_URL + '/user');
  }
  registerCompany(user: ClientCompany) {
    user.password = user.password.split('');
    return this.http.post(environment.API_URL + `/client/company/register`, user);
  }
  registerIndividual(user: ClientIndividual) {
    user.password = user.password.split('');
    return this.http.post(environment.API_URL + `/client/individual/register`, user);
  }
  setAsInActive(user: User) {
    return this.http.patch(environment.API_URL + '/user/' + user.id, {'active':false});
  }
  setAsActive(user: User) {
    return this.http.patch(environment.API_URL + '/user/' + user.id, {'active':true});
  }
}
