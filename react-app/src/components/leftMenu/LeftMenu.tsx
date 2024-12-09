import React from 'react';
import { useMatch } from "react-router"
import { Link } from 'react-router-dom';

import './leftMenu.scss'

export interface IMenuItem {
    icon: React.ReactElement;
    caption: string;
    url: string;
}

interface ILeftMenu {
    menuList: IMenuItem[]
}

const LeftMenu: React.FC<ILeftMenu> = (props) => {
    const { menuList } = props;
    const { params } = useMatch('/myDashing/:page')

    return (
        <>
            <div className="menuWrapper">
                {menuList.map((menuItem) => {
                    return (
                        <Link key={`menuItem-${menuItem.url}`} to={`${menuItem.url}`}>
                            <div className={`menuItem${params.page === menuItem.url ? ' active' : ''}`}>
                                <div className="icon">{menuItem.icon}</div>
                                <div className="caption">{menuItem.caption}</div>
                            </div>
                        </Link>
                    )
                })}
                <span className="devider" />
            </div>
        </>
    );
};

export default React.memo(LeftMenu);