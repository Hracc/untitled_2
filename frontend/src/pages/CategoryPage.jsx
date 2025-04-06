// src/pages/CategoryPage.jsx
import { useState, useEffect, useCallback } from "react";
import { useParams, Link } from "react-router-dom";
import { SearchBar } from "../components/SearchBar";
import { Header } from "../components/Header";
import "../styles.scss";

import { getOrganizations, serviceItem} from "../api/client/services"
import { addLocalJSON, getLocalJSON } from "../api/utils"
import { Loading } from "../components/Loading"
import { Modal } from "../components/Modal"

export function CategoryPage() {
    const { categoryName } = useParams();
    const [search, setSearch] = useState("");
    const [services, setServices] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [isLoading, setIsLoading] = useState(true)
    const [selectedCity, setSelectedCity] = useState(getLocalJSON(serviceItem.selectedData, "city"))
    const [isModalOpen, setModalOpen] = useState(false)

    const servicesPerPage = 10;

    // Загрузка всех организаций
    const findOrganization = async () => {
        try {
            const organizations = await getOrganizations();
            const formattedServices = organizations.map((org) => ({
                id: org.organizationId,
                name: org.organizationFullName,
                address: `${org.cityName}, ул. ${org.streetName}, дом ${org.houseNumber}`,
                street : `${org.streetName}, ${org.houseNumber}`,
                city: org.cityName,
            }));
            setServices(formattedServices)
        } catch (error) {
            console.error("Ошибка при загрузке данных:", error);
        } finally {
            setIsLoading(false)
        }
    };

    useEffect(() => {
        findOrganization()
    }, [categoryName, selectedCity])

    useEffect(() => {
        const handleStorageChange = () => {
            const newCity = getLocalJSON(serviceItem.selectedData, "city");
            if (newCity !== selectedCity) {
                setSelectedCity(newCity);
                setCurrentPage(1); // Сбрасываем пагинацию
            }
        };

        // Подписываемся на изменения в localStorage
        window.addEventListener('storage', handleStorageChange);
        
        return () => {
            window.removeEventListener('storage', handleStorageChange);
        };
    }, [selectedCity])

    // При изменении поисковой строки
    const handleSearchChange = (value) => {
        setSearch(value);
        setCurrentPage(1);
    };

    // Фильтрация: если есть выбранный город, можем фильтровать по нему
    // + по названию организации (если в search нет пробела)
    const filteredServices = services.filter(service => {
        // Если есть выбранный город, проверяем совпадение
        const cityMatch = selectedCity
            ? service.city.toLowerCase() === selectedCity.toLowerCase()
            : true;

        // Поиск по названию (если пользователь ввёл текст)
        const nameMatch = search
            ? service.name.toLowerCase().includes(search.toLowerCase())
            : true;

        return cityMatch && nameMatch;
    });

    // Пагинация
    const indexOfLastService = currentPage * servicesPerPage;
    const indexOfFirstService = indexOfLastService - servicesPerPage;
    const currentServices = filteredServices.slice(indexOfFirstService, indexOfLastService);

    const pageCount = Math.ceil(filteredServices.length / servicesPerPage);

    // Смена страницы
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div>
            <Header onLoginClick={() => setModalOpen(true)} />
            <div className="container">
                <div className="content">
                    <h1>{categoryName}</h1>
                    <nav className="navigate">
                        <Link to="/">Главная</Link>
                        {" — "}
                        <strong>{categoryName}</strong>
                    </nav>

                    {/* Поисковая строка */}
                    <SearchBar search={search} setSearch={handleSearchChange} />

                    {/* Список организаций */}
                    {isLoading ? <Loading/> :currentServices.map(service => (
                        <Link
                            key={service.id}
                            to={`/${encodeURIComponent(categoryName)}/${encodeURIComponent(service.name)}`}
                            onClick={() => {
                                addLocalJSON(serviceItem.selectedData, "street", service.street)
                                addLocalJSON(serviceItem.serviceRequest,"organizationId", service.id)}}
                            className="category-service-item"
                        >
                            <div className="service-name">{service.name}</div>
                            <div className="service-address">{service.address}</div>
                        </Link>
                    ))}

                    {/* Пагинация */}
                    <div className="pagination">
                        {[...Array(pageCount).keys()].map(number => (
                            <button
                                key={number + 1}
                                onClick={() => paginate(number + 1)}
                                className={currentPage === number + 1 ? "active" : ""}
                            >
                                {number + 1}
                            </button>
                        ))}
                    </div>
                </div>
                <Modal isOpen={isModalOpen} onClose={() => setModalOpen(false)}></Modal>
            </div>
        </div>
    );
}
