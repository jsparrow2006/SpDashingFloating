import React, { useRef, useState, useEffect } from 'react';

import './slider.scss';

interface ISlider {
    slides: React.ReactElement[]
}

const Slider: React.FC<ISlider> = (props) => {
    const { slides = [] } = props;
    const [activeSlide, setActiveSlide] = useState(0);
    const sliderRef = useRef(null);

    const handleScroll = () => {
        const scrollLeft = sliderRef.current.scrollLeft;
        const slideWidth = sliderRef.current.clientWidth;

        const currentSlide = Math.round(scrollLeft / slideWidth);
        setActiveSlide(currentSlide);
    };

    useEffect(() => {
        const slider = sliderRef.current;
        slider.addEventListener('scroll', handleScroll);

        return () => {
            slider.removeEventListener('scroll', handleScroll);
        };
    }, []);

    const setSlide = (index) => {
        if (index < 0 || index >= slides.length) return;
        setActiveSlide(index);
        sliderRef.current.scrollTo({
            left: sliderRef.current.clientWidth * index,
            behavior: 'smooth',
        });
    };

    return (
        <div className="sliderWrapper">
            <div className="pointsWrapper">
                {slides.map((slides, index) => {
                    return <span key={`slide-point-${index}`} className={`point${index === activeSlide ? ' active' : ''} `} />
                })}
            </div>
            <div className="slider" ref={sliderRef}>
                {slides.map((slide, index) => (
                    <div
                        className="slide"
                        key={index}
                    >
                        {slide}
                    </div>
                ))}
            </div>
            {/*<div className="navigation">*/}
            {/*    <button onClick={() => setSlide(activeSlide - 1)}>Prev</button>*/}
            {/*    <span>Slide {activeSlide + 1}</span>*/}
            {/*    <button onClick={() => setSlide(activeSlide + 1)}>Next</button>*/}
            {/*</div>*/}
        </div>
    );
};

export default Slider;