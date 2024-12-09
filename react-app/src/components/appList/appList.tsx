import React  from 'react';

import IApp from '../../lib/SpNative/types/IApp';

import AppItem from './components/appItem/appItem';

import './appList.scss'

interface IAppList {
    onOpenApp: (packageName: string) => void
    onOpenURLApp: (url: string) => void
    appList: IApp[]
}

const AppList: React.FC<IAppList> = (props) => {
    const { onOpenApp, onOpenURLApp, appList } = props;

    return (
        <div className="appListWrapper">
            <span className="devider" />
            <div className="appList">
                <AppItem key="/settings" appItem={{appName: "My Dashing", packageName: '/myDashing', icon: appList[0]?.icon || ''}} onClick={onOpenURLApp} />
                <AppItem key="/test" appItem={{appName: "Test", packageName: '/test', icon: appList[0]?.icon || ''}} onClick={onOpenURLApp} />
                {appList.map((app) => {
                    return <AppItem key={app.packageName} appItem={app} onClick={onOpenApp} />
                })}
            </div>
        </div>
    )
}

export default React.memo(AppList)