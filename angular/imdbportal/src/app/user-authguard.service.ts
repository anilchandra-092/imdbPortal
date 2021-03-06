import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {ContentServiceComponent} from "../content/content.service.component";
import {AuthentificationServiceComponent} from "./authentification.service.component";

@Injectable()
export class UserAuthguardService implements CanActivate {
  private errorMsg;
  private isValidSession;

  constructor(
    private router: Router,
    private contentService:ContentServiceComponent,
    private authService:AuthentificationServiceComponent
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

      this.contentService. checkForSessionWithUserRole("user")
      .subscribe(
        data => this.isValidSession = data,
        dataError => this.errorMsg = dataError,
        ()=>{ this.checkForSessionWithUserRoleCallBackFuncion();}
      );

    return this.isValidSession;
  }

  checkForSessionWithUserRoleCallBackFuncion(){

    console.log("call back function is valid user session: ==>",this.isValidSession);
    if(this.isValidSession==true){
      console.log("==> Valid session");
      this.router.navigate(['/user']);
    }
    else{
      console.log("==> Invalid session");
      if(this.authService.auth.role=="admin"){
        this.router.navigate(['/admin']);
      }else{
        this.router.navigate(['/login']);
      }
    }

    }
}
