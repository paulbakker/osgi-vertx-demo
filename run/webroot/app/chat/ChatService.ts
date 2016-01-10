import { Injectable } from 'angular2/core'
import {Observable} from 'rxjs/Observable';
import {ChatMessage} from "./Model";


@Injectable()
export class ChatService {
    eb : EventBus_Instance;

    listen() : Observable<ChatMessage> {

        return Observable.create(observer => {
            console.log("connecting...")

            this.eb = new EventBus('http://192.168.0.14:8080/eventbus');

            this.eb.onopen = () => {
                this.eb.registerHandler('chat', (err, message) => {
                    console.log(message.body)
                    observer.next(JSON.parse(message.body));
                });
            }
        });
    }

    send(message : ChatMessage) {
        this.eb.publish('chat',JSON.stringify(message));
    }

}
