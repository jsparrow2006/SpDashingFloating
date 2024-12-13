export interface INativePromise {
    resolve: (id: number, data: any) => void
    reject: (id: number, error: any) => void
    add: () => {id: number, promise: Promise<any>}
}

class NativePromise implements INativePromise{
    private promises: {};
    constructor() {
        this.promises = {};
    }

    private tryConvertToJSON(data: any): any {
        let prepareData = data
        try {
            prepareData =  JSON.parse(data);
        } catch (error) {
            console.warn('errorConvertJSON')
        }

        return prepareData
    }

    public resolve(id, data) {
        if (this.promises[id]) {
        this.promises[id].resolve(this.tryConvertToJSON(data))
        }
        delete this.promises[id]
    }

    public reject(id, error) {
        if (this.promises[id]) {
            this.promises[id].reject(this.tryConvertToJSON(error))
        }
        delete this.promises[id]
    }

    public add() {
        const id = Date.now()
        const preparePromise = { promise: Promise, resolve: null, reject: null }
        preparePromise.promise = new Promise((resolve, reject) => {
            preparePromise.resolve = resolve;
            preparePromise.reject = reject;
        })
        this.promises = {
            ...this.promises,
            [id]: preparePromise
        }

        return {id, promise: this.promises[id].promise}
    }
}

export default NativePromise