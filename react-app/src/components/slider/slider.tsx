import React, { useState, useCallback } from 'react';
import { RiArrowLeftWideLine, RiArrowRightWideLine } from "react-icons/ri";

import './slider.scss';

interface ISlider {
    slides: React.ReactElement[]
}

const Slider: React.FC<ISlider> = (props) => {
    const { slides = [] } = props;
    const [activeSlide, setActiveSlide] = useState(0);

    const handleNext = useCallback(() => {
        if (activeSlide !== slides.length - 1) {
            setActiveSlide(activeSlide + 1)
        }
    }, [activeSlide])

    const handlePrev = useCallback(() => {
        if (activeSlide !== 0) {
            setActiveSlide(activeSlide - 1)
        }
    }, [activeSlide])

    return (
        <div className="sliderWrapper">
            <div className="pointsWrapper">
                {slides.map((slides, index) => {
                    return <span key={`slide-point-${index}`} className={`point${index === activeSlide ? ' active' : ''} `} />
                })}
            </div>
            <div className="button prev" onClick={handlePrev}><RiArrowLeftWideLine/></div>
            <div className="button next" onClick={handleNext}><RiArrowRightWideLine/></div>
            <div className="slider" style={{ transform: `translateX(-${activeSlide * 100}%)` }}>
                {slides.map((slide, index) => (
                    <div
                        className={`slide${index === activeSlide ? ' active' : ''}`}
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