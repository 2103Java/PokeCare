import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import {HttpService, Trainer} from "../httpService/http.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  userNameFormControl: FormControl;
  emailFormControl: FormControl;
  passwordFormControl: FormControl;
  confirmPasswordFormControl: FormControl;
  matcher = new MyErrorStateMatcher();
    private trainer: Trainer;

  constructor(private httpService: HttpService) {
    this.userNameFormControl = new FormControl('', [
      Validators.required
    ])
    this.emailFormControl = new FormControl('', [
      Validators.required,
      Validators.pattern(new RegExp("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
    ]);
    this.passwordFormControl = new FormControl('', [
      Validators.required
    ]);
    this.confirmPasswordFormControl = new FormControl('');
    this.form = new FormGroup({
      userName: this.userNameFormControl,
      email: this.emailFormControl,
      password: this.passwordFormControl,
      confirmPassword: this.confirmPasswordFormControl
    }, {validators: this.checkPasswords});
  }

  ngOnInit(): void {
  }

  checkPasswords(group: FormGroup) {
    const password = group.get('password').value;
    const confirmPassword = group.get('confirmPassword').value;

    return password === confirmPassword ? null : { notSame: true }
  }

  submit() {
    if (this.form.valid) {
      console.log(this.emailFormControl);
        this.httpService.registerRequest(this.userNameFormControl.value, this.emailFormControl.value, this.passwordFormControl.value).subscribe(data =>{
            this.trainer = data;
            console.log(this.trainer);
        });
    }
  }
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control?.invalid && control?.parent?.dirty);
    const invalidParent = !!(control?.parent?.invalid && control?.parent?.dirty);

    return invalidCtrl || invalidParent;
  }
}
