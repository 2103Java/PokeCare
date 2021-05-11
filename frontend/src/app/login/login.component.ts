import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpService, Trainer } from '../httpService/http.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  form: FormGroup;
  emailFormControl: FormControl;
  passwordFormControl: FormControl;
  trainer: Trainer;

  constructor(private httpService: HttpService ) {
    this.emailFormControl = new FormControl('', [
      Validators.required
    ]);
    this.passwordFormControl= new FormControl('', [
      Validators.required
    ]);
    this.form = new FormGroup({
      email: this.emailFormControl,
      password: this.passwordFormControl
    });
  }

  ngOnInit() {
  }

  submit() {
    if (this.form.valid) {
      console.log(this.emailFormControl);
      this.httpService.login(this.emailFormControl.value, this.passwordFormControl.value).subscribe(data =>{
        this.trainer = data;
        console.log(this.trainer);
      });
    }
  }

}
