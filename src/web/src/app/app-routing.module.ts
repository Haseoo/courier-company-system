import { AppComponent } from './app.component';
import { RegisterIndividualComponent } from './registerIndividual/registerIndividual.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterCompanyComponent } from './registerCompany/registerCompany.component';
import { AuthGuard } from './helpers/auth.guard';
import { AdminComponent } from './admin';
import { OfferComponent } from './offer/offer.component';
import { UserComponent } from './user/user.component';
import { MagazineComponent } from './magazine/magazine.component';


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
    canActivate: [AuthGuard]
  },
  {
    path: 'offer',
    component: OfferComponent
  },
  {
    path: 'admin/users',
    component: UserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'admin/magazine',
    component: MagazineComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
