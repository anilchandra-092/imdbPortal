import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule, routingComponents} from "./app.routing.module";
import {ContentServiceComponent} from "../content/content.service.component";
import {ContentModule} from "../content/content.module";
import {AuthentificationServiceComponent} from "./authentification.service.component";



@NgModule({
  declarations: [
    AppComponent,
    routingComponents
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ContentModule
  ],
  providers: [ContentServiceComponent,AuthentificationServiceComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }