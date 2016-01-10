import {Component} from 'angular2/core';
import {MessageListing} from "./chat/ChatComponents";

@Component({
    selector: 'my-app',
    template: `
    <h1>Vertx OSGi demo</h1>
    <messages></messages>
    `,
    directives: [MessageListing],
    styles: [`

  `],
})
export class AppComponent {}


