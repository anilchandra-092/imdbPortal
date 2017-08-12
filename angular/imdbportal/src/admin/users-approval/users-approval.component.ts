import {Component} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ContentServiceComponent} from "../../content/content.service.component";
import {userInfo} from "os";


@Component({
  selector:'uesrs-approval-comp',
  templateUrl:'./users-approval.component.html'
})

export class UsersApprovalComponent{
  pid;
  categeory;
  users;
  isNewUsersExists;


  constructor(
    private router:Router,
    private activatedRoute:ActivatedRoute,
    private contentService:ContentServiceComponent
  ){}

  ngOnInit(){
    this.activatedRoute.params
      .subscribe((params:Params)=>{
        this.pid=parseInt(params['pid']);
        this.categeory=params['categeory'];
      });
    this.loadNewUsers();
  }
  loadNewUsers(){
    this.contentService.getNewUsers().subscribe(
      data=>this.users=data,
      error=>{console.log(error);},
      ()=>{
        if(this.users.length>=1){
          this.isNewUsersExists=true;
        }
        else{
          this.isNewUsersExists=false;
        }
      }
    );
  }

  onAcceptClick(id){
   this.changeStatus(id,"approve");
  }
  onRemoveClick(id){
    this.changeStatus(id,"reject");
  }

  changeStatus(id,status){
    console.log("called in userapproval>>");
    this.contentService.changeStatus(id,status).subscribe(
      data=>{console.log("userApproved",data)},
      error=>{console.log(error);},
      ()=>{this.loadNewUsers();}
    );
  }

  goBack(){
    var path="../admin/"+this.pid;
    this.router.navigate([path,{categeory:this.categeory}]);
  }
}
