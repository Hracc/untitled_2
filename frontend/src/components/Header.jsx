import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Logo } from "./Logo";
import { CityModal } from "./CityModal";
import { Location } from "./Location";
import { getUserRole } from "../api/jwt.js";
import { Person } from "./Person.jsx";
import { getTokenOrThrow, getLocalJSON } from "../api/utils.js";
import { serviceItem } from "../api/client/services.js";

import "./Header.scss";

export function Header({ onLoginClick }) {
    const [isCityModalOpen, setCityModalOpen] = useState(false);
    const [selectedCity, setSelectedCity] = useState(
        () => getLocalJSON(serviceItem.selectedData, 'city') || ""
    );
    const [isAuthorization, setIsAuthorization] = useState(false);
    const [isAuthChecked, setIsAuthChecked] = useState(false); // добавлено
    const navigate = useNavigate();
    const item = serviceItem.selectedData;

    const initialAuthState = (() => {
        try {
            const token = getTokenOrThrow();
            return {
                isAuthorized: true,
                role: getUserRole()
            };
        } catch (error) {
            return {
                isAuthorized: false,
                role: null
            };
        }
    })();

    useEffect(() => {
        try {
            const token = getTokenOrThrow();
            setIsAuthorization(true);
        } catch (error) {
            setIsAuthorization(false);
        } finally {
            setIsAuthChecked(true); // установка после проверки
        }

        const cityFromStorage = getLocalJSON(item, 'city');
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
    };

    const handleProfileClick = () => {
        const role = getUserRole();

        if (!role) {
            onLoginClick(); // Открывает модалку входа, если нет токена
        } else {
            const defaultRoute = role === "ADMINISTRATION"
                ? "/admin/profile"
                : role === "ORGANIZATION"
                ? "/partner/profile"
                : "/profile";

            navigate(defaultRoute);
        }
    };

    const handleCloseModal = () => {
        setCityModalOpen(false);
        const cityFromStorage = getLocalJSON(item, 'city');
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

                    {/* Кнопка выбора города (только если роль CUSTOMER или не авторизован) */}
                    {(!role || role === "CUSTOMER") && (
                        <button onClick={() => setCityModalOpen(true)} className="header__button city-button">
                            <span className="city-button-icon">
                                <Location />
                            </span>
                            <span className="city-button-text">
                                {selectedCity ? selectedCity : "Выбрать город"}
                            </span>
                        </button>
                    )}

                    {/* Кнопка входа/профиля — только после завершения проверки авторизации */}
                    {isAuthChecked && (
                        <button onClick={handleProfileClick} className="header__button login-button">
                            <span className="login-button-icon">
                                <Person />
                            </span>
                            <span className="login-button-text">
                                {isAuthorization ? "Профиль" : "Вход или регистрация"}
                            </span>
                        </button>
                    )}
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
