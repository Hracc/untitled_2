// src/components/Header.jsx
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Logo } from "./Logo";
import { CityModal } from "./CityModal";
import { Location } from "./Location";

import "./Header.scss";
import {Person} from "./Person.jsx";

export function Header({ onLoginClick }) {
    const [isCityModalOpen, setCityModalOpen] = useState(false);
    const [selectedCity, setSelectedCity] = useState("");

    useEffect(() => {
        const cityFromStorage = localStorage.getItem("selectedCity") || "";
        setSelectedCity(cityFromStorage);
    }, []);

    const handleCloseModal = () => {
        setCityModalOpen(false);
        const cityFromStorage = localStorage.getItem("selectedCity") || "";
        setSelectedCity(cityFromStorage);
    };

    return (
        <header className="header">
            <div className="container">
                <div className="header__logo">
                    <Link to="/" className="header__logo-link">
                        <Logo />
                    </Link>
                </div>
                <div className="header__buttons">

                    {/* Кнопка выбора города */}
                    <button onClick={() => setCityModalOpen(true)} className="header__button city-button">
                        {/* Иконка (Location) */}
                        <span className="city-button-icon">
                            <Location />
                        </span>
                        {/* Текст */}
                        <span className="city-button-text">
                            {selectedCity ? selectedCity : "Выбрать город"}
                        </span>
                    </button>

                    {/* Кнопка входа/регистрации */}
                    <button onClick={onLoginClick} className="header__button login-button">
                        <span className="login-button-icon">
                            <Person />
                        </span>
                        <span className="login-button-text">Вход или регистрация</span>
                    </button>
                </div>
            </div>

            {/* Модальное окно выбора города */}
            <CityModal
                isOpen={isCityModalOpen}
                onClose={handleCloseModal}
            />
        </header>
    );
}
