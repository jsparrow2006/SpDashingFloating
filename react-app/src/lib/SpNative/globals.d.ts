import { IPubSub } from './pubSub';

interface IAndroidSpNative {
    getRegisteredModules: () => string
}

declare global {
    interface Window {
        registeredModules: string[];
        _AndroidPubSub: IPubSub
        _AndroidSpNative: IAndroidSpNative
    }
}

export {}