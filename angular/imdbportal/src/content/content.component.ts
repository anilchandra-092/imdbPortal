import {Component} from '@angular/core';
import {AuthentificationServiceComponent} from "../app/authentification.service.component";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {EventEmmiterService} from "./event-emmiter.service";
import {ContentServiceComponent} from "./content.service.component";

@Component({
  selector: 'content-root',
  templateUrl: './content.component.html',
  styleUrls:['./content.component.css']
})
export class ContentComponent {
  isUserDisabled=false;
  isAdminDisabled=false;
  isLogoutDisabled=true;
  searchForm:FormGroup;

  constructor(
    private authService:AuthentificationServiceComponent,
    private _formBuilder:FormBuilder,
    private _http:Http,
    private router:Router,
    private contentService:ContentServiceComponent,
    private eventEmmitterService:EventEmmiterService
  ){}

  ngOnInit(){
    this.searchForm=this._formBuilder.group({
      searchKey:[null,[Validators.required]],
      searchOption:["movie"]
    });
    this.eventEmmitterService.getLoginStatus().subscribe(Login=>this.isLogoutDisabled=!Login.status);
  }

  onSearchSubmit(){

    this.router.navigate(["/search",
        {
          "pageid":1,
          "searchKey":this.searchForm.value.searchKey,
          "searchOption":this.searchForm.value.searchOption
        }
    ]);
  }

  onLogOutClick(){
    this.eventEmmitterService.changeLoginStatus(false);
    this.authService.delAuthObject();
    this.contentService.logout().subscribe(data=>{console.log("isLoggedOut:",data)});
    this.router.navigate(["/home"]);
  }

}
