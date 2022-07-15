import { Component, AfterContentChecked } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { UploadService } from './upload.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements AfterContentChecked {
  selected: FileList;
  public label: string = 'Select a file or two...';
  progress: { percentage: number } = { percentage: 0 };
  public uploadedFiles: Array<string>;

  constructor(private uploadService: UploadService) {}

  ngAfterContentChecked() {
    this.updateFileList();
  }

  get status() {
    return this.progress.percentage <= 25 ? 'is-danger' : this.progress.percentage <= 50 ? 'is-warning' : this.progress.percentage <= 75 ? 'is-info' : 'is-success';
  }

  selectFile(event: any) {
    this.selected = event.target.files;
    this.label = this.selected.length > 1 ? this.selected.length + ' files selected' : '1 file selected';
  }

  upload() {
    this.progress.percentage = 0;

    this.uploadService.upload(this.selected).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File successfully uploaded!');
      }
    })

    this.selected = undefined;
  }

  updateFileList() {
    this.uploadService.getUploadedFiles().subscribe(res => {
      this.uploadedFiles = [...res.toString().split(',').map(name => name.replace(/^.*[\\\/]/, ''))];
    });
  }
}
