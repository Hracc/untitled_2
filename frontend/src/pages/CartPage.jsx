import { useLocation, Link } from "react-router-dom";
import { Header } from "../components/Header";
import "../styles.scss";
import Calendar from "../components/Calendar.jsx";
import { useEffect } from "react";
import React, { useState } from "react";

import { serviceItem, postRequest } from "../api/client/services.js";
import { addLocalJSON } from "../api/utils.js";

export function CartPage() {
    const location = useLocation();
    const cartData = location.state || {
        serviceName: "",
        address: "",
        category: "",
        offers: [],
    };

    const [selectedDateTime, setSelectedDateTime] = useState(null);

    const { serviceName, address, category, offers } = cartData;

    const totalPrice = offers.reduce((sum, o) => sum + o.price, 0);
    const totalTime = offers.reduce((sum, o) => {
        const numericTime = parseInt(o.time);
        return sum + (isNaN(numericTime) ? 0 : numericTime);
    }, 0);
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h1>Подтверждение заказа</h1>

                    {/* Хлебные крошки */}
                    <nav className="navigate">
                        <Link to={`/${encodeURIComponent(category)}/${encodeURIComponent(serviceName)}`}>
                            <strong>{serviceName}</strong>
                            {"   ("}{address}{")"}
                        </Link>
                        {/*{" — "}*/}
                        {/*<strong>Корзина</strong>*/}
                    </nav>

                    {/* Таблица услуг */}
                    <table className="cart-table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Наименование услуги</th>
                            <th>Цена</th>
                            <th>Время</th>
                        </tr>
                        </thead>
                        <tbody>
                        {offers.map((o, i) => (
                            <tr key={o.id}>
                                <td>{i + 1}</td>
                                <td>{o.name}</td>
                                <td>{o.price} RUB</td>
                                <td>{o.time}</td>
                            </tr>
                        ))}
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colSpan="2" className="cart-total">Итого:</td>
                            <td className="cart-total">{totalPrice} RUB</td>
                            <td className="cart-total">{totalTime} мин</td>
                        </tr>
                        </tfoot>
                    </table>

                    {/* Кнопка подтверждения */}
                    <div className="cart-confirm">
                        <Calendar
                            onDateTimeSelect={setSelectedDateTime}
                        />
                        <button className="confirm-button" onClick={async () => {
                            if (!selectedDateTime) {
                                alert("Выберите время");
                                return;
                            }
                            try {
                                addLocalJSON(serviceItem.serviceRequest,"addInfo", "string_front")
                                const response = await postRequest()
                                alert("Заказ подтверждён!")
                        } catch (error){
                            alert("Выбранное время занято! Выберите другое.")
                        }
                        
                        }}>
                            Подтвердить заказ
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
