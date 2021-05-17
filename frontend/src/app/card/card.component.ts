import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TrainComponent} from '../train/train.component';
import {MatDialog} from '@angular/material/dialog';
import {HttpService, Pokemon} from "../httpService/http.service";
import {ReturnComponent} from "../return/return.component";
import {FeedComponent} from "../feed/feed.component";

const XP_LEVELS: number[] = [15, 52, 122, 237, 406, 637, 942, 1326, 1800, 2369, 3041, 3822, 4719, 5737, 6881, 8155, 9564, 11111, 12800, 14632, 16610, 18737,
    21012, 23437, 26012, 28737, 31610, 34632, 37800, 41111, 44564, 48155, 51881, 55737, 59719, 63822, 68041, 72369, 76800, 81326, 85942, 90637, 95406, 100237,
    105122, 110052, 115015, 120001, 125000, 131324, 137795, 144410, 151165, 158056, 165079, 172229, 179503, 186894, 194400, 202013, 209728, 217540, 225443,
    233431, 241496, 249633, 257834, 267406, 276458, 286328, 296358, 305767, 316074, 326531, 336255, 346965, 357812, 367807, 378880, 390077, 400293, 411686,
    423190, 433572, 445239, 457001, 467489, 479378, 491346, 501878, 513934, 526049, 536557, 548720, 560922, 571333, 583539, 591882, 600000];

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
export class CardComponent implements OnInit, OnDestroy {
    @Input() index: string;
    @Output() currentPosition: EventEmitter<number> = new EventEmitter<number>();
    @Input() position: number;
    @Input() poke: Pokemon;

    cardName: string;
    //this is the name that defines what 3D model is returned.
    pokeName: string;
    errorHandled: number = 0;
    displayName: string;
    pokeImgSrc: string = "";
    level: number;
    xpAsPercent: number;
    intervalId: number;

    constructor(private dialog: MatDialog, private http: HttpService) {
    }

    ngOnInit() {
        this.cardName = 'Title ' + this.index;
        if (this.poke.data.name == "mr-rime" || this.poke.data.name == "mr-mime") {
            this.pokeName = "mr._" + this.poke.data.name.slice(3)
        } else {
            this.pokeName = this.poke.data.name;
        }
        this.displayName = this.editDisplayName(this.pokeName)

        this.intervalId = setInterval(() => {
            if (this.poke.fatigue < 1) {
                return;
            }

            if (--this.poke.fatigue % 5 == 0) {
                this.http.updateFatigue(this.poke);
            }
        }, 2000);

        this.poke.updateLevel = () => {
            for (let i = 0; i < XP_LEVELS.length; i++) {
                if (this.poke.experience < XP_LEVELS[i]) {
                    this.level = i + 1;
                    this.xpAsPercent = (this.poke.experience / XP_LEVELS[i])*100;
                    break;
                }
            }
        };
        this.poke.updateLevel();
    }

    ngOnDestroy() {
        clearInterval(this.intervalId);
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
        this.dialog.open(TrainComponent, {
            data: {pokemon: this.poke},
            panelClass: 'trainModal'
        });
    }

    editDisplayName(name) {
        if (name == "mr._mime" || name == "mr._rime") {
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

    play() {
        this.http.playPokemon(this.poke).subscribe(hap => {
            this.poke.happiness = Math.min(this.poke.happiness + hap, 100);
            this.poke.fatigue = Math.min(this.poke.fatigue + 20, 100);
            this.poke.hunger = Math.min(this.poke.hunger + 10, 100);
        });
    }

    returnPokemon() {
        this.dialog.open(ReturnComponent, {
            data: {pokemon: this.poke}
        });
    }

    cantFeed() {
        return this.poke.hunger === 0;
    }

    cantPlay() {
        return this.poke.fatigue > 0 || this.poke.hunger === 100;
    }

    cantTrain() {
        return this.poke.fatigue > 0 || this.poke.hunger === 100 || this.poke.happiness < 20;
    }
}