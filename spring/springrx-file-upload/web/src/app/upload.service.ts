import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  constructor(private client: HttpClient) {}

  getUploadedFiles() {
    return this.client.get(environment.apiUrl);
  }

  upload(data: FileList): Observable<HttpEvent<{}>> {
    const formData = new FormData();

    Array.from(data).forEach(file => {
      formData.append('data', file);
    });

    const request = new HttpRequest('POST', environment.apiUrl, formData, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.client.request(request);
  }
}
