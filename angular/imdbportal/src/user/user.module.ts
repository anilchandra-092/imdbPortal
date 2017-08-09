import {NgModule} from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {UserComponent} from "./user.component";



@NgModule({
  declarations: [
    UserComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule
  ],
  providers: [],
  exports : [UserComponent]
})
export class UserModule {}
