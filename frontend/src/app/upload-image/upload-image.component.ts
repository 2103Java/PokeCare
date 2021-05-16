import {Component, ElementRef, Inject, ViewChild} from '@angular/core';
import Swal from 'sweetalert2';
import {HttpService} from '../httpService/http.service';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
    selector: 'app-upload-image',
    templateUrl: './upload-image.component.html',
    styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent {
    @ViewChild('fileInput') fileInput: ElementRef;
    files: FileList
    fileAttr: any;
    message: string;

    constructor(private httpService: HttpService, @Inject(MAT_DIALOG_DATA) public data: object) {
    }

    readURL(event) {
        this.files = event.target.files;

        if (this.files.length === 0) {
            return;
        }

        let mimeType = this.files[0].type;

        if (mimeType.match(/image\/*/) == null) {
            this.message = "Only images are supported.";
            return;
        }

        let reader = new FileReader();

        reader.readAsDataURL(this.files[0]);
        reader.onload = (_event) => {
            this.fileAttr = reader.result;
        }
    }

    upload() {
        this.httpService.upload(this.files[0]).subscribe(response => {
            if (response.status == 200) {
                this.data['img']['src'] = this.fileAttr;
                Swal.fire("Success", "Image Uploaded Successfully", "success");
            } else {
                Swal.fire("Oopss", "Something Went Wrong", "error");
            }
        })
    }
}