import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from './httpService/http.service';

@Injectable({
  providedIn: 'root'
})
export class Auth2Guard implements CanActivate {
  
  constructor(private httpService: HttpService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      this.httpService.checkSessionImpl().then(data => {
        if (data)
          this.router.navigate(["./care"]);
      });
      return true;
  }
  
}
