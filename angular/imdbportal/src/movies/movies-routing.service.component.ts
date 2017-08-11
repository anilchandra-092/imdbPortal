import {Injectable} from "@angular/core";
import {ContentServiceComponent} from "../content/content.service.component";
import {ActivatedRoute, Router} from "@angular/router";

@Injectable()
export class MoviesRoutingServiceComponent{

  movies=[];
  paginationArray=[];
  errorMsg;


  constructor(
    private _contentServiceComponent:ContentServiceComponent,
    private router:Router,
    private activatedRoute:ActivatedRoute

  ){}

  onMovieClick(currentComp,pageid,movieid){
    var path="/"+currentComp+"/"+pageid;
    this.router.navigate([path,movieid,{pid:pageid,parent:currentComp}]);
  }

  onPaginationClick(currentComp,pageid){
    this.router.navigate(["/"+currentComp,pageid,{parent:currentComp}]);
  }
  onAdminPaginationClick(currentComp,pageid,categeory){
    this.router.navigate(["/"+currentComp,pageid,{parent:currentComp,categeory:categeory}]);
  }
}
