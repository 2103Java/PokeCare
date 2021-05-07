import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  emailFormControl: FormControl;
  passwordFormControl: FormControl;

  constructor() {
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
    }
  }

}
