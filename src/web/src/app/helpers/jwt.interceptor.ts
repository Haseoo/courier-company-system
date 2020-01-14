import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.authenticationService.currentUserValue;
    const isLoggedIn = currentUser && currentUser.response.accessToken;
    // const isApiUrl = request.url.startsWith(environment.API_URL);
    if (isLoggedIn) {
      request = request.clone({

        setHeaders: {
          Authorization: `Bearer ${currentUser.response.accessToken}`
      }
      });
    }

    return next.handle(request);
  }
}
