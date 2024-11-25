import React, { MouseEventHandler, useCallback } from 'react';

import { IApplication } from '../../appList';

import './appItem.scss'

interface IAppItem {
    onClick: (packageName: string) => void
    appItem: IApplication
}

const AppItem: React.FC<IAppItem> = (props) => {
    const { onClick, appItem } = props;

    const handleClickApp = useCallback((event: any) => {
        const appId = event.target.id
        console.log(appId)
        if (appId) {
            onClick(appId)
        }
    }, [onClick])

    return (
        <div className="appItemWrapper">
            <div className="appIcon">
                <img
                    id={appItem.packageName}
                    onClick={handleClickApp}
                    src={`data:image/jpeg;base64, ${appItem.icon}`}
                />
            </div>
            <span className="appName">
                {appItem.appName}
            </span>
        </div>
    )
}


export default React.memo(AppItem)