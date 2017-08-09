import {NgModule} from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";

import {SearchComponent} from "./search.component";




@NgModule({
  declarations: [
    SearchComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule
  ],
  providers: [],
  exports : [SearchComponent]
})
export class SearchModule {}
