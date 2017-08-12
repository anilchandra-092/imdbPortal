import { Component } from '@angular/core';
import {ContentServiceComponent} from "../content.service.component";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {MoviesRoutingServiceComponent} from "../../movies/movies-routing.service.component";

@Component({
  selector: 'home-comp',
  templateUrl: '../../movies/movies.component.html'
})
export class HomeComponent {

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
      this.loadBasicInformation();
      this.getMoviesCount();
  }
  loadBasicInformation(){

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

  getMoviesCount(){
    this._contentServiceComponent.getCountOfMovies()
      .subscribe(
        moviesLengthObj=>this.mcount=moviesLengthObj,
        errormsg=>this.errorMsg=errormsg,
        ()=>{ this.getCountOfMoviesCallBackFunction(); }
    ) ;
  }

  getCountOfMoviesCallBackFunction(){
    if(this.mcount.count != 0){
      this.errorMsg="";
     this.paginationArray=this._contentServiceComponent.getPaginationArray(this.mcount.count);
     this.loadMovies();
    }
    else{
      this.errorMsg="no Movies in Database";
    }
  }

  loadMovies(){

    if(this.pageid==1){
      this.start=0;
      this.end=9
    }
    else{
      this.start=(this.pageid-1)*9;
      this.end=this.pageid*9;
    }

    this.movies=[];

    this._contentServiceComponent.getMovies(this.start,this.end).
    subscribe(moviesList=>this.movies=moviesList,
        errormsg=>this.errorMsg=errormsg,
      ()=>{console.log(this.movies)}
      );
  }

  onMovieClick(mid){
    this.moviesService.onMovieClick("home",this.pageid,mid);
  }

  onPaginationClick(pid){
    this.moviesService.onPaginationClick("home",pid);
  }
}
