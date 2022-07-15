import { Component } from '@angular/core';
import { GreetingService } from './greeting.service';

@Component({
  selector: 'app-root',
  template: `
    <main>{{ message }}</main>
  `,
  styles: [
    `
      main {
        margin: calc(2rem + 5vw);
        font-size: calc(2rem + 4vw);
      }
    `
  ]
})
export class AppComponent {
  message: string = 'Hello, stranger!';

  constructor(private greetingService: GreetingService) {}

  ngOnInit(): void {
    this.getMessage();
  }

  getMessage() {
    this.greetingService.getGreeting()
      .subscribe(response => (this.message = response['message']));
  }
}
