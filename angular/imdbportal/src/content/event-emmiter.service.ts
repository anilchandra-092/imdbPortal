import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

@Injectable()
export class EventEmmiterService{
 private currentLoginStatus=new Subject<any>();

  changeLoginStatus(status) {
    this.currentLoginStatus.next({status :status });
  }

  getLoginStatus() : Observable<any>{
    return this.currentLoginStatus.asObservable();
  }


}


