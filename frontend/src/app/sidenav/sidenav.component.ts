import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
import {Router} from "@angular/router";
import {MatDialog} from '@angular/material/dialog';
import {UploadImageComponent} from '../upload-image/upload-image.component';

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
    opened = false;

    @ViewChild('profileImg') private profileImg: ElementRef;

    constructor(private httpService: HttpService, private router: Router, private dialog: MatDialog) {
    }

    ngOnInit(): void {
    }

    logout() {
        this.httpService.logout().subscribe(data => {
            this.router.navigateByUrl("/login");
        });
    }

    addPokemon() {
        this.httpService.newPokemonRequest().subscribe(pokemon => {
            this.httpService.trainer.pokemon.push(pokemon);
        });
    }

    uploadImage() {
        this.dialog.open(UploadImageComponent, {
            data: {img: this.profileImg.nativeElement}
        });
    }

    get trainer(): Trainer {
        return this.httpService.trainer;
    }

    fallbackImage(errorEvent: ErrorEvent) {
        errorEvent.target['src'] = "https://w7.pngwing.com/pngs/1021/743/png-transparent-ash-ketchum-and-pikachu-ash-ketchum-pokxe9mon-x-and-y-pokxe9mon-sun-and-moon-pikachu-misty-pokemon-ash-free-chibi-boy-fictional-character-thumbnail.png";
    }
}