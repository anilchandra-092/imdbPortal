import {Injectable} from "@angular/core";
import{Http,Response} from "@angular/http";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserdataServiceComponent {

  _addingUserUrl:string ='http://localhost:8080/imdbportal/users/adduser';
  _loginUrl:string ='http://localhost:8080/imdbportal/login';

  constructor(private _http: Http) {}

  addUser(userData) {
    event.preventDefault();
    return this._http.post(this._addingUserUrl,userData)
      .map((response: Response) => response.json())
      .catch(this._errorHandler);
  }

  loginUser(userData){
    event.preventDefault();
    return this._http.post(this._loginUrl,userData,{withCredentials: true})
      .map((response: Response) => response.json())
      .catch(this._errorHandler);
  }

  _errorHandler(error: Response) {
    return Observable.throw(error || "json url not found");
  }
}
