import IApp from './IApp';

export interface IVersion {
    versionCode: number;
    versionName: string;
    releaseNotes?: string;
}

interface IUpdateManager {
    asyncCheckUpdates: () => Promise<IVersion>;
    getCurrentVersion: () => IVersion;
    asyncUpdateApplication: () => Promise<any>
}

export default IUpdateManager;