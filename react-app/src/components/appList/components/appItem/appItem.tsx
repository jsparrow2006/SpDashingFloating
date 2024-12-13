import React, { MouseEventHandler, useCallback } from 'react';

import IApp from '../../../../lib/SpNative/types/IApp';

import './appItem.scss'

interface IAppItem {
    onClick: (packageName: string) => void
    appItem: IApp
}

const AppItem: React.FC<IAppItem> = (props) => {
    const { onClick, appItem } = props;

    const handleClickApp = useCallback((event: any) => {
        const appId = event.target.id
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