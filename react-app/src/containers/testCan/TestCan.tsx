import React, { useState, useEffect, useCallback } from 'react';

import './textCan.scss'
import SpNative from '../../lib/SpNative';

interface ITestCan {

}

const DEFAULT_CAN_DATA = {
}

const TestCan: React.FC<ITestCan> = (props) => {
    const [canData, setCanData] = useState<any>(DEFAULT_CAN_DATA)

    useEffect(() => {
        SpNative.subscribeEvent('CAN_BUS', onCanData)

        return () => {
            SpNative.unSubscribeEvent('CAN_BUS', onCanData)
        }
    }, [])

    const onCanData = useCallback((data: string) => {
        const slittedData = data.split('#')

        setCanData({
            ...canData,
            [slittedData[0]]: slittedData[1] || ''
        })
    }, [canData])

    return (
        <>
            <div className="canList">
                {Object.keys(canData).map((key) => {
                    return (
                        <div key={key} className='canDataRow'>
                            <span className='key'>{key}: </span>
                            <span>{canData[key]}</span>
                        </div>
                    )
                })}
            </div>
        </>
    );
};

export default React.memo(TestCan);