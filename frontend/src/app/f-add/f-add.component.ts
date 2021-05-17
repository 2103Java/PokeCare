import { Component, OnInit } from '@angular/core';
import {HttpService} from "../httpService/http.service";
import { FormGroup, FormBuilder,  FormControl} from "@angular/forms";

@Component({
  selector: 'app-f-add',
  templateUrl: './f-add.component.html',
  styleUrls: ['./f-add.component.css']
})
export class FAddComponent implements OnInit {
    addFriendForm = this.formBuilder.group({
        username:""
    });

  constructor(private http: HttpService,private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
  }

  addFriend(){

      this.http.addFriend(this.addFriendForm.value).subscribe(data =>{
          alert(data);
      });
  }

}
