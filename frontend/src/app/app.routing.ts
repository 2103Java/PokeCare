import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import { AuthGuard } from './auth.guard';
import { Auth2Guard } from './auth2.guard';
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
        component: LoginComponent,
        canActivate: [Auth2Guard]
    },
    {
        path: 'register',
        component: RegisterComponent,
        canActivate: [Auth2Guard]
    },
    {
        path: 'care',
        component: SidenavComponent,
        canActivate: [AuthGuard]
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