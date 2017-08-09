import {Injectable} from "@angular/core";

@Injectable()
export class AuthentificationServiceComponent {
  public auth={
    id:"",
    role:"normalUser"
  };
  setAuthObject(id,role){
    this.auth.id=id;
    this.auth.role=role;
    console.log("==>authservice role:",this.auth.role);
  }
  delAuthObject(){
    this.auth.id="";
    this.auth.role="normalUser";
  }
}
