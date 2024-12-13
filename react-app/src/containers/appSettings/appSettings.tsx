import React from 'react';

import WidgetWindowParams from './components/widgetWindowParams/WidgetWindowParams';
import UpdateApp from './components/updateApp/UpdateApp';

import './appSettings.scss'

const AppSettings: React.FC = () => {

    return (
        <div className="appSettingsWrapper">
            <WidgetWindowParams />
            <UpdateApp />
        </div>
    )
}

export default React.memo(AppSettings)