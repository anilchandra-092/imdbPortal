import { Component } from '@angular/core';
import {EventEmmiterService} from "../content/event-emmiter.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private eventEmiterService:EventEmmiterService){
    this.eventEmiterService.changeLoginStatus(false);
  }
}
