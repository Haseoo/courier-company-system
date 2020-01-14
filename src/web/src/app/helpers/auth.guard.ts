import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { Role } from '../model';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // if (currentUser.userType === Role.ADMIN) {
      //   this.router.navigate(['/admin']).catch((e: Error) => console.error(e));
      //   return true;
      // }
      // if (currentUser.userType === Role.INDIVIDUAL_CLIENT || currentUser.userType === Role.COMPANY_CLIENT) {
      //   this.router.navigate(['/client']);
      // }
      // if (currentUser.userType === Role.COURIER) {
      //   this.router.navigate(['/courier']);
      // }
      // if (currentUser.userType === Role.LOGISTICIAN) {
      //   this.router.navigate(['/logistician']);
      // }
      return true;
    }
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
