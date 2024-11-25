import React, { useCallback, useEffect, useState } from 'react';

import SpNative from '../../lib/SpNative/index'

import { IApplication } from '../../components/appList/appList';

import Slider from '../../components/slider/slider';
import AppList from '../../components/appList/appList';
import MoreButton from '../../components/slider/components/moreButton'

import './floatingWindow.scss'

const FloatingWindow: React.FC = () => {
    const [isOpenWindow, setIsOpenWindow] = useState<boolean>(false);
    const [appList, setAppList] = useState<IApplication[]>([])

    const { handleOpenWindow, getAppsListJson, launchApp, launchWebActivity } = SpNative.getAndroidModules(['FloatingWindow', 'AppProvider']);

    useEffect(() => {
        SpNative.subscribeEvent('floatingWindow:isOpen', onOpenWindow)
        loadApps()
        return () => {
            SpNative.unSubscribeEvent('floatingWindow:isOpen', onOpenWindow)
        }
    }, [])

    const loadApps = () => {
        const apps = getAppsListJson()
        setAppList(JSON.parse(apps))
        console.log(JSON.parse(getAppsListJson()))
    }

    const onOpenWindow = (isOpen: boolean) => {
        if (isOpen) {
            loadApps()
        }
        setIsOpenWindow(isOpen)
    }

    const handleOpenApp = useCallback((appId: string) => {
        launchApp(appId)
    }, [launchApp])

    const handleOpenUrlApp = useCallback((url: string) => {
        console.log(url)
        console.log(launchWebActivity)
        launchWebActivity(url)
    }, [launchWebActivity])

    const handleCLickMore = useCallback(() => {
        handleOpenWindow()
    }, [])

    return (
        <div className="floatingWrapper">
            <div className="mainArea">
                   <Slider
                       slides={[
                           <h1>Slide 111</h1>,
                           <h1>Slide 2</h1>,
                           <h1>Slide 3</h1>,
                           <h1>Slide 4</h1>,
                           <h1>Slide 5</h1>,
                       ]}
                   />
            </div>
            <AppList appList={appList} onOpenApp={handleOpenApp} onOpenURLApp={handleOpenUrlApp} />
            <MoreButton onClick={handleCLickMore} isOpen={isOpenWindow} />
        </div>
    )
}

export default React.memo(FloatingWindow)