import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {CardData} from "./card.component.interface";

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
    pokeName: string = "celebi";

  constructor() { }

  ngOnInit(){
      this.cardName = 'Title ' + this.index;
  }
    get cardPosition() {
        return this.position;
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


