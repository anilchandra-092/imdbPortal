import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ContentServiceComponent} from "../../content/content.service.component";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Http, RequestOptions, Response, Headers} from "@angular/http";

@Component({
  selector: 'add-movie-comp',
  templateUrl: './add-movie.component.html'
})

export class AddMovieComponent {
  movieForm: FormGroup;
  statusObj;
  isVisible = false;
  errorMsg;
  userMessage;
  pid;
  categeory;
  file: File;
  fileType;

  constructor(private _formBuilder: FormBuilder,
              private contentService: ContentServiceComponent,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private _http: Http) {
  }

  ngOnInit() {
    this.movieForm = this._formBuilder.group({
      title: [null, [Validators.required]],
      imageFile:[],
      shortDescription: [null, [Validators.required]],
      language: [null, [Validators.required]],
      director: [null, [Validators.required]],
      year: [null, [Validators.required]],
      duration: [null, [Validators.required]],
      detailDescription: [null],
      starCast: [],
      comedy: [],
      romantic: [],
      scifi: [],
      action: []
    });

    this.activatedRoute.params
      .subscribe((params: Params) => {
        this.pid = parseInt(params['pid']);
        this.categeory = params['categeory'];
      });
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.file = fileList[0];
      var splitVals = this.file.name.split(".");
      this.fileType = splitVals[splitVals.length - 1];
    }
  }

  onSubmit() {

    if (!this.movieForm.value.comedy) {
      this.movieForm.value.comedy = false;
    }
    if (!this.movieForm.value.action) {
      this.movieForm.value.action = false;
    }
    if (!this.movieForm.value.scifi) {
      this.movieForm.value.scifi = false;
    }

    if (!this.movieForm.value.romantic) {
      this.movieForm.value.romantic = false;
    }
    this.movieForm.value.imageFile = this.file;
    console.log(this.movieForm.value);


    let formData: FormData = new FormData();
    formData.append('imageFile', this.file);
    formData.append('fileType', this.fileType);
    formData.append('title', this.movieForm.value.title);
    formData.append("shortDescription", this.movieForm.value.shortDescription);
    formData.append("language", this.movieForm.value.language);
    formData.append("director", this.movieForm.value.director);
    formData.append("year", this.movieForm.value.year);
    formData.append("duration", this.movieForm.value.duration);
    formData.append("detailDescription", this.movieForm.value.detailDescription);
    formData.append("starCast", this.movieForm.value.starCast);
    formData.append("comedy", this.movieForm.value.comedy);
    formData.append("romantic", this.movieForm.value.romantic);
    formData.append("scifi", this.movieForm.value.scifi);
    formData.append("action", this.movieForm.value.action);


    let headers = new Headers();
     //headers.append('Content-Type', 'false');
    // headers.append('processData', 'false');
    console.log("==>1", formData);

    this._http.post("http://192.168.32.27:8080/imdbportal/file/uploadMovieDetails", formData, {headers: headers,withCredentials: true})
      .map((response: Response) => response.json())
      .subscribe(
        status => this.statusObj = status,
        error => {
          this.errorMsg = error;
          console.log(error);
        },
        () => {
          this.callBackFunction()
        }
      );
    console.log("==>2", formData);
  }

  callBackFunction() {
    if (this.statusObj.status == "Fail") {
      this.userMessage = "movie is not added to the database";

    }
    else {
      this.userMessage = "Movie added Successfully";
      this.movieForm.reset();
    }
    this.isVisible = true;
  }

  goBack() {
    var path = "../admin/" + this.pid;
    this.router.navigate([path, {categeory: this.categeory}]);
  }
}
