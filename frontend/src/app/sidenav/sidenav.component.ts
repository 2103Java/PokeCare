import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpService, Pokemon, Trainer} from "../httpService/http.service";

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

    trainer: Trainer;

    constructor(private httpService: HttpService) {
    }

    ngOnInit(): void {
        this.httpService.fetchTrainer().subscribe(data => {
            this.trainer = data;
            console.log(this.trainer);
        })
    }

    opened = false;

    logout(){
        this.httpService.logout().subscribe(data=>{
            console.log(data);
        });
    }

    addPokemon(){
        this.httpService.newPokemonRequest().subscribe(pokemon=>{
            this.trainer.pokemon.push(pokemon);
        });
    }
}
