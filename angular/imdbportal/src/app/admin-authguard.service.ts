import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

@Injectable()
export class AdminAuthguardService implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
  var con=true;
    if(con==true){
      return true;
    }
    else{
      this.router.navigate(['/login']);
      return false;
    }

  }
}
