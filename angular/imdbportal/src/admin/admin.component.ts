import { Component } from '@angular/core';
import {MoviesRoutingServiceComponent} from "../movies/movies-routing.service.component";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ContentServiceComponent} from "../content/content.service.component";

@Component({
  selector: 'admin-comp',
  templateUrl: './admin.component.html',
  styleUrls:['./admin.component.css']
})
export class AdminComponent {

  movies=[];
  paginationArray=[];
  errorMsg;
  mcount;
  pageid:number;
  start:number=0;
  end:number=0;
  categeory;

  constructor(
    private _contentServiceComponent:ContentServiceComponent,
    private router:Router,
    private activatedRoute:ActivatedRoute,
    private moviesService:MoviesRoutingServiceComponent
  ){}

  ngOnInit(){
    this.loadBsicInformation();
    this.buildPaginationArray();
  }

  loadBsicInformation(){

    this.activatedRoute.params.subscribe((params:Params)=>{
      console.log(params['pageid']);
      this.pageid=parseInt(params['pageid']);
      this.categeory=params['categeory'];
      if(this.pageid >= 1){
        this.loadMovies();
      }
    });

    if(this.activatedRoute.snapshot.data['categeory']=="all"){
      this.categeory=this.activatedRoute.snapshot.data['categeory'];
    }

    if(this.activatedRoute.snapshot.data['pageid']==1){
      this.pageid=this.activatedRoute.snapshot.data['pageid'];
    }
  }

  buildPaginationArray(){

    this._contentServiceComponent.getCountOfMoviesToAdmin(this.categeory).subscribe(
      moviesLengthObj=>this.mcount=moviesLengthObj,
      errormsg=>this.errorMsg=errormsg+"ngoninit",
      ()=>{ this.PaginationArrayCallBackFunction(); }
    );

  }

  PaginationArrayCallBackFunction(){

    this.paginationArray=this._contentServiceComponent.getPaginationArray(this.mcount.count);
    this.loadMovies();
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

    this._contentServiceComponent.getMoviesToAdmin(this.start,this.end,this.categeory).
    subscribe(moviesList=>this.movies=moviesList,
      errormsg=>this.errorMsg=errormsg,
      ()=>{console.log(this.movies)}
    );
  }
  onMovieClick(movieId){
   console.log("admin==>movie id: ",movieId);
   this.router.navigate(["/admin/editMovie",{pid:this.pageid,mid:movieId,categeory:this.categeory}]);
  }
  onPaginationClick(pid){
      this.moviesService.onAdminPaginationClick("admin",pid,this.categeory);
  }
  onAddMovieClick(){
    this.router.navigate(["/admin/addMovie",{pid:this.pageid,categeory:this.categeory}]);
  }
  onAllMovieClick(){ this.setCategeory("all");}

  onComedyMovieClick(){ this.setCategeory("comedy"); }

  onRomanticMovieClick(){ this.setCategeory("romantic"); }

  onScifiMovieClick(){ this.setCategeory("scifi"); }

  onActionMovieClick(){ this.setCategeory("action"); }

  setCategeory(categeory){
    console.log("on",categeory,"Movie Click");
    this.categeory=categeory;
    this. buildPaginationArray();
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
