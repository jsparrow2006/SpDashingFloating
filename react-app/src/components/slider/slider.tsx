import React, { useRef, useState, useEffect } from 'react';

import './slider.scss';

interface ISlider {
    slides: React.ReactElement[]
}

const Slider: React.FC<ISlider> = (props) => {
    const { slides = [] } = props;
    const [activeSlide, setActiveSlide] = useState(0);

    return (
        <div className="sliderWrapper">
            <div className="pointsWrapper">
                {slides.map((slides, index) => {
                    return <span key={`slide-point-${index}`} className={`point${index === activeSlide ? ' active' : ''} `} />
                })}
            </div>
            <div className="slider">
                {slides.map((slide, index) => (
                    <div
                        className="slide"
                        key={index}
                    >
                        {slide}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Slider;