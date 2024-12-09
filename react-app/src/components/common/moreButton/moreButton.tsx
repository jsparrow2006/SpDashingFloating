import React, { useEffect } from 'react';

import { AiOutlineAppstore } from "react-icons/ai";

import './moreButton.scss'

interface IMoreButton {
    onClick: () => void
    isOpen?: boolean
}


const MoreButton: React.FC<IMoreButton> = (props) => {
    const { onClick, isOpen } = props;

    return (
        <div className={`moreButton${isOpen ? ' isOpen' : ''}`} onClick={onClick}>
            <AiOutlineAppstore />
        </div>
    )
}

export default React.memo(MoreButton)