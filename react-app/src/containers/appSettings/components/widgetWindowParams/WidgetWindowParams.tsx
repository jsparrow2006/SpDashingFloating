import React, { useCallback, useEffect, useState } from 'react';
import Grid from '../../../../components/common/grid/Grid';
import Counter from '../../../../components/common/counter/Counter';
import Button from '../../../../components/common/button/Button';
import ContentCard from '../../../../components/common/contentCard/ContentCard';
import IFloatingWindowParams from '../../../../lib/SpNative/types/IFloatingWindowParams';
import SpNative from '../../../../lib/SpNative';

interface IWidgetWindowParams {

}

const WidgetWindowParams: React.FC<IWidgetWindowParams> = (props) => {
    const {} = props;
    const [windowParams, setWindowParams] = useState<IFloatingWindowParams>({} as IFloatingWindowParams)

    const floatingWindow = SpNative.getNativeModules().FloatingWindow;

    useEffect(() => {
        getWidgetWindowParams()
    }, []);

    const getWidgetWindowParams = useCallback(() => {
        const windowParams = floatingWindow.getWindowParams()
        setWindowParams(windowParams)
    }, []);

    const handleSetPositionX = useCallback((newValue: number) => {
        floatingWindow.setWindowPositionX(newValue);
        getWidgetWindowParams()
    }, []);

    const handleSetPositionY = useCallback((newValue: number) => {
        floatingWindow.setWindowPositionY(newValue);
        getWidgetWindowParams()
    }, []);

    const handleSetWidgetWidth = useCallback((newValue: number) => {
        floatingWindow.setWindowWith(newValue);
        getWidgetWindowParams()
    }, []);

    const handleSetWidgetHeight = useCallback((newValue: number, isOpen: boolean = false) => {
        floatingWindow.setWindowHeight(newValue, isOpen);
        getWidgetWindowParams()
    }, []);

    const handleSaveWidgetParams = useCallback(() => {
        floatingWindow.saveWindowParams();
        getWidgetWindowParams()
    }, []);

    return (
        <ContentCard caption="Положение и размер виджета">
            <Grid type="row">
                <Grid type='row' width={6}>
                    <Counter caption='Положение по X' value={windowParams.posX} onChange={handleSetPositionX} />
                </Grid>
                <Grid type='row' width={6}>
                    <Counter caption='Положение по Y' value={windowParams.posY} onChange={handleSetPositionY} />
                </Grid>
            </Grid>
            <Grid type="row">
                <Grid type='row' width={6}>
                    <Counter caption='Ширина виджета' value={windowParams.withDp} onChange={handleSetWidgetWidth} />
                </Grid>
                <Grid type='row' width={6}>
                    <Counter caption='Высота виджета' value={windowParams.heightDp} onChange={handleSetWidgetHeight} />
                </Grid>
                <Grid type='row' width={6}>
                    <Counter
                        caption='Высота открытого виджета'
                        value={windowParams.heightOpenDp}
                        onChange={(value) => handleSetWidgetHeight(value, true)}
                    />
                </Grid>
            </Grid>
            <Grid>
                <Button caption='Сохранить положение и размер' onClick={handleSaveWidgetParams} />
            </Grid>
        </ContentCard>
    );
};

export default React.memo(WidgetWindowParams);