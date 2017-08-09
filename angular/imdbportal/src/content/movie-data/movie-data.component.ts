import {Component} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ContentServiceComponent} from "../content.service.component";
import {AuthentificationServiceComponent} from "../../app/authentification.service.component";

@Component({
  selector:'movie-comp',
  templateUrl:'./movie-data.component.html'
})
export class MovieDataComponent{

  public mid;
  public pid;
  public movieDetails={};
  public responseObj={};
  public errorMsg;
  public parentComp;
  public userRate=-1;
  public rateArray=[];
  public searchKey;
  public searchOption;
  public userRole;

  constructor(
    private activatedRoute:ActivatedRoute,
    private router:Router,
    private contentService:ContentServiceComponent,
    private authService:AuthentificationServiceComponent
  ){}

  ngOnInit(){ this.loadBasicInformation();}

  loadBasicInformation(){

    this.userRole=this.authService.auth.role;

    this.activatedRoute.params
      .subscribe((params:Params)=>{
        this.mid=parseInt(params['mid']);
        this.pid=parseInt(params['pid']);
        this.parentComp=params['parent'];
        if(this.parentComp=="search"){
          this.searchKey=params['searchKey'];
          this.searchOption=params['searchOption'];
        }
      });

    this.loadMovieDetails();
    if(this.parentComp=="user"||this.parentComp=="search"){
      this.getUserRate();
      this.buildrateArray();
    }
  }

  loadMovieDetails(){

    this.contentService.getMovie(this.mid)
    .subscribe(
      response=>this.movieDetails=response,
      errormsg=>this.errorMsg=errormsg,
      ()=>{console.log("call back function=>",this.movieDetails)}
    );

  }

  getUserRate(){
    this.contentService.getUserRate(this.mid,parseInt(this.authService.auth.id))
      .subscribe(
        response=>this.responseObj=response,
        errormsg=>this.errorMsg=errormsg,
        ()=>{
          this.userRate=this.responseObj["rate"];
          this.buildrateArray();
        }
      );
  }

  buildrateArray(){

    for(let i=0;i<10;i++){
      if(i<this.userRate){
        this.rateArray[i]=true;
      }
      else{
        this.rateArray[i]=false;
      }
    }
  }

  onStarClick(i){
    this.userRate=i+1;
    this.contentService.setMovieRating(this.mid,parseInt(this.authService.auth.id),i+1)
      .subscribe(
        data => {},
        error => { console.log(JSON.stringify(error.json()));},
        ()=>{
          this.loadMovieDetails();
          this.buildrateArray();
        }
      );
  }

  goBack(){
    if(this.parentComp!=="search"){
      let path="../"+this.parentComp+"/"+this.pid;
      this.router.navigate([path]);
    }
    else{
      this.router.navigate(["/search",{
        "pageid":this.pid,
          "searchKey":this.searchKey,
          "searchOption":this.searchOption
      }]);
    }
  }
}
