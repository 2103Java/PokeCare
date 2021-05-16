import {Component, Inject, OnInit} from '@angular/core';
import {HttpService, Pokemon, Trainer} from "../httpService/http.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.css']
})
export class FriendsComponent {
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

}