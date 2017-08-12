import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {ContentServiceComponent} from "../content/content.service.component";

@Injectable()
export class UserAuthguardService implements CanActivate {
  private errorMsg;
  private isValidSession;

  constructor(
    private router: Router,
    private contentService:ContentServiceComponent
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

      this.contentService. checkForSessionWithUserRole("user")
      .subscribe(
        data => this.isValidSession = data,
        dataError => this.errorMsg = dataError,
        ()=>{
          console.log("call back function is valid user session: ==>",this.isValidSession);
          if(this.isValidSession==true){
            console.log("==> Valid session");
            this.router.navigate(['/user']);
          }
          else{
            console.log("==> Invalid session");
            this.router.navigate(['/login']);
          }
        }
      );

    return this.isValidSession;
  }
}
