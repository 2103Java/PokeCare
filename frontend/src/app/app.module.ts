import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';


import {AppComponent} from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import {ReactiveFormsModule} from "@angular/forms";
import {FormsModule} from "@angular/forms";
import {SidenavComponent} from "./sidenav/sidenav.component";
import {MaterialModule} from "./material/material.module";
import { CardComponent } from './card/card.component';
import { CarouselComponent } from './carousel/carousel.component';

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        SidenavComponent,
        CardComponent,
        CarouselComponent,

    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        MaterialModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
