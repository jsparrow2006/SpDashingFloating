import { IPubSub } from './pubSub';
import INativeModules from './types/INativeModules';

interface IAndroidSpNative {
    nativeModules: Partial<INativeModules>;
    getRegisteredModules: () => string;
    Promises: any
}

declare global {
    interface Window {
        registeredModules: string[];
        _AndroidPubSub: IPubSub
        _AndroidSpNative: IAndroidSpNative
    }
}

export {}