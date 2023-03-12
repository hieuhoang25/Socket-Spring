import { Component } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  id!: string;
  greetings: string[] = [];
  showConversation: boolean = false;
  ws: any;
  name!: string;
  disabled!: boolean;

  constructor() {}

  connect() {
    //connect to stomp where stomp endpoint is exposed
    //let ws = new SockJS(http://localhost:8080/greeting);
    let socket = new WebSocket('ws://localhost:8080/greeting');
    
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect(
      {},
      (frame: any) => {
        that.ws.subscribe('/errors', function (message: any) {
          alert('Error ' + message.body);
        });
        that.ws.subscribe(`/topic/server`, function (message: any) {
          console.log(message);
          that.showGreeting(message.body);
        });
        that.disabled = true;
      },
      function (error: any) {
        alert('STOMP error ' + error);
      }
    );
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    this.setConnected(false);
    console.log('Disconnected');
  }

  // sendName() {
  //   var data = JSON.stringify({
  //     'name' : this.name
  //   })
  //   this.ws.send(`/app/message/${this.id}`, {}, data);
  // }

  showGreeting(message: any) {
    this.showConversation = true;
    this.greetings.push(message);
  }

  setConnected(connected: any) {
    this.disabled = connected;
    this.showConversation = connected;
    this.greetings = [];
  }
}
