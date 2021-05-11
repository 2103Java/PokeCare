import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-train',
  templateUrl: './train.component.html',
  styleUrls: ['./train.component.css']
})
export class TrainComponent implements OnInit {

  training1 : number = 0;
  training2 : number = 0;
  training3 : number = 0;
  hover1 : number = 0;
  hoverDisplay1 : boolean = false;
  hover2 : number = 0;
  hoverDisplay2 : boolean = false;
  hover3 : number = 0;
  hoverDisplay3 : boolean = false;
  pokeStamina : number = 75;
  pokeHunger : number = 20;
  totalStamina : number = 0;
  totalHunger : number = 0;
  @ViewChild('total') private total: ElementRef;
  @ViewChild('applyButton') private applyButton: any;

  constructor(public dialogRef: MatDialogRef<TrainComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  calculateTotal() {
    setTimeout(() => {
      this.totalStamina = this.training1*20 + this.training2*28 + this.training3*33;
      this.totalHunger = this.training1*15 + this.training2*25 + this.training3*30;
      if (this.pokeStamina - this.totalStamina < 0 || this.pokeHunger + this.totalHunger > 100) {
        this.total.nativeElement.style.color = 'red';
        this.applyButton.disabled = true;
      }
      else {
        this.total.nativeElement.style.color = 'black';
        this.applyButton.disabled = false;
      }
    }, 10);
  }

  reset() {
    this.training1=0;
    this.training2=0;
    this.training3=0;
    this.calculateTotal();
  }

  apply() {
    this.pokeStamina-=this.totalStamina;
    this.pokeHunger+=this.totalHunger;
    this.reset();
  }

}
