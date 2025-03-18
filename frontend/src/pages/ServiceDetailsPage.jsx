// src/pages/ServiceDetailsPage.jsx

import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { Header } from "../components/Header";
import {SearchBar} from "../components/SearchBar.jsx";
import "../styles.scss";


export function ServiceDetailsPage() {
    // Из URL получаем и категорию, и название сервиса:
    const { categoryName, serviceName } = useParams();

    // Данные об автосервисе (название, адрес, категория)
    const [serviceData, setServiceData] = useState(null);
    // Список услуг
    const [offers, setOffers] = useState([]);
    // Выбранные услуги (ID-шники)
    const [selectedOffers, setSelectedOffers] = useState([]);

    // Загружаем (или мокаем) данные при монтировании
    useEffect(() => {
        // Можно сделать fetch(`/api/services/${categoryName}/${serviceName}`)
        // но пока заглушка:
        setServiceData({
            id: 1,
            name: serviceName,
            address: "Московское шоссе, 176",
            category: categoryName
        });

        setOffers([
            { id: 101, name: "Комплексная мойка", price: 2000, time: "15 мин" },
            { id: 102, name: "Чистка салона", price: 2000, time: "15 мин" },
            { id: 103, name: "Мойка кассеты радиаторов", price: 2000, time: "15 мин" },
            { id: 104, name: "Мойка днища", price: 2000, time: "15 мин" },
            { id: 105, name: "Удаление водного камня", price: 2000, time: "15 мин" },
        ]);
    }, [categoryName, serviceName]);

    // Переключение выбранной услуги
    const toggleOffer = (offerId) => {
        setSelectedOffers((prev) =>
            prev.includes(offerId)
                ? prev.filter((id) => id !== offerId)
                : [...prev, offerId]
        );
    };

    // Подсчет итоговой суммы
    const totalPrice = offers
        .filter((o) => selectedOffers.includes(o.id))
        .reduce((sum, o) => sum + o.price, 0);

    if (!serviceData) {
        return <div>Загрузка...</div>;
    }

    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h1 style={{ margin: "0.25rem 0" }}>{serviceData.name}</h1>
                    <h5>{serviceData.address}</h5>
                    <nav className="navigate" style={{ margin: "2rem 0" }} >
                        <Link to={`/${encodeURIComponent(serviceData.category)}`}>
                            {serviceData.category}
                        </Link>
                        {" — "}
                        <strong>{serviceData.name}</strong>
                    </nav>

                    {/* Итоговая сумма + ссылка на корзину */}
                    <div className="offers-total">
                        <span>
                        Итого: <strong>{totalPrice} RUB</strong>{" "}
                        </span>
                        <Link to="/cart" className="cart-link">
                            Корзина
                        </Link>
                    </div>

                    <div className="offers-list">
                        {offers.map((offer) => {
                            const isSelected = selectedOffers.includes(offer.id);
                            return (
                                <div key={offer.id} className="offer-item">
                                    {/* Кастомный чекбокс */}
                                    <label className="offer-checkbox">
                                        <input
                                            type="checkbox"
                                            checked={isSelected}
                                            onChange={() => toggleOffer(offer.id)}
                                        />
                                        <span className="custom-check"></span>
                                    </label>

                                    {/* Название и цена услуги */}
                                    <div className="offer-info">
                                        <div className="offer-name">{offer.name}</div>
                                        <div className="offer-price">
                                            {offer.price} RUB, {offer.time}
                                        </div>
                                    </div>
                                </div>
                            );
                        })}
                    </div>

                </div>
            </div>
        </div>
    );
}
