import React, { useEffect, useState, useRef } from 'react';

import { FaRegSnowflake } from 'react-icons/fa';
import { TbTemperatureCelsius } from 'react-icons/tb';

import './timeAndTemperature.scss'


const TimeAndTemperature: React.FC = (props) => {
    const [date, setDate] = useState<Date>(new Date())
    const timerId = useRef<number | null>(null)

    useEffect(() => {
        timerId.current = setInterval(() => {
            setDate(new Date())
        }, 1000)

        return () => {
            clearInterval(timerId.current || 0)
        }
    }, [])

    return (
        <>
            <div className="time">{String(date.getHours()).padStart(2, '0')}:{String(date.getMinutes()).padStart(2, '0')}</div>
            <div className="temp"><FaRegSnowflake className="snow" /><span className="tempCount">-28</span><TbTemperatureCelsius className="type"/></div>
        </>
    )
}

export default React.memo(TimeAndTemperature)