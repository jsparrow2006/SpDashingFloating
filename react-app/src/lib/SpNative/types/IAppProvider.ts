import IApp from './IApp';

interface IAppProvider {
    getAppsListJson: () => IApp[];
    launchApp: (packageName: string) => void;
    launchWebActivity: (url: string) => void;
}

export default IAppProvider;