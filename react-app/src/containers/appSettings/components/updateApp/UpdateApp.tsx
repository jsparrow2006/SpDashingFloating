import React, { useCallback, useEffect, useState, useMemo } from 'react';
import Grid from '../../../../components/common/grid/Grid';
import Button from '../../../../components/common/button/Button';
import ContentCard from '../../../../components/common/contentCard/ContentCard';
import ProgressBar from '../../../../components/progressBar/ProgressBar';
import Spinner from '../../../../components/spinner/Spinner';

import SpNative from '../../../../lib/SpNative';

import { IVersion } from '../../../../lib/SpNative/types/IUpdateManager';

import './updateApp.scss'

interface IUpdateApp {

}

const UpdateApp: React.FC<IUpdateApp> = (props) => {
    const {} = props;
    const [currentVersion, setCurrentVersion] = useState<IVersion>({} as IVersion);
    const [updateVersion, setUpdateVersion] = useState<IVersion>({} as IVersion);
    const [loadingUpdate, setLoadingUpdate] = useState<number>(0);
    const [checkUpdate, setCheckUpdate] = useState<boolean>(false);
    const [error, setError] = useState<string>('');

    const updateManager = SpNative.getNativeModules().UpdateManager;

    useEffect(() => {
        const currentVersion = updateManager.getCurrentVersion()
        setCurrentVersion(currentVersion)
    }, [])

    const handleCheckUpdates = useCallback(() => {
        setCheckUpdate(true)
        updateManager.asyncCheckUpdates()
            .then((data) => {
                setUpdateVersion(data)
            })
            .catch((error) => {
                setError(error)
            })
            .finally(() => {
                setCheckUpdate(false)
            })
    }, [setCheckUpdate, setError])

    const handleUpdate = useCallback(() => {
        SpNative.subscribeEvent('LOADING_UPDATE', setLoadingUpdate)
        setLoadingUpdate(0.1)
        updateManager.asyncUpdateApplication()
            .then((data) => {
                setUpdateVersion(data)
            })
            .catch((error) => {
                setError(error)
            })
            .finally(() => {
                SpNative.unSubscribeEvent('LOADING_UPDATE', setLoadingUpdate)
                setLoadingUpdate(0)
            })
    }, [setLoadingUpdate])

    const isHasUpdate = useMemo(() => {
        return updateVersion.versionCode > currentVersion.versionCode
    }, [currentVersion, updateVersion])

    return (
        <ContentCard caption="Обновление приложения" className='updateWrapper'>
            <Grid type="row">
                <Grid type="row" className='checkUpdate'>
                    <Grid type='row' width={6}>
                        <Button
                            disabled={checkUpdate}
                            onClick={handleCheckUpdates}
                        >
                            {checkUpdate ? <Spinner /> : 'Проверить обновления'}
                        </Button>
                    </Grid>
                    <Grid width={6} className='version'>
                        <span>Текущая версия: { currentVersion.versionName }</span>
                        {isHasUpdate && (
                            <span className="newVersion">Доступна версия: { updateVersion.versionName }</span>
                        )}
                    </Grid>
                </Grid>
                <Grid className='releaseNotes'>
                    {isHasUpdate && (
                        <>
                            <span>Что нового в версии {updateVersion.versionName}:</span>
                            {updateVersion?.releaseNotes}
                        </>
                    )}
                </Grid>
                <Grid>
                    {isHasUpdate && (
                        <Button
                            onClick={handleUpdate}
                            disabled={loadingUpdate > 0}
                        >
                            {loadingUpdate === 0 ? 'Обновить' : <ProgressBar textBefore='Загрузка' value={loadingUpdate} />}
                        </Button>
                    )}
                </Grid>
                {error && (
                    <Grid type='row' className='error'>
                        {error}
                    </Grid>
                )}
            </Grid>
        </ContentCard>
    );
};

export default React.memo(UpdateApp);