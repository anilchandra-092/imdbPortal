import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserdataServiceComponent} from "./userdata.service.component";

@Component({
  selector: 'sign-comp',
  templateUrl: './signup.component.html'
})
export class SignupComponent {

  userForm:FormGroup;
  userMessage="nothing";
  statusObj;
  isVisible=false;
  errorMsg;
  loginStatus=false;

  constructor(private _formBuilder:FormBuilder,private _userService:UserdataServiceComponent){}

  ngOnInit(){ this.buildUserForm(); }

  buildUserForm(){

    this.userForm=this._formBuilder.group({
      uname:[null,[Validators.required,Validators.minLength(4),Validators.pattern("^[a-zA-Z0-9_]{4,50}$")]],
      password:[null,[Validators.pattern("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,16}$")]],
      confpass: [null,[Validators.pattern("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,16}$")]],
      email:[],
      role:[null,[Validators.required]]
    });

  }

  onSubmit(){
    if(this.userForm.value.confpass == this.userForm.value.password){

      delete this.userForm.value['confpass'];

       this._userService.addUser(this.userForm.value).
       subscribe(
         subObj=>this.statusObj=subObj,
         errormsg=>this.errorMsg=errormsg,
         ()=>{ this.addUserCallBackFunction()}
       );
    }
    else{
      this.userMessage="password and conform password must be same";
      this.isVisible=true;
    }

  }

  addUserCallBackFunction(){

    if(this.statusObj.status=="Fail"){
      this.userMessage=this.statusObj.message;

    }
    else {
      this.userMessage = "u registred successfully now login";
      this.loginStatus = true;
      this.userForm.reset();
    }
    this.isVisible = true;

  }
}
