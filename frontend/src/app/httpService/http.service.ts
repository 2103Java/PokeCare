import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';


export interface Pokemon{
    id: number;
    poke_number: number;
    trainer_id: number;
    happiness: number;
    hunger: number;
    fatigue: number;
    xp: number;
}

export interface Trainer{
    id: number;
    username: string;
    pokeList: Array<Pokemon>;
    currency: number;
}



@Injectable({
  providedIn: 'root'
})
export class HttpService {

    apiUrl = "/api/trainer/";
  constructor(private httpClient: HttpClient) { }

    registerRequest(username, email, password): Observable<Trainer>{

        const body = new HttpParams()
            .set('username', username)
            .set('email', email)
            .set('password', password);

        return this.httpClient.post<Trainer>(this.apiUrl+'register',
            body.toString(),
            {
                headers: new HttpHeaders()
                    .set('Content-Type', 'application/x-www-form-urlencoded')
            }
        );
    }

    login(username, password): Observable<Trainer> {

        const body = new HttpParams()
            .set('username', username)
            .set('password', password);

        return this.httpClient.post<Trainer>(this.apiUrl+'login',
            body.toString(),
            {
                headers: new HttpHeaders()
                    .set('Content-Type', 'application/x-www-form-urlencoded')
            }
        );
    }

//     newPokemonRequest(data: Pokemon) {
//         this.apiUrl = "/pokemon/new";
//         return this.httpClient.post<Pokemon>(this.apiUrl, data); //this should also prob. be a post
//     }
 }
