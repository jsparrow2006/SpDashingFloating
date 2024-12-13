import React, { useState, useCallback } from 'react';

import './selector.scss'
import Button from '../button/Button';

export interface IOption {
    name: string;
    value: string;
}

interface ISelector {
    caption?: string
    options: IOption[];
    onChange: (value: string) => void
}

const Selector: React.FC<ISelector> = (props) => {
    const {
        caption,
        options = [],
        value,
        onChange = (value: string) => {}
    } = props;

    const [selected, setSelected] = useState<string>(value || options[0].name)

    const handleSelect = useCallback((event) => {
        // setSelected(event.target.id)
        onChange(event.target.id)
    }, [])

    return (
        <div className='selectorWrapper'>
            {caption && (
                <div className="caption">{caption}</div>
            )}
            <div className="selectorElements">
                {options.map((option: IOption) => {
                    return (
                        <div key={`selector-option-${option.value}`}>
                            <Button
                                className={option.value != value ? 'notActive' : ''}
                                caption={option.name}
                                id={String(option.value)}
                                onClick={handleSelect}
                            />
                        </div>
                    )
                })}
            </div>
        </div>
    );
};

export default React.memo(Selector);