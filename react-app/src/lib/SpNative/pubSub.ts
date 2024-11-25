export interface IPubSub {
    subscribe: (event: string, callback: (data: any) => void) => void
    unsubscribe: (event: string, callback: (data: any) => void) => void
    publish: (event: string, data: any) => void
}

class PubSub implements IPubSub{
    private subscribers: {};
    constructor() {
        this.subscribers = {};
    }

    private logEvent(event: string, data: any) {
        console.log(`\u001b[31m[${event}] `, data)
    }

    subscribe(event: string, callback: (data: any) => void) {
        if (!this.subscribers[event]) {
            this.subscribers[event] = [];
        }
        this.subscribers[event].push(callback);
    }

    publish(event: string, data: any) {
        this.logEvent(event, data)
        if (!this.subscribers[event]) return;

        this.subscribers[event].forEach(callback => callback(data));
    }

    unsubscribe(event: string, callback: (data: any) => void) {
        if (!this.subscribers[event]) return;

        this.subscribers[event] = this.subscribers[event].filter(
            subscriber => subscriber !== callback
        );
    }
}

export default PubSub