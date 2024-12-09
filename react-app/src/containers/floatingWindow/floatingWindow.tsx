import React, { useCallback, useEffect, useState } from 'react';

import SpNative from '../../lib/SpNative/index'

import IFloatingWindowParams from '../../lib/SpNative/types/IFloatingWindowParams';
import IApp from '../../lib/SpNative/types/IApp';

import Slider from '../../components/slider/slider';
import AppList from '../../components/appList/appList';
import MoreButton from '../../components/common/moreButton/moreButton'
import TimeAndTemperature from '../../components/timeAndTemperature/timeAndTemperature'

import './floatingWindow.scss'

const FloatingWindow: React.FC = () => {
    const [isOpenWindow, setIsOpenWindow] = useState<boolean>(false);
    const [appList, setAppList] = useState<IApp[]>([])
    const [windowParams, setWindowParams] = useState<IFloatingWindowParams>({} as IFloatingWindowParams)

    const floatingWindow = SpNative.getNativeModules().FloatingWindow;
    const AppProvider = SpNative.getNativeModules().AppProvider

    useEffect(() => {
        setWindowParams(floatingWindow.getWindowParams())
        loadApps()
    }, [])

    const loadApps = () => {
        const apps = AppProvider.getAppsListJson()
        setAppList(apps)
    }

    const handleOpenApp = useCallback((appId: string) => {
        AppProvider.launchApp(appId)
    }, [AppProvider])

    const handleOpenUrlApp = useCallback((url: string) => {
        AppProvider.launchWebActivity(url)
    }, [AppProvider])

    const handleCLickMore = useCallback(() => {
        const isOpenWindow = floatingWindow.handleOpenWindow()
        setIsOpenWindow(isOpenWindow)
        if (isOpenWindow) {
            loadApps()
        }
    }, [])

    return (
        <div className="floatingWrapper">
            <div className="mainArea" style={{height: windowParams.heightPx || 0}}>
                {/*<div className="notification">Same notification</div>*/}
                   <Slider
                       slides={[
                           <h1>Slide 111</h1>,
                           <h1>Slide 2</h1>,
                           <h1>Slide 3</h1>,
                           <h1>Slide 4</h1>,
                           <h1>Slide 5</h1>,
                       ]}
                   />
                <TimeAndTemperature />
            </div>
            <AppList appList={appList} onOpenApp={handleOpenApp} onOpenURLApp={handleOpenUrlApp} />
            <MoreButton onClick={handleCLickMore} isOpen={isOpenWindow} />
        </div>
    )
}

export default React.memo(FloatingWindow)