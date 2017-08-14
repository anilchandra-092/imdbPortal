import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {ContentServiceComponent} from "../content/content.service.component";
import {AuthentificationServiceComponent} from "./authentification.service.component";

@Injectable()
export class AdminAuthguardService implements CanActivate {
  private errorMsg;
  private isValidSession;

  constructor(
    private router: Router,
    private contentService:ContentServiceComponent,
    private authService:AuthentificationServiceComponent
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    this.contentService. checkForSessionWithUserRole("admin")
      .subscribe(
        data => this.isValidSession = data,
        dataError => this.errorMsg = dataError,
        ()=>{ this.checkForSessionWithUserRoleCallBackFunction();}
      );
    return this.isValidSession;
  }
  checkForSessionWithUserRoleCallBackFunction(){

    console.log("call back function is valid admin session: ==>",this.isValidSession);
    if(this.isValidSession==true){
      console.log("==> Valid session");
      this.router.navigate(['/admin']);
    }
    else{
      console.log("==> Invalid session");
      if(this.authService.auth.role=="user"){
        this.router.navigate(['/user']);
      }else{
        this.router.navigate(['/login']);
      }
    }

  }
}
