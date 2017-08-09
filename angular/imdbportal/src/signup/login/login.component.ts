import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserdataServiceComponent} from "../userdata.service.component";
import {Router} from "@angular/router";
import {AuthentificationServiceComponent} from "../../app/authentification.service.component";

@Component({
  selector:'login-comp',
  templateUrl:'./login.component.html'
})
export class LoginComponent{

  userForm:FormGroup;
  userMessage="nothing";
  statusObj;
  isVisible=false;
  errorMsg;

  constructor(
    private _formBuilder:FormBuilder,
    private _userService:UserdataServiceComponent,
    private router:Router,
    private authService:AuthentificationServiceComponent
  ){}
  ngOnInit(){

    this.userForm=this._formBuilder.group({
      uname:[null,[Validators.required]],
      password:[null,[Validators.required]]
    });

  }

  onSubmit() {
    console.log(this.userForm.value);
    this._userService.loginUser(this.userForm.value).
    subscribe(
      subObj=>this.statusObj=subObj,
      errormsg=>this.errorMsg=errormsg,
      ()=>{ this.loginUserCallBack()}
    );
  }

  loginUserCallBack(){
    if(this.statusObj.status=="Fail"){
      this.userMessage=this.statusObj.message;
    }
    else{

      this.userForm.reset();
      this.authService.setAuthObject(this.statusObj.id,this.statusObj.role);

      if(this.statusObj.role=="user"){
        this.router.navigate(["user"]);
      }else{
        this.router.navigate(["admin"]);
      }

    }

    this.isVisible=true;
  }
}
