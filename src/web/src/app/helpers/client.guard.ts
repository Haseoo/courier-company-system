import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { Role } from '../model';

@Injectable({ providedIn: 'root' })
export class ClientGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.authenticationService.currentUserValue;
        if (currentUser) {
            if (currentUser.userType === Role.COMPANY_CLIENT || currentUser.userType === Role.INDIVIDUAL_CLIENT) {
                return true;
            }
            return false;
        }
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return false;
    }
}
