// src/pages/CategoryPage.jsx
import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { SearchBar } from "../components/SearchBar";
import { Header } from "../components/Header";
import "../styles.scss";

import { getOrganizations, addToLocalStorage } from "../api/client/services";

export function CategoryPage() {
    const { categoryName } = useParams();
    const [search, setSearch] = useState("");
    const [services, setServices] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const servicesPerPage = 10;

    // Читаем город из Local Storage (если пользователь выбрал)
    const selectedCity = localStorage.getItem("selectedCity") || "";

    // Загрузка всех организаций
    const findOrganization = async () => {
        try {
            const organizations = await getOrganizations();
            const formattedServices = organizations.map((org, index) => ({
                id: org.organizationId,
                name: org.organizationFullName,
                address: `${org.cityName}, ул. ${org.streetName}, дом ${org.houseNumber}`,
                city: org.cityName,
            }));
            setServices(formattedServices);
        } catch (error) {
            console.error("Ошибка при загрузке данных:", error);
        }
    };

    useEffect(() => {
        findOrganization();
    }, [categoryName]);

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
            <Header />
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
                    {currentServices.map(service => (
                        <Link
                            key={service.id}
                            to={`/${encodeURIComponent(categoryName)}/${encodeURIComponent(service.name)}`}
                            onClick={() => addToLocalStorage("organizationId", service.id)}
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
            </div>
        </div>
    );
}
