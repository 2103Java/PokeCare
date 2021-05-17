import { Component, OnInit } from '@angular/core';
import {HttpService} from "../httpService/http.service";
import { FormGroup, FormBuilder,  FormControl} from "@angular/forms";

@Component({
  selector: 'app-f-add',
  templateUrl: './f-add.component.html',
  styleUrls: ['./f-add.component.css']
})
export class FAddComponent implements OnInit {


  constructor(private http: HttpService) {
  }

  ngOnInit(): void {
  }

  addFriend(username: string){
      this.http.addFriend(username).subscribe(data =>{
          if(data == 1) alert("Friend Request Sent!!");
          else alert("Failed to Send Friend Request");
      });
  }

}
