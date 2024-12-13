import React, { MouseEventHandler } from 'react';

import './button.scss'

interface IButton {
    id?: string;
    children: React.ReactElement | string
    caption?: string;
    onClick?: (event?: MouseEventHandler<HTMLButtonElement>) => void
    className?: string;
    dark?: boolean;
    disabled?: boolean;
}

const Button: React.FC<IButton> = (props) => {
    const {
        id,
        children,
        caption,
        onClick = () => {},
        className,
        dark,
        disabled = false
    } = props;

    return (
        <button id={id} onClick={onClick} disabled={disabled} className={`${dark ? 'dark' : ''} ${className || ''}`}>
            {caption || children}
        </button>
    );
};

export default React.memo(Button);