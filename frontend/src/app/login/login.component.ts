import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {HttpService} from '../httpService/http.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    form: FormGroup;
    emailFormControl: FormControl;
    passwordFormControl: FormControl;

    constructor(private httpService: HttpService, private router: Router) {
        this.emailFormControl = new FormControl('', [
            Validators.required
        ]);
        this.passwordFormControl = new FormControl('', [
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
            this.httpService.login(this.emailFormControl.value, this.passwordFormControl.value).subscribe(trainer => {
                if (trainer)
                    this.router.navigateByUrl("/care");
            }, e => {
                if (e.status == 401)
                    Swal.fire("Oopss", "Wrong Username or Password", "error");
            });
        }
    }

}