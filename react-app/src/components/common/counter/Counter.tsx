import React, { useCallback } from 'react';

import Button from '../button/Button';

import './conter.scss'

interface ICounter {
    caption?: string;
    value: number;
    step?: number;
    onChange?: (value: number) => void

}

const Counter: React.FC<ICounter> = (props) => {
    const {
        caption,
        value,
        step = 1,
        onChange = (value: number) => {}
    } = props;


    const handleLess = useCallback(() => {
        onChange(value - step)
    }, [value, step])

    const handleMore = useCallback(() => {
        onChange(value + step)
    }, [value, step])

    return (
        <div className='counterWrapper'>
            {caption && (
                <div className="caption">{caption}</div>
            )}
            <div className="countersElements">
                <div><Button caption='-' onClick={handleLess} /></div>
                <div className='count'>{value || 0}</div>
                <div><Button caption='+' onClick={handleMore} /></div>
            </div>
        </div>
    );
};

export default React.memo(Counter);