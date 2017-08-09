import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "../content/home/home.component";
import {AdminComponent} from "../admin/admin.component";
import {SignupComponent} from "../signup/signup.component";
import {MovieDataComponent} from "../content/movie-data/movie-data.component";
import {LoginComponent} from "../signup/login/login.component";
import {UserComponent} from "../user/user.component";
import {AddMovieComponent} from "../admin/add-movie/add-movie.component";
import {SearchComponent} from "../search/search.component";
import {EditMovieComponent} from "../admin/edit-movie/edit-movie.component";


const routes:Routes=[
  {path:'',redirectTo:"/home",pathMatch:"full"},
  {path:"home",children:[
    {path:"",component:HomeComponent,data:{  pageid:1 }},
    {path:":pageid",children:[
      {path:"",component:HomeComponent},
      {path:":mid",component:MovieDataComponent}
    ]}
  ]},
  {path:"admin",children:[
    {path:"",component:AdminComponent,data:{  pageid:1,categeory:"all" }},
    {path:"editMovie",component:EditMovieComponent},
    {path:"addMovie",component:AddMovieComponent},
    {path:":pageid",component:AdminComponent},
  ]},
  {path:"signup",component:SignupComponent},
  {path:"user",children:[
    {path:"",component:UserComponent,data:{  pageid:1 }},
    {path:":pageid",children:[
      {path:"",component:UserComponent},
      {path:":mid",component:MovieDataComponent}
    ]}
  ]},
  {path:"login",component:LoginComponent},
  {path:"search",component:SearchComponent},
  {path:"search/:mid",component:MovieDataComponent}
];

@NgModule({
  imports:[RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutingModule{}

export const routingComponents=[
  HomeComponent,
  MovieDataComponent,
];
