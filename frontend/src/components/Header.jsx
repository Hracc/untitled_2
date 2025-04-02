import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { Logo } from "./Logo";
import { CityModal } from "./CityModal";
import { Location } from "./Location";
import { getUserRole } from "../api/jwt.js";

import "./Header.scss";
import {Person} from "./Person.jsx";
import { getTokenOrThrow, getLocalJSON} from "../api/utils.js";
import { serviceItem } from "../api/client/services.js";

export function Header({ onLoginClick }) {
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

    const getDefaultRoute = () => {
        const role = getUserRole();
    
        return !role 
            ? "/" 
            : role === "ADMINISTRATION"
            ? "/admin/requests"
            : role === "ORGANIZATION"
            ? "/partner/form"
            : "/";
    }

    const handleProfileClick = () => {
        const role = getUserRole();
    
        if (!role) {
            onLoginClick(); // Открывает модалку входа, если нет токена
        } else {
            // Определяем дефолтный маршрут для роли
            const defaultRoute = role === "ADMINISTRATION"
                ? "/admin/profile"
                : role === "ORGANIZATION"
                ? "/partner/profile"
                : "/profile";
    
            navigate(defaultRoute);
        }
    }

    const handleCloseModal = () => {
        setCityModalOpen(false);
        const cityFromStorage = getLocalJSON(item, 'city')
        setSelectedCity(cityFromStorage);
    };

    const role = getUserRole();

    return (
        <header className="header">
            <div className="container">
                <div className="header__logo">
                    <Link to={getDefaultRoute()} className="header__logo-link">
                        <Logo />
                    </Link>
                </div>
                <div className="header__buttons">

                    {/* Показываем кнопку выбора города только если роль CUSTOMER */}
                    {role === "CUSTOMER" && (
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
                    )}

                    {/* Кнопка входа/регистрации */}
                    <button onClick={handleProfileClick} className="header__button login-button">
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
