import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {CardData} from "./card.component.interface";
import {ifStmt} from "@angular/compiler/src/output/output_ast";

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

    cardName: string;
    //this is the name that defines what 3D model is returned.
    pokeName: string = "sdf";
    errorHandled: boolean = false;

    constructor() {
    }

    ngOnInit() {
        this.cardName = 'Title ' + this.index;
    }

    get cardPosition() {
        return this.position;
    }

    //if the 3D model for the pokemon does not exist; we change the source to a static img.
    errHandler(error) {
        console.log(error);

        if (this.errorHandled) {
            error.target.src = "https://www.pkparaiso.com/imagenes/xy/sprites/animados/" + this.pokeName + ".gif"
        } else {
            error.target.src = "https://www.pkparaiso.com/imagenes/ultra_sol_ultra_luna/sprites/animados-sinbordes-gigante/" + this.pokeName + ".gif"
            this.errorHandled = true;
        }
        //error.target.src = "https://www.pkparaiso.com/imagenes/xy/sprites/animados/" + this.pokeName + ".gif"
        //If the above fails I need to use the below url. but I don't know how to tell typescript the above also failed
        // "https://www.pkparaiso.com/imagenes/ultra_sol_ultra_luna/sprites/animados-sinbordes-gigante/" + this.pokeName + ".gif"
        // error.target.src = "https://www.pkparaiso.com/imagenes/xy/sprites/animados/" + this.pokeName + ".gif";
        // error.target.style = "height:auto; width: 40%; top: 3rem"
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


}


