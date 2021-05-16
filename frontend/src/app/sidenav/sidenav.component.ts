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
    myFriends: Array<Trainer>;

    constructor(private httpService: HttpService, private router: Router) {
    }

    ngOnInit(): void {
        this.httpService.getMyFriends.subscribe(data =>{
            this.myFriends = data;
        });
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

    toggleFriends(e) {
        e.preventDefault;
    if(document.getElementById("toggleFriends").style.display == "none"){
        document.getElementById("toggleFriends").style.display = "block";
    }
    else{
        document.getElementById("toggleFriends").style.display = "none";
    }

    }

    myFriendsStats(friend: Trainer) {
        let message = "Username: " + friend.username + "\n";
        message += "Caring for " + friend.pokemon.length + " Pokémon\n";
        message += "Currency: " + friend.currency;
        alert(message);
    }
}