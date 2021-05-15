import {Component, Inject} from '@angular/core';
import {HttpService, Pokemon} from "../httpService/http.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
    selector: 'app-return',
    templateUrl: './return.component.html',
    styleUrls: ['./return.component.css']
})
export class ReturnComponent {
    pokemon: Pokemon;

    constructor(private http: HttpService, @Inject(MAT_DIALOG_DATA) public data: object) {
        this.pokemon = data['pokemon'];
    }

    return() {
        this.http.returnPokemon(this.pokemon).subscribe(money => {
            const index = this.http.trainer.pokemon.indexOf(this.pokemon);

            if (index != -1) {
                this.http.trainer.currency += money;
                this.http.trainer.pokemon.splice(index, 1);
            }
        });
    }
}