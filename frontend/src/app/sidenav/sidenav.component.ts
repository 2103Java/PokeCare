import {Component, OnInit} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
import {Router} from "@angular/router";
import { MatDialog } from '@angular/material/dialog';
import { UploadImageComponent } from '../upload-image/upload-image.component';

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
    opened = false;

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
        const dialogRef = this.dialog.open(UploadImageComponent);
    }

    get trainer(): Trainer {
        return this.httpService.trainer;
    }
}