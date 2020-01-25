import { CheckComponent } from './check/check.component';
import { ParcelComponent } from './parcel/parcel.component';
import { CourierGuard } from './helpers/courier.guard';
import { CourierComponent } from './courier/courier.component';
import { ClientGuard } from './helpers/client.guard';
import { ClientCompany } from './model/clientCompany';
import { LogisticianGuard } from './helpers/logistician.guard';
import { LogisticianComponent } from './logistician/logistician.component';
import { EmployeeComponent } from './employee/employee.component';
import { MagazineComponent } from './magazine/magazine.component';
import { AdminGuard } from './helpers/admin.guard';
import { AppComponent } from './app.component';
import { RegisterIndividualComponent } from './registerIndividual/registerIndividual.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterCompanyComponent } from './registerCompany/registerCompany.component';
import { AdminComponent } from './admin';
import { OfferComponent } from './offer/offer.component';
import { UserComponent } from './user/user.component';
import { MagazinesComponent } from './magazines/magazines.component';
import { ClientComponent } from './client/client.component';


const routes: Routes = [
  {
    path: ' ', component: AppComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'register/individual',
    component: RegisterIndividualComponent
  },

  {
    path: 'register/company',
    component: RegisterCompanyComponent
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'offer',
    component: OfferComponent
  },
  {
    path: 'admin/users',
    component: UserComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'admin/magazines',
    component: MagazinesComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'admin/magazines/:id',
    component: MagazineComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'admin/employee',
    component: EmployeeComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'logistician',
    component: LogisticianComponent,
    canActivate: [LogisticianGuard]
  },
  {
    path: 'client',
    component: ClientComponent,
    canActivate: [ClientGuard]
  },
  {
    path: 'courier',
    component: CourierComponent,
    canActivate: [CourierGuard]
  },
  {
    path: 'check',
    component: CheckComponent,
    
  },
  {
    path: 'check/parcel/:id',
    component: ParcelComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
