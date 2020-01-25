import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RegisterIndividualComponent } from './registerIndividual';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login';
import { HomeComponent } from './home/home.component';
import { JwtInterceptor, ErrorInterceptor } from './helpers';
import { RegisterCompanyComponent } from './registerCompany/registerCompany.component';

import { AdminComponent } from './admin/admin.component';
import { OfferComponent } from './offer/offer.component';
import { HeaderComponent } from './header/header.component';
import { UserComponent } from './user/user.component';
import { AlertComponent } from './alert/alert.component';
import { MagazinesComponent } from './magazines/magazines.component';
import { MagazineComponent } from './magazine/magazine.component';
import { EmployeeComponent } from './employee/employee.component';
import { LogisticianComponent } from './logistician/logistician.component';
import { ClientComponent } from './client/client.component';
import { CourierComponent } from './courier/courier.component';
import { ParcelComponent } from './parcel/parcel.component';
import { CheckComponent } from './check/check.component';
import { SendComponent } from './send/send.component';


@NgModule({
  declarations: [
    AppComponent,
    RegisterIndividualComponent,
    LoginComponent,
    HomeComponent,
    RegisterCompanyComponent,
    AlertComponent,
    AdminComponent,
    OfferComponent,
    HeaderComponent,
    UserComponent,
    MagazinesComponent,
    MagazineComponent,
    EmployeeComponent,
    LogisticianComponent,
    ClientComponent,
    CourierComponent,
    ParcelComponent,
    CheckComponent,
    SendComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
