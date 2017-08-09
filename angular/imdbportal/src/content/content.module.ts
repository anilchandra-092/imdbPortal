import {NgModule} from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AdminModule} from "../admin/admin.module";
import {SignupModule} from "../signup/signup.module";

import {ContentComponent} from "./content.component";
import {RouterModule} from "@angular/router";
import {UserModule} from "../user/user.module";
import {MoviesRoutingServiceComponent} from "../movies/movies-routing.service.component";
import {SearchModule} from "../search/search.module";




@NgModule({
  declarations: [
    ContentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule,
    AdminModule,
    SignupModule,
    UserModule,
    SearchModule
  ],
  providers: [MoviesRoutingServiceComponent],
  exports : [ContentComponent]
})
export class ContentModule {}
