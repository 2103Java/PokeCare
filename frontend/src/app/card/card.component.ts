import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TrainComponent} from '../train/train.component';
import {MatDialog} from '@angular/material/dialog';
import {Pokemon} from "../httpService/http.service";
import {ReturnComponent} from "../return/return.component";
import {FeedComponent} from "../feed/feed.component";

@Component({
    selector: 'app-card',
    templateUrl: './card.component.html',
    styleUrls: ['./card.component.css'],
    animations: [
        trigger('cardFlip', [
            state('default', style({
                transform: 'none'
            })),
            state('flipped', style({
                transform: 'rotateY(180deg)'
            })),
            transition('default => flipped', [
                animate('400ms')
            ]),
            transition('flipped => default', [
                animate('200ms')
            ])
        ])
    ]
})

export class CardComponent implements OnInit {
    @Input() index: string;
    @Output() currentPosition: EventEmitter<number> = new EventEmitter<number>();
    @Input() position: number;
    @Input() poke: Pokemon;

    cardName: string;
    //this is the name that defines what 3D model is returned.
    pokeName: string;
    errorHandled: number = 0;
    displayName: string;

    constructor(private dialog: MatDialog) {
    }

    ngOnInit() {
        this.cardName = 'Title ' + this.index;
        if (this.poke.data.name=="mr-rime"||this.poke.data.name=="mr-mime"){
            this.pokeName = "mr._"+this.poke.data.name.slice(3)
        }else{
            this.pokeName = this.poke.data.name;
        }
        this.displayName = this.editDisplayName(this.pokeName)
    }

    get cardPosition() {
        return this.position;
    }

    //if the 3D model for the pokemon does not exist; we change the source to a static img.
    errHandler(error) {
        if (this.errorHandled == 1) {
            error.target.src = "https://www.pkparaiso.com/imagenes/xy/sprites/animados/" + this.pokeName + ".gif"
            error.target.style = "height:50%; width: auto; top: 4rem"
            this.errorHandled++;
        } else if (this.errorHandled == 0) {
            error.target.src = "https://www.pkparaiso.com/imagenes/ultra_sol_ultra_luna/sprites/animados-sinbordes-gigante/" + this.pokeName + ".gif"
            this.errorHandled++;
        } else if (this.errorHandled == 2) {
            error.target.src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6at7RwZOM_yVpsUZWimO0o75bYbKAE1DaTg&usqp=CAU";
            this.errorHandled = 0;
        }
    }

    positionChange(position) {
        this.position = position;
        let positionsToMove = 0;

        switch (position) {
            case 1:
                positionsToMove = 1;
                break;
            case 4:
                positionsToMove = -2;
                break;
            case 0:
                positionsToMove = 2;
                break;
            case 3:
                positionsToMove = -1;
                break;
        }

        if (positionsToMove !== 0) {
            this.currentPosition.emit(positionsToMove);
        }
    }

    openTraining() {
        const dialogRef = this.dialog.open(TrainComponent, {
            data: {pokeName: this.pokeName}
        });
    }
    editDisplayName(name){
        if( name == "mr._mime" || name == "mr._rime") {
            name = "Mr. " + name.charAt(4).toUpperCase() + name.slice(5);
        } else {
            name = name.charAt(0).toUpperCase() + name.slice(1)
        }
        return name;
    }

    feedPokemon() {
        this.dialog.open(FeedComponent, {
            data: {pokemon: this.poke}
        });
    }

    returnPokemon() {
        this.dialog.open(ReturnComponent, {
            data: {pokemon: this.poke}
        });
    }
}