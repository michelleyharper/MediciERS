import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // usig string interpolation we can display the value 
    //of the variable in the template. 
  title = 'hello-angular';
  message = 'Hi Everyone!';

}
