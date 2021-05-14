import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {HttpService} from './httpService/http.service';
import {catchError, map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private httpService: HttpService, private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.httpService.loadTrainer().pipe(
            map(trainer => {
                if (state.url.startsWith("/login") || state.url.startsWith("/register")) {
                    if (trainer) {
                        this.router.navigateByUrl("/care");
                        return false;
                    }
                }

                return true;
            }), catchError(err => {
                if (state.url.startsWith("/login") || state.url.startsWith("/register")) {
                    return of(true);
                }
                return this.router.navigateByUrl("/login");
            })
        );
    }
}