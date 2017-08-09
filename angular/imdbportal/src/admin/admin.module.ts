import {NgModule} from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {AdminComponent} from "./admin.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AddMovieComponent} from "./add-movie/add-movie.component";
import {EditMovieComponent} from "./edit-movie/edit-movie.component";




@NgModule({
  declarations: [
    AddMovieComponent,
    EditMovieComponent,
    AdminComponent,

  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  providers: [],
  exports : [AdminComponent]
})
export class AdminModule {}
