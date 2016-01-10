System.register(['angular2/core', "./ChatService", "./Model"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, ChatService_1, Model_1;
    var MessageListing;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (ChatService_1_1) {
                ChatService_1 = ChatService_1_1;
            },
            function (Model_1_1) {
                Model_1 = Model_1_1;
            }],
        execute: function() {
            MessageListing = (function () {
                function MessageListing(chatService) {
                    var _this = this;
                    this.chatService = chatService;
                    this.messages = [];
                    this.message = new Model_1.ChatMessage();
                    chatService.listen().subscribe(function (message) {
                        _this.messages.push(message);
                    });
                }
                MessageListing.prototype.sendMessage = function () {
                    this.chatService.send(this.message);
                    this.message = new Model_1.ChatMessage();
                };
                MessageListing = __decorate([
                    core_1.Component({
                        selector: 'messages',
                        providers: [ChatService_1.ChatService]
                    }),
                    core_1.View({
                        styles: [],
                        template: "\n    <h3 class=\"col-md-12\">Messages</h3>\n    <div *ngFor=\"#msg of messages\">{{msg.text}}</div>\n    <input type=\"text\" [(ngModel)]=\"message.text\">  <button (click)=\"sendMessage()\">Send</button>\n    "
                    }), 
                    __metadata('design:paramtypes', [ChatService_1.ChatService])
                ], MessageListing);
                return MessageListing;
            })();
            exports_1("MessageListing", MessageListing);
        }
    }
});
//# sourceMappingURL=ChatComponents.js.map