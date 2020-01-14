import { Role } from './../model/role';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { User } from '../model/user';



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
  public get getUsername(): any {
    return this.currentUserValue.userName;
  }
  login(username: string, password: string) {
    return this.http.post<any>((environment.API_URL + `/login`), { username, password })
      .pipe(map(user => {
        if (user && user.response.accessToken) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }
        return user;
      }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
