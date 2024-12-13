import React from 'react';

import './slideGear.scss'

interface ISlideGear {

}

const SlideGear: React.FC<ISlideGear> = (props) => {
    const {} = props;

    return (
        <div className="slideGearWrapper">
            <div className="gear">
                <div className="caption">
                    Передача
                </div>
                <div className="value">
                    D1
                </div>
            </div>

            <div className="fuelFlow">
                <div className="block">
                    <div className="caption">
                        Моментальный расход
                    </div>
                    <div className="value">
                        15,4 <span>L</span>
                    </div>
                </div>
                <div className="block">
                    <div className="caption">
                        Средний расход
                    </div>
                    <div className="value">
                        9.8 <span>L/100km</span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default React.memo(SlideGear);