import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpService, Trainer} from "../httpService/http.service";
import {Router} from "@angular/router";
import {MatDialog} from '@angular/material/dialog';
import {UploadImageComponent} from '../upload-image/upload-image.component';
import {ReturnComponent} from "../return/return.component";
import {FriendsComponent} from "../friends/friends.component";
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

    @ViewChild('profileImg') private profileImg: ElementRef;

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

    uploadImage() {
        this.dialog.open(UploadImageComponent, {
            data: {img: this.profileImg.nativeElement}
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

    fallbackImage(errorEvent: ErrorEvent) {
        errorEvent.target['src'] = "https://w7.pngwing.com/pngs/1021/743/png-transparent-ash-ketchum-and-pikachu-ash-ketchum-pokxe9mon-x-and-y-pokxe9mon-sun-and-moon-pikachu-misty-pokemon-ash-free-chibi-boy-fictional-character-thumbnail.png";
    }
}