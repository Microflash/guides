import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GreetingService {
  constructor(private client: HttpClient) {}

  getGreeting() {
    return this.client.get('/hello?name=Microflash');
  }
}
