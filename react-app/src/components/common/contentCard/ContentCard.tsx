import React from 'react';

import './contentCard.scss';

interface IContentCard {
    caption: string
    children: React.ReactElement;
    smallCaption?: boolean;
    className?: string;
}

const ContentCard: React.FC<IContentCard> = (props) => {
    const { caption, children, smallCaption, className = '' } = props;

    return (
        <div className={`contentCardWrapper ${className}`}>
            <div className={`caption${smallCaption ? ' small': ''}`}>{caption}</div>
            {children}
        </div>
    );
};

export default React.memo(ContentCard);