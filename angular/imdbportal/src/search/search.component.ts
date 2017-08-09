import {Component} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ContentServiceComponent} from "../content/content.service.component";
import {AuthentificationServiceComponent} from "../app/authentification.service.component";

@Component({
  selector:'search-comp',
  templateUrl:'./search.component.html'
})
export class SearchComponent{


  searchKey;
  searchOption;
  movies=[];
  paginationArray=[];
  errorMsg;
  mcount;
  pageid:number;
  start:number=0;
  end:number=0;
  userRole;

  constructor(
    private activatedRoute:ActivatedRoute,
    private _contentServiceComponent:ContentServiceComponent,
    private router:Router,
    private authService:AuthentificationServiceComponent
  ){}

  ngOnInit(){
    this.userRole=this.authService.auth.role;
    this.loadBasicInformation();
  }

  loadBasicInformation(){
    this.activatedRoute.params.subscribe((params:Params)=>{
      this.pageid=parseInt(params['pageid']);
      this.searchKey=params['searchKey'];
      this.searchOption=params['searchOption'];
      if(this.pageid >= 1){
        this.loadMovies();
      }
      this.buildPaginationArray();

    });
  }

  buildPaginationArray(){
    this._contentServiceComponent.getCountOfMoviesInSearch(this.searchKey,this.searchOption)
      .subscribe(
        moviesLengthObj=>this.mcount=moviesLengthObj,
        errormsg=>this.errorMsg=errormsg+"ngoninit",
        ()=>{
          this.paginationArray=this._contentServiceComponent.getPaginationArray(this.mcount.count);
          this.loadMovies();
        }
    );
  }

  loadMovies(){
    if(this.pageid==1){
      this.start=0;
      this.end=9
    }
    else{
      this.start=(this.pageid-1)*9;
      this.end=this.pageid*9-1;
    }
    this.movies=[];

    this._contentServiceComponent.getMoviesInSearch(this.start,this.end,this.searchKey,this.searchOption).
    subscribe(moviesList=>this.movies=moviesList,
      errormsg=>this.errorMsg=errormsg,
      ()=>{console.log(this.movies)}
    );
  }
  onPaginationClick(pid){
    this.router.navigate(["/search",
      {
        "pageid":pid,
        "searchKey":this.searchKey,
        "searchOption":this.searchOption
      }
    ]);
  }

  onMovieClick(mid){
    if(this.userRole!="admin"){
      this.router.navigate(["/search",mid,
        {
          mid:mid,
          pid:this.pageid,
          parent:"search",
          searchKey:this.searchKey,
          searchOption:this.searchOption
        }
      ]);
    }else{
      this.router.navigate(["/admin/editMovie",
        {
          mid:mid,
          pid:this.pageid,
          categeory:"search",
          searchKey:this.searchKey,
          searchOption:this.searchOption
        }
      ]);
    }
  }

  onTrahClick(mid){
    this._contentServiceComponent.deleteMovie(mid)
      .subscribe(
        data=>{},
        error=>{},
        ()=>{this.buildPaginationArray();}
      );
  }
}
