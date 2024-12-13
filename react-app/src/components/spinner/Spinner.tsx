import React from 'react';

import './spinner.scss'

interface ISpinner {
    size: number
}

const Spinner: React.FC<ISpinner> = (props) => {
    const {
        size = 35
    } = props;

    return (
        <div className='spinnerWrapper'>
            <div className='spinner' style={{width: `${size}px`, height: `${size}px`}} />
        </div>
    );
};

export default React.memo(Spinner);