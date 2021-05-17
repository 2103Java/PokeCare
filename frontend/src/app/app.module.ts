import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {MAT_DIALOG_DEFAULT_OPTIONS} from "@angular/material/dialog";
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule} from "@angular/forms";
import {FormsModule} from "@angular/forms";
import {SidenavComponent} from "./sidenav/sidenav.component";
import {MaterialModule} from "./material/material.module";
import {CardComponent} from './card/card.component';
import {CarouselComponent} from './carousel/carousel.component';
import {AppRoutingModule} from './app.routing';
import {LoginComponent} from './login/login.component';
import {CommonModule} from '@angular/common';
import {PortalModule} from '@angular/cdk/portal';
import {FlexLayoutModule} from '@angular/flex-layout';
import {RegisterComponent} from './register/register.component';
import {TrainComponent} from './train/train.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpService} from "./httpService/http.service";
import {HttpClientModule} from "@angular/common/http";
import { ReturnComponent } from './return/return.component';
import { UploadImageComponent } from './upload-image/upload-image.component';
import {FriendsComponent} from './friends/friends.component';
import { FRequestsComponent } from './f-requests/f-requests.component';
import { FAddComponent } from './f-add/f-add.component';

@NgModule({
    declarations: [
        AppComponent,
        SidenavComponent,
        CardComponent,
        CarouselComponent,
        LoginComponent,
        RegisterComponent,
        TrainComponent,
        ReturnComponent,
        FriendsComponent,
        FRequestsComponent,
        FAddComponent,
        UploadImageComponent
    ],
    imports: [
        CommonModule,
        BrowserModule,
        AppRoutingModule,
        RouterModule,
        BrowserAnimationsModule,
        PortalModule,
        FlexLayoutModule,
        ReactiveFormsModule,
        NgbModule,
        HttpClientModule,
        MaterialModule,
        FormsModule
    ],
    exports: [
        PortalModule,
        BrowserModule,
        ReactiveFormsModule,
        MaterialModule,
        FormsModule,
    ],
    providers: [
        HttpService,
        {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}