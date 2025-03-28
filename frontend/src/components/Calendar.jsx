import React, { useState } from "react";
import "./Calendar.scss";
import { NextButton } from "./Next-Button.jsx";
import { PrevButton } from "./Prev-Button.jsx";

import { addDate } from "../api/client/services.js";

// Именительный падеж (для шапки календаря)
const MONTH_NAMES_NOMINATIVE = [
    "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
    "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
];

// Родительный падеж (для склонения «2 марта», «10 июня» и т. д.)
const MONTH_NAMES_GENITIVE = [
    "января", "февраля", "марта", "апреля", "мая", "июня",
    "июля", "августа", "сентября", "октября", "ноября", "декабря"
];

const WEEK_DAYS = ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"];

function getDaysInMonth(year, month) {
    const date = new Date(year, month, 1);
    const days = [];
    let startDay = date.getDay();
    if (startDay === 0) startDay = 7;

    for (let i = 1; i < startDay; i++) {
        days.push(null);
    }

    while (date.getMonth() === month) {
        days.push(new Date(date));
        date.setDate(date.getDate() + 1);
    }
    return days;
}

function chunkArray(array, size) {
    const result = [];
    for (let i = 0; i < array.length; i += size) {
        result.push(array.slice(i, i + size));
    }
    return result;
}

export default function Calendar( { onDateTimeSelect } ) {
    const today = new Date();
    const [isCalendarOpen, setIsCalendarOpen] = useState(false);
    const [currentYear, setCurrentYear] = useState(today.getFullYear());
    const [currentMonth, setCurrentMonth] = useState(today.getMonth());
    const [selectedDate, setSelectedDate] = useState(null);
    const [selectedTime, setSelectedTime] = useState(null);

    const times = Array.from({ length: 12 }, (_, i) => `${10 + i}:00`);
    const timeColumns = chunkArray(times, 3);
    const days = getDaysInMonth(currentYear, currentMonth);

    const isCurrentMonthAndYear =
        currentYear === today.getFullYear() && currentMonth === today.getMonth();

    const handlePrevMonth = () => {
        if (isCurrentMonthAndYear) return;
        if (currentMonth === 0) {
            setCurrentMonth(11);
            setCurrentYear(currentYear - 1);
        } else {
            setCurrentMonth(currentMonth - 1);
        }
        setSelectedDate(null);
        setSelectedTime(null);
    };

    const handleNextMonth = () => {
        if (currentMonth === 11) {
            setCurrentMonth(0);
            setCurrentYear(currentYear + 1);
        } else {
            setCurrentMonth(currentMonth + 1);
        }
        setSelectedDate(null);
        setSelectedTime(null);
    };

    const handleDayClick = (day) => {
        if (!day) return;
        const todayMidnight = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        if (day < todayMidnight) return;

        setSelectedDate(day);
        setSelectedTime(null);
    };

    const handleTimeClick = (time) => {
        setSelectedTime(time);
        setIsCalendarOpen(false);

        if(onDateTimeSelect && selectedDate) {
            const selectedDateTime = new Date(selectedDate);
            const [hours, minutes] = time.split(":").map(Number); 
            selectedDateTime.setHours(hours, minutes, 0, 0); 
            
            onDateTimeSelect(selectedDateTime)
            
            addDate(selectedDateTime)
        }
    };

    const maxSelectableDate = new Date();
    maxSelectableDate.setDate(maxSelectableDate.getDate() + 45);

    let displayDateTime = "Выберите дату и время";
    if (selectedDate && selectedTime) {
        const day = selectedDate.getDate();
        const monthName = MONTH_NAMES_GENITIVE[selectedDate.getMonth()];
        displayDateTime = `${day} ${monthName} ${selectedTime}`;
    }

    return (
        <div className="calendar-wrapper">
            <button className="calendar-toggle-btn" onClick={() => setIsCalendarOpen(!isCalendarOpen)}>
                {displayDateTime}
            </button>

            {isCalendarOpen && (
                <div className="calendar">
                    <div className="calendar__header">
                        <button className="calendar__nav-btn" onClick={handlePrevMonth} disabled={isCurrentMonthAndYear}>
                            <PrevButton />
                        </button>
                        <div className="calendar__month-label">
                            {MONTH_NAMES_NOMINATIVE[currentMonth]} {currentYear}
                        </div>
                        <button className="calendar__nav-btn" onClick={handleNextMonth}>
                            <NextButton />
                        </button>
                    </div>

                    <div className="calendar__weekdays">
                        {WEEK_DAYS.map((wd) => (
                            <div key={wd} className="calendar__weekday">
                                {wd}
                            </div>
                        ))}
                    </div>

                    <div className="calendar__days">
                        {days.map((day, idx) => {
                            if (!day) {
                                return <div key={idx} className="calendar__day calendar__day--empty" />;
                            }

                            const isSelected =
                                selectedDate && day.toDateString() === selectedDate.toDateString();
                            const isPast = day < new Date(today.getFullYear(), today.getMonth(), today.getDate());
                            const isBeyondLimit = day > maxSelectableDate;

                            return (
                                <div
                                    key={idx}
                                    className={
                                        "calendar__day" +
                                        (isPast ? " calendar__day--past" : "") +
                                        (isBeyondLimit ? " calendar__day--past" : "") +
                                        (isSelected ? " calendar__day--selected" : "")
                                    }
                                    onClick={() => {
                                        if (!isPast && !isBeyondLimit) {
                                            handleDayClick(day);
                                        }
                                    }}
                                >
                                    {day.getDate()}
                                </div>
                            );
                        })}
                    </div>

                    {selectedDate && (
                        <div className="calendar__time">
                            {timeColumns.map((column, colIndex) => (
                                <div key={colIndex} className="calendar__time-column">
                                    {column.map((time) => {
                                        const [hour] = time.split(":").map(Number);
                                        const selectedDateTime = new Date(selectedDate);
                                        selectedDateTime.setHours(hour, 0, 0, 0);

                                        const nowPlus30Min = new Date();
                                        nowPlus30Min.setMinutes(nowPlus30Min.getMinutes() + 30);

                                        const isPastTime = selectedDateTime < nowPlus30Min;
                                        const isSelected = time === selectedTime;

                                        return (
                                            <div
                                                key={time}
                                                className={
                                                    "calendar__time-slot" +
                                                    (isSelected ? " calendar__time-slot--selected" : "") +
                                                    (isPastTime ? " calendar__time-slot--disabled" : "")
                                                }
                                                onClick={() => {
                                                    if (!isPastTime) {
                                                        handleTimeClick(time);
                                                    }
                                                }}
                                            >
                                                {time}
                                            </div>
                                        );
                                    })}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}
