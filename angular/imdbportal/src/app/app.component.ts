import { Component } from '@angular/core';
import {EventEmmiterService} from "../content/event-emmiter.service";
import {ContentServiceComponent} from "../content/content.service.component";
import {Router} from "@angular/router";
import {AuthentificationServiceComponent} from "./authentification.service.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  private userData;
  private isValidSession;

  constructor(
    private eventEmiterService:EventEmmiterService,
    private contentService:ContentServiceComponent,
    private router:Router,
    private authService:AuthentificationServiceComponent
    ){


    this.contentService. checkForSession()
      .subscribe(
        data => this.isValidSession = data,
        dataError => {console.log(dataError);},
        ()=>{ this.checkForSessionCallBackFuncion();}
      );

    //this.eventEmiterService.changeLoginStatus(false);
  }

  checkForSessionCallBackFuncion(){

    if(this.isValidSession==true){
      console.log("App ==> Valid session");
      this.eventEmiterService.changeLoginStatus(true);
      this.contentService.getSessionData()
        .subscribe(
          data=>{this.userData=data},
          error=>{console.log(error);},
          ()=>{this.getSessionDataCallBackFunction();}
        );
    }
    else{
      console.log("App ==> Invalid session");
      this.eventEmiterService.changeLoginStatus(false);
    }
  }

  getSessionDataCallBackFunction(){
    if(this.userData){
      this.authService.setAuthObject(this.userData.id,this.userData.role);
      if(this.userData.role=="user"){
        this.router.navigate(['/user']);
      }
      else{
        this.router.navigate(['/admin']);
      }
    }
    else{
      console.log("user data getting:",this.userData);
    }
  }
}

