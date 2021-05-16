import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import Swal from 'sweetalert2';
import { HttpService } from '../httpService/http.service';

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent implements OnInit {

  constructor(private httpService: HttpService) { }

  ngOnInit(): void {
  }

  @ViewChild('fileInput') fileInput: ElementRef;
  fileAttr : any;
  imagePath: any;
  message: string;
  
  readURL(event) {
    let files: FileList = event.target.files;
    if (files.length === 0)
      return;

    let mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = "Only images are supported.";
      return;
    }

    let reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.fileAttr = reader.result;
    }
  }

  upload() {
    this.httpService.upload(this.fileAttr).subscribe(data => {
      if (data.status == 200)
        Swal.fire("Success", "Image Uploaded Successfully", "success");
      else Swal.fire("Oopss", "Something Went Wrong", "error");
    })
  }

}


