import {Component, Inject} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
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

    fallbackImage(errorEvent: ErrorEvent) {
        errorEvent.target['src'] = "https://w7.pngwing.com/pngs/1021/743/png-transparent-ash-ketchum-and-pikachu-ash-ketchum-pokxe9mon-x-and-y-pokxe9mon-sun-and-moon-pikachu-misty-pokemon-ash-free-chibi-boy-fictional-character-thumbnail.png";
    }
}