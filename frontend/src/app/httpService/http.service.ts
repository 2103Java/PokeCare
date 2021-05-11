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

    apiUrl = "/";
  constructor(private httpClient: HttpClient) { }

    registerRequest(data: Trainer): Observable<Trainer>{
        this.apiUrl = "/register"
        return this.httpClient.post<Trainer>(this.apiUrl, data); //this should prob. be a post
    }

    login(username, password): Observable<any> {

        this.apiUrl = "/login"
        const body = new HttpParams()
            .set('username', username)
            .set('password', password);

        return this.httpClient.post(this.apiUrl,
            body.toString(),
            {
                headers: new HttpHeaders()
                    .set('Content-Type', 'application/x-www-form-urlencoded')
            }
        );
    }

    newPokemonRequest(data: Pokemon) {
        this.apiUrl = "/pokemon/new";
        return this.httpClient.post<Pokemon>(this.apiUrl, data); //this should also prob. be a post
    }
}
