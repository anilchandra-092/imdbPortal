import {NgModule} from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";import {HttpModule} from "@angular/http";

import {SignupComponent} from "../signup/signup.component";
import {LoginComponent} from "./login/login.component";
import {UserdataServiceComponent} from "./userdata.service.component";






@NgModule({
  declarations: [
    SignupComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule,

  ],
  providers: [UserdataServiceComponent],
  exports : [SignupComponent]
})
export class SignupModule {}
