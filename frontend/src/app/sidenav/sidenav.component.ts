import {Component, OnInit} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
import {Router} from "@angular/router";
import {ReturnComponent} from "../return/return.component";
import {FriendsComponent} from "../friends/friends.component";
import {MatDialog} from "@angular/material/dialog";
import {FRequestsComponent} from "../f-requests/f-requests.component";
import {FAddComponent} from "../f-add/f-add.component";

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent {
    opened = false;
    myFriends: Array<Trainer>;

    constructor(private httpService: HttpService, private router: Router, private dialog: MatDialog) {
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

    showFriends() {
        this.httpService.getMyFriends().subscribe(friends => {
            this.dialog.open(FriendsComponent, {
                data: {friends: friends}
            });
        });
    }

    pendingRequests() {
        this.httpService.getPendingReqs().subscribe(friends => {
            this.dialog.open(FRequestsComponent, {
                data: {friends: friends}
            });
        });
    }

    addFriend() {
        this.dialog.open(FAddComponent);
    }
}
