import {Component, ElementRef, Inject, ViewChild} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {HttpService, Pokemon} from "../httpService/http.service";

@Component({
    selector: 'app-train',
    templateUrl: './train.component.html',
    styleUrls: ['./train.component.css']
})
export class TrainComponent {
    pokemon: Pokemon;
    @ViewChild('total') private total: ElementRef;
    @ViewChild('applyButton') private applyButton: any;

    selected: number;

    @ViewChild('battle') private battle: ElementRef;
    @ViewChild('practice') private practice: ElementRef;
    @ViewChild('obedience') private obedience: ElementRef;

    constructor(private http: HttpService, public dialogRef: MatDialogRef<TrainComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
        this.pokemon = data['pokemon'];
    }

    apply() {
        if (this.selected) {
            this.http.trainPokemon(this.pokemon, this.selected).subscribe(done => {
                switch (this.selected) {
                    case 1:
                        this.pokemon.hunger = 100;
                        this.pokemon.fatigue = 100;
                        this.pokemon.experience += 150;
                        break;
                    case 2:
                        this.pokemon.hunger = 50;
                        this.pokemon.fatigue = 50;
                        this.pokemon.experience += 60;
                        break;
                    case 3:
                        this.pokemon.hunger = 10;
                        this.pokemon.fatigue = 10;
                        this.pokemon.experience += 30;
                        break;
                }

                this.pokemon.updateLevel();
            });
        }
    }

    selectTrain(method: number) {
        this.selected = method;

        switch (method) {
            case 1:
                this.practice.nativeElement.checked = false;
                this.obedience.nativeElement.checked = false;
                break;
            case 2:
                this.battle.nativeElement.checked = false;
                this.obedience.nativeElement.checked = false;
                break;
            case 3:
                this.battle.nativeElement.checked = false;
                this.practice.nativeElement.checked = false;
                break;
        }
    }

    get afterFatigue() {
        return 0;
    }

    get afterHunger() {
        return 0;
    }
}