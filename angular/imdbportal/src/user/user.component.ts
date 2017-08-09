import {Component} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ContentServiceComponent} from "../content/content.service.component";
import {MoviesRoutingServiceComponent} from "../movies/movies-routing.service.component";

@Component({
  selector:'user-comp',
  templateUrl:'../movies/movies.component.html'
})

export class UserComponent{

  movies=[];
  paginationArray=[];
  errorMsg;
  mcount;
  pageid:number;
  start:number=0;
  end:number=0;
  constructor(
    private _contentServiceComponent:ContentServiceComponent,
    private router:Router,
    private activatedRoute:ActivatedRoute,
    private moviesService:MoviesRoutingServiceComponent
  ){}

  ngOnInit(){

    this.getMoviesCount();
    this.loadPageId();
  }

  getMoviesCount(){

    this._contentServiceComponent.getCountOfMovies()
      .subscribe(
        moviesLengthObj=>this.mcount=moviesLengthObj,
        errormsg=>this.errorMsg=errormsg+"ngoninit",
        ()=>{
          this.paginationArray=this._contentServiceComponent.getPaginationArray(this.mcount.count);
          this.loadMovies();
        }
      );
  }

  loadPageId(){

    this.activatedRoute.params.subscribe((params:Params)=>{
      console.log(params['pageid']);
      this.pageid=parseInt(params['pageid']);
      if(this.pageid >= 1){
        this.loadMovies();
      }
    });

    if(this.activatedRoute.snapshot.data['pageid']==1){
      this.pageid=this.activatedRoute.snapshot.data['pageid'];
    }
  }

  loadMovies(){

    this.movies=[];
    if(this.pageid==1){
      this.start=0;
      this.end=9
    }
    else{
      this.start=(this.pageid-1)*9;
      this.end=this.pageid*9-1;
    }

    this._contentServiceComponent.getMovies(this.start,this.end).
    subscribe(moviesList=>this.movies=moviesList,
      errormsg=>this.errorMsg=errormsg,
      ()=>{console.log(this.movies)}
    );
  }

  onMovieClick(mid){
    this.moviesService.onMovieClick("user",this.pageid,mid);
  }

  onPaginationClick(pid){
    this.moviesService.onPaginationClick("user",pid);
  }

}
