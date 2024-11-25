import PubSub from './pubSub'

type TSubscriber = (event: string, callback: (data: any) => void) => void;
type TEvent = (event: string, data: any) => void;

interface ISpNative {
    getAndroidModules: <T extends object[]>(classes: string[] | string) => T[number];
    initSpNativeClient: () => void;
    subscribeEvent: TSubscriber,
    unSubscribeEvent: TSubscriber
    sendEvent: TEvent
}

const convertStringToArray = (arrayString: string): string[] => {
    return arrayString
        .replace('[', '')
        .replace(']', '')
        .split(', ')
}

const initSpNativeClient = () => {
    const registeredModules = window._AndroidSpNative.getRegisteredModules()
    convertStringToArray(registeredModules).forEach((module) => {
        console.log(`REGISTERED_NATIVE_MODULE: ${module}`)
    })
    if (!window.registeredModules) {
        window.registeredModules = convertStringToArray(registeredModules);
    }
    window._AndroidPubSub = new PubSub();
}

const getAndroidModules = <T extends object[]>(modules: string[] | string): T[number] => {
    const androidMethods = {};

    const attachMethods = (androidModule: string) => {
        const methods = Object.keys(window[androidModule] || {})
        methods.forEach((method) => {
            androidMethods[method] = window[androidModule][method].bind(window[androidModule])
        })
    }
    if (typeof modules === 'string') {
        attachMethods(modules)
    } else {
        modules.forEach((androidClass) => {
            attachMethods(androidClass)
        })
    }


    return androidMethods
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
    initSpNativeClient: initSpNativeClient,
    getAndroidModules: getAndroidModules,
    subscribeEvent: subscribeEvent,
    unSubscribeEvent: unSubscribeEvent,
    sendEvent: sendEvent
}

export default SpNative