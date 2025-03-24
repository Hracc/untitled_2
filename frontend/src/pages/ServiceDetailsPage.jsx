import { useState, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { Header } from "../components/Header";
import { Modal } from "../components/Modal";
import "../styles.scss";

import { postServiceDetail, addToLocalStorage} from "../api/client/services.js";

export function ServiceDetailsPage() {
    const { categoryName, serviceName } = useParams();
    const navigate = useNavigate();

    const [serviceData, setServiceData] = useState(null);
    const [offers, setOffers] = useState([]);
    const [selectedOffers, setSelectedOffers] = useState([]);
    const [isModalOpen, setModalOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const organizations = await postServiceDetail(localStorage.getItem('selectedServiceTypeCode'));
    
                const formattedOffers = organizations.map((org, index) => ({
                    id: org.serviceDetailId, 
                    name: `${org.serviceDetailCode} ${org.serviceDetailName}`, 
                    price: org.serviceDetailCost, 
                    time: `${org.serviceDetailDuration} мин`,
                }));
    
                // Обновляем состояние offers
                setOffers(formattedOffers);
            } catch (error) {
                console.error('Ошибка при загрузке данных:', error);
            }
        };
    
        fetchData();
    
        // Устанавливаем статические данные для serviceData
        setServiceData({
            id: 1,
            name: serviceName,
            address: "Московское шоссе, 176",
            category: categoryName,
        });
    }, [categoryName, serviceName]);

    // Переключаем выбор услуги
    const toggleOffer = (offerId) => {
        setSelectedOffers((prev) =>
            prev.includes(offerId)
                ? prev.filter((id) => id !== offerId)
                : [...prev, offerId]
        );
    };

    // Считаем итоговую стоимость выбранных услуг
    const totalPrice = offers
        .filter((o) => selectedOffers.includes(o.id))
        .reduce((sum, o) => sum + o.price, 0);

    // Переход в корзину, передавая все нужные данные через state
    const goToCart = () => {
        if (!serviceData) return;

        // Отбираем выбранные услуги (полные объекты)
        const chosenOffers = offers.filter((o) => selectedOffers.includes(o.id));

        // Формируем объект для корзины
        const cartData = {
            serviceName: serviceData.name,
            address: serviceData.address,
            category: serviceData.category,
            offers: chosenOffers,
        };

        // Переходим на /cart, передавая cartData через state
        navigate("/cart", { state: cartData });
    };

    if (!serviceData) {
        return <div>Загрузка...</div>;
    }


    
    return (
        <div>
            <Header onLoginClick={() => setModalOpen(true)} />
            <div className="container">
                <div className="content">
                    <h1 style={{ margin: "0.25rem 0" }}>{serviceData.name}</h1>
                    <h5>{serviceData.address}</h5>

                    <nav className="navigate" style={{ margin: "2rem 0" }}>
                        <Link to={`/${encodeURIComponent(serviceData.category)}`}>
                            {serviceData.category}
                        </Link>
                        {" — "}
                        <strong>{serviceData.name}</strong>
                    </nav>

                    {/* Кнопка "В корзину" */}
                    <div className="offers-total">
            <span>
              Итого: <strong>{totalPrice} RUB</strong>
            </span>
                        <button onClick={() => {if(selectedOffers.length !== 0) {
                                goToCart();
                                addToLocalStorage("serviceDetailId",selectedOffers)
                                }
                                else{
                                    alert("Выберите хотя бы одну услугу.")
                                    return
                                }
                            }} className="cart-link">
                            Корзина
                        </button>
                    </div>

                    <div className="offers-list">
                        {offers.map((offer) => {
                            const isSelected = selectedOffers.includes(offer.id);
                            return (
                                <div key={offer.id} className="offer-item">
                                    <label className="offer-checkbox">
                                        <input
                                            type="checkbox"
                                            checked={isSelected}
                                            onChange={() => toggleOffer(offer.id)}
                                        />
                                        <span className="custom-check"></span>
                                    </label>
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
                <Modal isOpen={isModalOpen} onClose={() => setModalOpen(false)} />
            </div>
        </div>
    );
}
