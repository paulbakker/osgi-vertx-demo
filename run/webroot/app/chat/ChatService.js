System.register(['angular2/core', 'rxjs/Observable'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, Observable_1;
    var ChatService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (Observable_1_1) {
                Observable_1 = Observable_1_1;
            }],
        execute: function() {
            ChatService = (function () {
                function ChatService() {
                }
                ChatService.prototype.listen = function () {
                    var _this = this;
                    return Observable_1.Observable.create(function (observer) {
                        console.log("connecting...");
                        _this.eb = new EventBus('http://192.168.0.14:8080/eventbus');
                        _this.eb.onopen = function () {
                            _this.eb.registerHandler('chat', function (err, message) {
                                console.log(message.body);
                                observer.next(JSON.parse(message.body));
                            });
                        };
                    });
                };
                ChatService.prototype.send = function (message) {
                    this.eb.publish('chat', JSON.stringify(message));
                };
                ChatService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [])
                ], ChatService);
                return ChatService;
            })();
            exports_1("ChatService", ChatService);
        }
    }
});
//# sourceMappingURL=ChatService.js.map