import {Component, Inject} from '@angular/core';
import {HttpService, Pokemon} from "../httpService/http.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
    selector: 'app-feed',
    templateUrl: './feed.component.html',
    styleUrls: ['./feed.component.css']
})
export class FeedComponent {
    pokemon: Pokemon;

    constructor(private http: HttpService, @Inject(MAT_DIALOG_DATA) public data: object) {
        this.pokemon = data['pokemon'];
    }

    feed() {
        this.http.feedPokemon(this.pokemon).subscribe(done => {
            this.http.trainer.currency -= 100;
            this.pokemon.hunger = Math.max(this.pokemon.hunger - 50, 0);
        });
    }
}