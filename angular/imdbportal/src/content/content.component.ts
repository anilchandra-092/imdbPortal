import {Component} from '@angular/core';
import {AuthentificationServiceComponent} from "../app/authentification.service.component";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Http} from "@angular/http";
import {Router} from "@angular/router";

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
    private router:Router
  ){}

  ngOnInit(){
    this.searchForm=this._formBuilder.group({
      searchKey:[],
      searchOption:["movie"]
    });

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

}