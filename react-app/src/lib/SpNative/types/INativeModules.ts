import IAppProvider from './IAppProvider';
import IFloatingWindowProvider from './IFloatingWindowProvider';

interface INativeModules {
    AppProvider: IAppProvider,
    FloatingWindow: IFloatingWindowProvider,
}

export type TNativeModulesFlat = {
    [K in keyof INativeModules]: INativeModules[K] extends Record<string, any> ? INativeModules[K] : never;
}[keyof INativeModules] & INativeModules;


export default INativeModules