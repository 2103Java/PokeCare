import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {SidenavComponent} from "./sidenav/sidenav.component";

const routes: Routes = [
    {
        path: '',
        redirectTo: 'care',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'care',
        component: SidenavComponent
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes),
        CommonModule,
        BrowserModule
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}