import {Component} from '@angular/core';
import {HttpService} from "../httpService/http.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-f-add',
    templateUrl: './f-add.component.html'
})
export class FAddComponent {

    constructor(private http: HttpService) {
    }

    addFriend(username: string) {
        this.http.addFriend(username).subscribe(data => {
            if (data == 1) {
                Swal.fire("Friend Request Sent!", "Please wait for them to accept your request.", "success");
            } else {
                Swal.fire("Oops!", "An error occurred", "error");
            }
        });
    }
}