import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { Logo } from "./Logo";
import { CityModal } from "./CityModal";
import { Location } from "./Location";

import "./Header.scss";
import {Person} from "./Person.jsx";
import { getTokenOrThrow, getLocalJSON} from "../api/utils.js";
import { serviceItem } from "../api/client/services.js";

export function Header({ onLoginClick, logo="/" }) {
    const [isCityModalOpen, setCityModalOpen] = useState(false);
    const [selectedCity, setSelectedCity] = useState("");
    const [isAuthorization, setIsAuthorization] = useState(false);
    const navigate = useNavigate()
    const item = serviceItem.selectedData

    useEffect(() => {
        try {
            const token = getTokenOrThrow()
            setIsAuthorization(true)
        } catch (error) {
            setIsAuthorization(false)
        }

        const cityFromStorage = getLocalJSON(item, 'city')
        setSelectedCity(cityFromStorage);
    }, []);

    const handleCloseModal = () => {
        setCityModalOpen(false);
        const cityFromStorage = getLocalJSON(item, 'city')
        setSelectedCity(cityFromStorage);
    };

    return (
        <header className="header">
            <div className="container">
                <div className="header__logo">
                    <Link to={logo} className="header__logo-link">
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
                    <button onClick= {() => {
                        if (isAuthorization) {
                            navigate("/profile");
                        } else {
                            onLoginClick();
                        }
                    }}
                            className="header__button login-button">
                        <span className="login-button-icon">
                            <Person />
                        </span>
                        <span className="login-button-text">{isAuthorization ? "Профиль" : "Вход или регистрация"}</span>
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
