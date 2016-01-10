import {Component, View} from 'angular2/core';
import { HTTP_PROVIDERS } from 'angular2/http'
import {Observable} from 'rxjs/Observable';
import * as moment from "moment";
import {ChatService} from "./ChatService";
import {ChatMessage} from "./Model";
import {NgForm}    from 'angular2/common';

@Component({
    selector: 'messages',
    providers: [ChatService]
})
@View({
    styles: [],
    template: `
    <h3 class="col-md-12">Messages</h3>
    <div *ngFor="#msg of messages">{{msg.text}}</div>
    <input type="text" [(ngModel)]="message.text">  <button (click)="sendMessage()">Send</button>
    `
})
export class MessageListing {

    messages = [];
    message = new ChatMessage();

    constructor(private chatService : ChatService) {
       chatService.listen().subscribe(message => {
           this.messages.push(message)
       })
    }

    sendMessage() {
        this.chatService.send(this.message)
        this.message = new ChatMessage();
    }

}
