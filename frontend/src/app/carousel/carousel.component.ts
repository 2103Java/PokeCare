import {
    AfterViewInit,
    Component,
    ElementRef, Input, OnChanges,
    OnInit,
    QueryList, SimpleChanges,
    ViewChild,
    ViewChildren
} from '@angular/core';
import {animate, AnimationBuilder, AnimationPlayer, style} from "@angular/animations";
import {CardComponent} from "../card/card.component";
import {Trainer, Pokemon, HttpService} from "../httpService/http.service";

@Component({
    selector: 'app-carousel',
    templateUrl: './carousel.component.html',
    styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit, AfterViewInit, OnChanges {
    @Input() pokeArrLength: number;
    @ViewChildren('withBuilder') cards: QueryList<CardComponent>;
    @ViewChildren('withBuilder', {read: ElementRef}) elCards: QueryList<ElementRef>;
    @ViewChild('withBuilder', {read: ElementRef}) baseCard: ElementRef;
    @ViewChild('box', {read: ElementRef}) box: ElementRef;
    items = new Array();
    private speed = 400;
    private timing = this.speed + 'ms ease-out';
    private player: AnimationPlayer;
    private parentNode: ElementRef;
    private centerCoords = {x: 0, y: 0};
    private radius = 0;
    private canvasSize = {width: 0, height: 0};
    private cardBaseSize = {width: 0, height: 0};
    private propCanvasCard = 0;
    private cardsArr = [];
    loggedTrainer: Trainer;

    constructor(private animationBuilder: AnimationBuilder, private elementRef: ElementRef, private httpService: HttpService) {
    }

    ngOnInit() {
        this.loggedTrainer = this.httpService.trainer;
    }

    ngAfterViewInit() {
        this.cardsArr = this.cards.toArray();
        console.log(this.cardsArr)
        this.proportions();
        this.cards.changes.subscribe()
    }


    ngOnChanges(changes: SimpleChanges) {

        if (!changes['pokeArrLength'].isFirstChange()) {
            this.ngOnInit();
            this.ngAfterViewInit();
        }
    }

    private proportions() {
        const el = this.elementRef.nativeElement;
        this.canvasSize.width = this.elementRef.nativeElement.parentNode.clientWidth;
        this.canvasSize.height = this.canvasSize.width * 0.3;
        // this.canvasSize.height = this.canvasSize.width * 0.5;
        this.elementRef.nativeElement.parentNode.style.height = this.canvasSize.height + 'px';
        this.radius = this.canvasSize.width * 0.395;
        // this.radius = this.canvasSize.width * 0.20;
        this.centerCoords.x = this.canvasSize.width / 2;
        this.centerCoords.y = this.canvasSize.height / 2;
        this.cardBaseSize.width = this.baseCard.nativeElement.clientWidth;
        this.cardBaseSize.height = this.baseCard.nativeElement.clientHeight;
        this.propCanvasCard = this.canvasSize.width / this.cardBaseSize.width;
        this.createAnimation(0, false);
    }

    public createAnimation(offset: number, repeat: boolean) {
        if (this.player) {
            this.player.destroy();
        }
        this.elCards.forEach((card, key) => {
            let position = offset + this.cardsArr[key].cardPosition;
            switch (position) {
                case -1:
                    position = 5;
                    break;
                case -2:
                    position = 4;
                    break;
                case 6:
                    position = 0;
                    break;
                case 7:
                    position = 1;
                    break;
            }
            this.cardsArr[key].positionChange(position);
            const coords = this.calculateCoords(position);
            card.nativeElement.style.zIndex = Math.round((coords.z + 1) * 100);
            const animation = this.buildAnimation(coords);
            this.player = animation.create(card.nativeElement);
            this.player.play();
            this.player.onDone(() => {
                card.nativeElement.style.transform = this.applyStyle(coords).transform;
            });
        });
        if (repeat === true) {
            setTimeout(() => this.createAnimation(offset, false), this.speed + 20);
        }
    }

    private calculateCoords(index) {
        const x = this.radius * Math.cos((360 * index / 8) * Math.PI / 180) + this.centerCoords.x - (this.cardBaseSize.width * 0.5);
        const y = (this.radius * 0) * Math.sin((360 * index / 8) * Math.PI / 180) + this.centerCoords.y - (this.cardBaseSize.height * 0.5);
        const z = Math.sin((360 * index / 8) * Math.PI / 180);
        return {x, y, z};
    }

    private buildAnimation(coords) {
        if (coords.z < 0) {
            coords.z = 0;
        }
        return this.animationBuilder.build([
            animate(this.timing, style(this.applyStyle(coords)))
        ]);
    }

    private applyStyle(coords) {
        return {transform: `translate(${coords.x}px, ${coords.y}px) scale(${(coords.z + 1) * this.propCanvasCard * 0.2})`};
    }

    public onCardClick(i: number) {
        switch (this.cardsArr[i].position) {
            case 0:
                this.createAnimation(1, true);
                break;
            case 1:
                this.createAnimation(1, false);
                break;
            case 3:
                this.createAnimation(-1, false);
                break;
            case 4:
                this.createAnimation(-1, true);
                break;
        }
    }

    onResize($event: any) {
        this.proportions();
    }

}
