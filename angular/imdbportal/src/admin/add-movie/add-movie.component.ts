import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ContentServiceComponent} from "../../content/content.service.component";
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
  selector:'add-movie-comp',
  templateUrl:'./add-movie.component.html'
})

export class AddMovieComponent{
  movieForm:FormGroup;
  statusObj;
  isVisible=false;
  errorMsg;
  userMessage;
  pid;
  categeory;

  constructor(
    private _formBuilder:FormBuilder,
    private contentService:ContentServiceComponent,
    private router:Router,
    private activatedRoute:ActivatedRoute
  ){}

  ngOnInit(){
    this.movieForm=this._formBuilder.group({
      title:[null,[Validators.required]],
      imagePath:[],
      shortDescription:[null,[Validators.required]],
      avgRating:[null],
      language:[null,[Validators.required]],
      director:[null,[Validators.required]],
      year:[],
      duration:[],
      detailDescription:[],
      starCast:[],
        comedy:[],
        romantic:[],
        scifi:[],
        action:[]
    });

    this.activatedRoute.params
      .subscribe((params:Params)=>{
        this.pid=parseInt(params['pid']);
        this.categeory=params['categeory'];
      });
  }

  onSubmit(){

    if(!this.movieForm.value.comedy){
      this.movieForm.value.comedy=false;
    }
    if(!this.movieForm.value.action){
      this.movieForm.value.action=false;
    }
    if(!this.movieForm.value.scifi){
      this.movieForm.value.scifi=false;
    }

    if(!this.movieForm.value.romantic){
      this.movieForm.value.romantic=false;
    }
    console.log(this.movieForm.value);
    this.contentService.addMovie(this.movieForm.value)
      .subscribe(
        status=>this.statusObj=status,
        error=>this.errorMsg=error,
        ()=>{
          if(this.statusObj.status=="Fail"){
            this.userMessage="movie is not added to the database";

          }
          else{
            this.userMessage="Movie added Successfully";
            this.movieForm.reset();
          }
          this.isVisible=true;
        }
      );
  }
  goBack(){
    var path="../admin/"+this.pid;
    this.router.navigate([path,{categeory:this.categeory}]);
  }
}
