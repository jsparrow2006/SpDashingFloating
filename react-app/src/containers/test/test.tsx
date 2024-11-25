import React from 'react';

import './test.scss'

const Test: React.FC = () => {

    return (
        <div className="testWrapper">
            <h2>Test Page</h2>
        </div>
    )
}

export default React.memo(Test)