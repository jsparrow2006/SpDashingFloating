import PubSub from './pubSub'
import INativeModules, { TNativeModulesFlat } from './types/INativeModules';

type TSubscriber = (event: string, callback: (data: any) => void) => void;
type TEvent = (event: string, data: any) => void;

interface ISpNative {
    getNativeModules: () => INativeModules;
    getMethodsFromAndroidModules: (modules: string | string[]) => TNativeModulesFlat;
    initSpNativeClient: () => void;
    subscribeEvent: TSubscriber;
    unSubscribeEvent: TSubscriber;
    sendEvent: TEvent;
}

const convertStringToArray = (arrayString: string): string[] => {
    return (arrayString || '')
        .replace('[', '')
        .replace(']', '')
        .split(', ')
}

function JSONWrapper(func, context) {
    return function(...args) {
        let result = func.apply(context, args);
        try {
            return JSON.parse(result);
        } catch (error) {
            return null;
        }

        return result
    };
}

const initSpNativeClient = (): void => {
    const registeredModules = convertStringToArray(window._AndroidSpNative.getRegisteredModules())
    window._AndroidSpNative.nativeModules = {}

    const attachMethods = (androidModule: string) => {
        const methods = Object.keys(window[androidModule] || {})
        methods.forEach((method) => {
            if (!window._AndroidSpNative.nativeModules[androidModule]) {
                window._AndroidSpNative.nativeModules[androidModule] = {}
            }
            window._AndroidSpNative.nativeModules[androidModule][method] = JSONWrapper(window[androidModule][method], window[androidModule])
        })
        console.log(`\u001b[32mAdd ${androidModule} module: \u001b[31m[${methods.join(', ')}]`)
    }

    registeredModules.forEach((androidClass) => {
        attachMethods(androidClass)
    })

    window._AndroidPubSub = new PubSub();
}

const getNativeModules = (): INativeModules => {
    return <INativeModules>window._AndroidSpNative.nativeModules
}

const getMethodsFromAndroidModules = (modules: string[] | string): TNativeModulesFlat => {
    let androidMethods = {};

    if (typeof modules === 'string') {
        androidMethods = {...window._AndroidSpNative.nativeModules[modules]}
    } else {
        modules.forEach((androidClass) => {
            androidMethods = {...androidMethods, ...window._AndroidSpNative.nativeModules[androidClass]}
        })
    }

    return androidMethods as TNativeModulesFlat
}

const subscribeEvent: TSubscriber = (event, callback) => {
    window._AndroidPubSub.subscribe(event, callback)
}

const unSubscribeEvent: TSubscriber = (event, callback) => {
    window._AndroidPubSub.unsubscribe(event, callback)
}

const sendEvent: TEvent = (event, data) => {
    window._AndroidPubSub.publish(event, data)
}

const SpNative: ISpNative = {
    getNativeModules: getNativeModules,
    initSpNativeClient: initSpNativeClient,
    getMethodsFromAndroidModules: getMethodsFromAndroidModules,
    subscribeEvent: subscribeEvent,
    unSubscribeEvent: unSubscribeEvent,
    sendEvent: sendEvent
}

export default SpNative