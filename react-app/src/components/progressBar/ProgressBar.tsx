import React from 'react';

import './progressBar.scss'

interface IProgressBar {
    value: number;
    dimension?: string;
    textBefore?: string;
}

const ProgressBar: React.FC<IProgressBar> = (props) => {
    const {
        value,
        dimension = '%',
        textBefore = ''
    } = props;

    return (
        <div className="progressBarWrapper">
            <div className="progress" style={{width: `${value}%`}} />
            <span className="value">{textBefore} {Math.floor(value)}{dimension}</span>
        </div>
    );
};

export default React.memo(ProgressBar);