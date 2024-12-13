import IAppProvider from './IAppProvider';
import IFloatingWindowProvider from './IFloatingWindowProvider';
import IUpdateManager from './IUpdateManager';

interface INativeModules {
    AppProvider: IAppProvider,
    FloatingWindow: IFloatingWindowProvider,
    UpdateManager: IUpdateManager
}

export type TNativeModulesFlat = {
    [K in keyof INativeModules]: INativeModules[K] extends Record<string, any> ? INativeModules[K] : never;
}[keyof INativeModules] & INativeModules;


export default INativeModules