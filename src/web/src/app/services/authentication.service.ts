import { Role } from '../model/enums/role';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { User } from '../model/user';
import * as jwt_decode from 'jwt-decode';




@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }
  isLoggedIn(): boolean {
    if (this.currentUserValue) {
      return true;
    }
    return false;
  }
  isAdmin() {
    if (this.currentUserValue && this.currentUserValue.userType === Role.ADMIN) {
      return true;
    }
    return false;
  }
  isClient() {
    if (this.currentUserValue &&
      (this.currentUserValue.userType === Role.COMPANY_CLIENT || this.currentUserValue.userType === Role.INDIVIDUAL_CLIENT)) {
      return true;
    }
    return false;
  }
  isLogistician() {
    if (this.currentUserValue && this.currentUserValue.userType === Role.LOGISTICIAN) {
      return true;
    }
    return false;
  }
  isCourier() {
    if (this.currentUserValue && this.currentUserValue.userType === Role.COURIER) {
      return true;
    }
    return false;
  }
  public get getUsername(): any {
    return this.currentUserValue.userName;
  }
  public get getId(): any {
    return this.currentUserValue.id;
  }
  login(username: string, password: string) {
    return this.http.post<any>((environment.API_URL + `/login`), { username, password })
      .pipe(map(user => {
        console.log(user);
        if (user && user.response.accessToken) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }
        return user;
      }));
  }
  loginByGoogle(token: string) {
    if (token) {
      var decoded = jwt_decode(token);
      console.log(decoded);
      const user: any = {
        id: decoded.sub,
        response: {
          accessToken: token,
          tokenType: 'Bearer'
        },
        userType: Role.INDIVIDUAL_CLIENT
      };
      console.log(user);
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(user);
      return user;
    }
  }
  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
