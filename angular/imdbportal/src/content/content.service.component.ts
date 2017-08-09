import {Injectable} from "@angular/core";
import{Http,Response} from "@angular/http";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from "rxjs/Observable";

@Injectable()
export class ContentServiceComponent {
    baseUrl:string='http://localhost:8080/imdbportal';
  _url1:string =this.baseUrl+'/movies';

  constructor(private _http: Http) {}

  getRequest(url){
    return  this._http.get(url).map((response: Response) => response.json()).catch(this._errorHandler);
  }

  _errorHandler(error: Response) {
    return Observable.throw(error || "json url not found");
  }

  deleteMovie(mid){
    var url=this._url1+"/"+mid;
    event.preventDefault();
    return this._http.delete(url).map((response: Response) => response.json()).catch(this._errorHandler);
  }

  getMovies(start,end) {
   var url=this._url1+"?start="+start+"&end="+end;
   console.log(url);
    return this.getRequest(url);
  }

  getMovieToAdmin(movieId){
    let url=this._url1+"/admin/"+movieId;
    console.log(url);
    return this.getRequest(url);

  }
  getMoviesToAdmin(start,end,categeory) {
    var url=this._url1+"/admin?start="+start+"&end="+end+"&categeory="+categeory;
    return this.getRequest(url);
  }
  getCountOfMovies(){
    var url=this._url1+"/count";
    return this.getRequest(url);
  }

  getCountOfMoviesToAdmin(categeory){
    var url=this._url1+"/count/admin?categeory="+categeory;
    return this.getRequest(url);
  }
  getMoviesInSearch(start,end,searchKey,searchOption){
    let url=this.baseUrl+"/search?start="+start+"&end="+end;
    var body={
      "searchKey":searchKey,
      "searchOption":searchOption
    };

    return this._http.post(url,body).map((response: Response) =>response.json()).catch(this._errorHandler);
  }

  getCountOfMoviesInSearch(searchKey,searchOption){
    var url=this.baseUrl+"/search/count";
    var body={
      "searchKey":searchKey,
      "searchOption":searchOption
    };
    event.preventDefault();
    return this._http.post(url,body).map((response: Response) =>response.json()).catch(this._errorHandler);
  }

  getMovie(mid){
    var url=this._url1+"/"+mid;
    return  this.getRequest(url);
  }


  getUserRate(movieId,userId){
    var url=this._url1+"/"+movieId+"/"+userId;
    console.log("userRateUrl==>",url);
    return this.getRequest(url);
  }

  setMovieRating(movieId,userId,rate){
    var url=this._url1+"/setRate?movieid="+movieId+"&userid="+userId+"&rate="+rate;
    var body={};
    event.preventDefault();
    return this._http.post(url,body).map((response: Response) => {}).catch(this._errorHandler);
  }

  addMovie(movieDetails){
    event.preventDefault();
    var url=this._url1+"/addMovie";
    return this._http.post(url,movieDetails)
      .map((response: Response) => response.json())
      .catch(this._errorHandler);
  }
  updateMovie(movieId,movieData){
    var url=this._url1+"/updateMovie?movieId="+movieId;
    return this._http.put(url,movieData).map((response: Response) => response.json()).catch(this._errorHandler);
  }
  getPaginationArray(CountOfMovies){

    var paginationArray=[];
    var x=parseInt(CountOfMovies);
    var noOfPages:number = parseInt((x/9)+"");
    if(x%9!=0){
      noOfPages=noOfPages+1;
    }

    while (paginationArray.length < noOfPages)
      paginationArray.push( paginationArray.length + 1 );

    return paginationArray;

  }


}
