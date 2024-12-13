import React from 'react';

import './grid.scss'

interface IGrid {
    children?: React.ReactElement | string | any;
    type?: 'column' | 'row';
    width?: 1 | 2| 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12;
    className?: string;

}

const Grid: React.FC<IGrid> = (props) => {
    const {
        children,
        type = 'column',
        width = 12,
        className = ''
    } = props;

    return (
        <div
            className={`grid ${type} ${className}`}
            style={{['--width']: width}}
        >
            {children}
        </div>
    );
};

export default React.memo(Grid);