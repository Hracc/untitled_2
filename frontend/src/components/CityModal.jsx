// src/components/CityModal.jsx
import { useState, useEffect } from "react";
import { getCities } from "../api/client/services";
import { SearchBar } from "./SearchBar";
import "./modal.scss"; // Подключаем стили модалки

export function CityModal({ isOpen, onClose }) {
    const [search, setSearch] = useState("");
    const [cities, setCities] = useState([]);

    useEffect(() => {
        // При открытии модалки загружаем список городов
        async function fetchCities() {
            try {
                const organizations = await getCities();
                const uniqueCities = [...new Set(organizations.map((org) => org.city))];
                setCities(uniqueCities);
            } catch (error) {
                console.error("Ошибка при загрузке данных:", error);
            }
        }

        if (isOpen) {
            fetchCities()
                .then(() => {
                    // успех
                })
                .catch((err) => console.error(err));
        }
    }, [isOpen]);

    // Закрываем модалку, если кликнули на фон
    const handleOverlayClick = (e) => {
        if (e.target === e.currentTarget) {
            onClose();
        }
    };

    // Фильтрация городов по поиску
    const filteredCities = cities.filter((city) =>
        city.toLowerCase().includes(search.toLowerCase())
    );

    // Выбор города
    const handleSelectCity = (city) => {
        localStorage.setItem("selectedCity", city);
        onClose();
    };

    // Если модалка закрыта, не рендерим
    if (!isOpen) return null;

    return (
        <div className="modal-overlay" onClick={handleOverlayClick}>
            <div className="modal-content">
                <h2 className="modal-title">Выберите город</h2>

                {/* Используем SearchBar для поиска */}
                <SearchBar search={search} setSearch={setSearch}/>

                {/* Список городов */}
                <div className="city-list" style={{ marginTop: "1rem" }}>
                    {filteredCities.map((city) => (
                        <div
                            key={city}
                            className="city-item"
                            style={{ padding: "0.5rem 0", cursor: "pointer" }}
                            onClick={() => handleSelectCity(city)}
                        >
                            {city}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
