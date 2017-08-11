import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ContentServiceComponent} from "../../content/content.service.component";

@Component({
  selector:'edit-movie-comp',
  templateUrl:'./edit-movie.component.html'
})

export class EditMovieComponent{

  movieDetails;
  errorMsg;
  pid;
  mid;
  searchKey;
  searchOption;
  categeory;
  isEditable;

  title;
  imagePath;
  shortDescription;
  avgRating;
  language;
  director;
  year;
  duration;
  detailDescription;
  starCast;
  weightage;
  comedy;
  romantic;
  scifi;
  action;



  constructor(
    private _formBuilder:FormBuilder,
    private contentService:ContentServiceComponent,
    private router:Router,
    private activatedRoute:ActivatedRoute
  ){}

  ngOnInit(){
    this.isEditable=false;
    this.loadBsicInformation();
    this.loadMovieDetails();

  }
  loadBsicInformation(){

    this.activatedRoute.params
      .subscribe((params:Params)=>{
        this.pid=parseInt(params['pid']);
        this.mid=parseInt(params['mid']);
        this.categeory=params['categeory'];
        if(this.categeory=="search"){
          this.searchKey=params['searchKey'];
          this.searchOption=params['searchOption'];
        }
      });

  }


  loadMovieDetails(){
    this.contentService.getMovieToAdmin(this.mid)
      .subscribe(
        data=>this.movieDetails=data,
        error=>{},
        ()=> {
          this.setMovieFormValues();
        }
      );
  }

  setMovieFormValues(){

    this.title = this.movieDetails.title;
    this.imagePath =this.movieDetails.imagePath;
    this.shortDescription =this.movieDetails.shortDescription;
    this.avgRating = this.movieDetails.avgRating;
    this.language = this.movieDetails.language;
    this.director=this.movieDetails.director;
    this.year = this.movieDetails.year;
    this.duration = this.movieDetails.duration;
    this.detailDescription = this.movieDetails.detailDescription;
    this.starCast = this.movieDetails.starCast;
    this.weightage=this.movieDetails.weightage;
    this.comedy = this.movieDetails.comedy;
    this.romantic = this.movieDetails.romantic;
    this.scifi = this.movieDetails.scifi;
    this.action =this.movieDetails.action;

  }

  onSubmit(movieFormValue){
    console.log(movieFormValue);
    movieFormValue.avgRating = this.movieDetails.avgRating;
    movieFormValue.weightage = this.movieDetails.weightage;
    this.contentService.updateMovie(this.mid,movieFormValue).subscribe(
      data=>{},
      error=>{},
      ()=>{
        this.loadMovieDetails();
        this.onEditBack();
      }
    );
  }

  goBack(){
    if(this.categeory!="search"){
      this.router.navigate(["/admin",this.pid,{parent:"admin",categeory:this.categeory}]);
    }
    else{
      this.goBackToSearch();
    }
  }

  goBackToSearch(){
    this.router.navigate(["/search",
      {
        "pageid":this.pid,
        "searchKey":this.searchKey,
        "searchOption":this.searchOption
      }
    ]);
  }

  onEdit(){
    this.isEditable=true;
  }
  onEditBack(){
    this.setMovieFormValues();
    this.isEditable=false;
  }
}
