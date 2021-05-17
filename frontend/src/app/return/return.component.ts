import {Component, Inject} from '@angular/core';
import {HttpService, Pokemon} from "../httpService/http.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import Swal from "sweetalert2";
// import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'app-return',
    templateUrl: './return.component.html',
    styleUrls: ['./return.component.css']
})
export class ReturnComponent {
    pokemon: Pokemon;

    constructor( private http: HttpService, @Inject(MAT_DIALOG_DATA) public data: object) {
        this.pokemon = data['pokemon'];
    }

    return() {
        this.http.returnPokemon(this.pokemon).subscribe(money => {
            const index = this.http.trainer.pokemon.indexOf(this.pokemon);

            if (index != -1) {
                this.http.trainer.currency += money;
                this.http.trainer.pokemon.splice(index, 1);
                Swal.fire("Pokemon Returned", "You returned " + this.pokemon.data.name + " to its trainer and received ₽ " + money, "success");
            }
        });
    }
}