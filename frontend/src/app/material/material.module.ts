import { NgModule } from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatFormFieldModule} from "@angular/material/form-field";

const MaterialComponents =[
    MatToolbarModule,
    MatSidenavModule,
    MatCheckboxModule,
    MatFormFieldModule
]

@NgModule({

  imports: [
      MaterialComponents
  ],
    exports: [
        MaterialComponents
    ]
})
export class MaterialModule { }
