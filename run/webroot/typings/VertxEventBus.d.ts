interface EventBus_Static {
    new(address : string) : EventBus_Instance;
}

interface EventBus_Instance {
    onopen : () => void;
    registerHandler(address : string, callback : (err : any, message : any) => void) : void;
    send(address : string, message : any) : void;
    publish(address : string, message: any) : void;
}

declare var EventBus : EventBus_Static;