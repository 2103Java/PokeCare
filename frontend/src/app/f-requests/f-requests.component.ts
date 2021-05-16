import {Component, Inject, OnInit} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-f-requests',
  templateUrl: './f-requests.component.html',
  styleUrls: ['./f-requests.component.css']
})
export class FRequestsComponent implements OnInit {
    friends: Array<Trainer>;

    constructor(private http: HttpService, @Inject(MAT_DIALOG_DATA) public data: object) {
        this.friends = data['friends'];
    }

    myFriendsStats(friend: Trainer) {
        let message = "Username: " + friend.username + "\n";
        message += "Caring for " + friend.pokemon.length + " Pokémon\n";
        message += "Currency: " + friend.currency;
        alert(message);
    }

    ngOnInit(): void {
    }

    acceptFriend(friend: Trainer) {
    this.http.acceptFriend(friend.id).subscribe(data =>{
        console.log(data);
        let index =  this.friends.findIndex(x => x.username==friend.username);
        this.friends[index] = undefined;
    });
    }

    rejectFriend(friend: Trainer) {
    this.http.rejectFriend(friend.id).subscribe(data =>{
        console.log(data);
        let index =  this.friends.findIndex(x => x.username==friend.username);
        this.friends[index] = undefined;
    });

    }

}
