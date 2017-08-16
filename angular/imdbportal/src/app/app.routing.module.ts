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
import {AdminAuthguardService} from "./admin-authguard.service";
import {UserAuthguardService} from "./user-authguard.service";
import {UsersApprovalComponent} from "../admin/users-approval/users-approval.component";


const routes:Routes=[

  {path:'',redirectTo:"/home",pathMatch:"full"},


  {path:"home",children:[
    {path:"",component:HomeComponent,data:{  pageid:1 }},
    {path:":pageid",children:[
      {path:"",component:HomeComponent},
      {path:":mid",component:MovieDataComponent}
    ]}
  ]},


  {path:"user",children:[
    {path:"",component:UserComponent,data:{  pageid:1 }},
    {path:":pageid",children:[
      {path:"",component:UserComponent},
      {path:":mid",component:MovieDataComponent}
    ]}
  ], canActivate: [UserAuthguardService]},



  {path:"admin",children:[
    {path:"",component:AdminComponent,data:{  pageid:1,categeory:"all" }},
    {path:"editMovie",component:EditMovieComponent},
    {path:"usersApprove",component:UsersApprovalComponent},
    {path:"addMovie",component:AddMovieComponent},
    {path:":pageid",component:AdminComponent},
  ], canActivate: [AdminAuthguardService]},




  {path:"login",component:LoginComponent},
  {path:"signup",component:SignupComponent},
  {path:"search",component:SearchComponent},
  {path:"search/:mid",component:MovieDataComponent},
  {path:"**",redirectTo:"/home",pathMatch:"full"}
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
