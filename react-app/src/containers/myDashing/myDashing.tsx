import React, { useMemo } from 'react';
import { Outlet, Link } from 'react-router-dom';

import { FaCog, FaCar } from 'react-icons/fa';
import { AiFillDashboard } from "react-icons/ai";

import LeftMenu, { IMenuItem } from '../../components/leftMenu/LeftMenu';

import './myDashing.scss'

const MyDashing: React.FC = () => {

    const menuList = useMemo<IMenuItem[]>(() => {
        return [
            {
                icon: <FaCar />,
                caption: 'Информация',
                url: 'info'
            },
            {
                icon: <AiFillDashboard />,
                caption: 'Одометр',
                url: 'odo'
            },
            {
                icon: <FaCog />,
                caption: 'Настройки',
                url: 'settings'
            },
        ]
    }, [])

    return (
        <div className="myDashingWrapper">
            <LeftMenu menuList={menuList} />
            <div className="content">
                <Outlet />
            </div>
        </div>
    )
}

export default React.memo(MyDashing)