import React, { useEffect } from 'react';

import './moreButton.scss'

interface IMoreButton {
    onClick: () => void
    isOpen?: boolean
}


const MoreButton: React.FC<IMoreButton> = (props) => {
    const { onClick, isOpen } = props;

    return (
        <div className="moreButton" onClick={onClick}>
            <div className={`dotWrapper${isOpen ? ' open' : ''}`}>
                <span className="dot" />
                <span className="dot" />
                <span className="dot" />
            </div>
        </div>
    )
}

export default React.memo(MoreButton)