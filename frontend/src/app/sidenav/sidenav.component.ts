import {Component, OnInit} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
    opened = false;

    constructor(private httpService: HttpService, private router: Router) {
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

    get trainer(): Trainer {
        return this.httpService.trainer;
    }

}