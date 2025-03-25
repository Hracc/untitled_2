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
    const [showCities, setShowCities] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const servicesPerPage = 10;

    const fetchData = async () => {
        try {
            const organizations = await getOrganizations();
            const formattedServices = organizations.map((org, index) => ({
                id: index + 1,
                name: org.organizationFullName, 
                address: `${org.cityName}, ул. ${org.streetName}, дом ${org.houseNumber}`,
                city: org.cityName,
            }));
            setServices(formattedServices);
        } catch (error) {
            console.error('Ошибка при загрузке данных:', error);
        }
    };

    useEffect(() => {
        fetchData();
    }, [categoryName]);

    // Получаем уникальные города
    const cities = [...new Set(services.map(service => service.city))];

    // Обработчик изменения поиска
    const handleSearchChange = (value) => {
        setSearch(value);
        setCurrentPage(1);
        // Если есть пробел - переключаемся на организации
        setShowCities(!value.includes(' '));
    };

    // Фильтрация
    const filteredCities = cities.filter(city => 
        city.toLowerCase().includes(search.toLowerCase())
    );

    const filteredServices = services.filter(service => {
        const [city, ...nameParts] = search.toLowerCase().split(' ');
        const name = nameParts.join(' ');
        
        const matchesCity = city ? service.city.toLowerCase().includes(city) : true;
        const matchesName = name ? service.name.toLowerCase().includes(name) : true;
        
        return matchesCity && matchesName;
    });

    // Пагинация
    const indexOfLastService = currentPage * servicesPerPage;
    const indexOfFirstService = indexOfLastService - servicesPerPage;
    const currentCities = filteredCities.slice(indexOfFirstService, indexOfLastService);
    const currentServices = filteredServices.slice(indexOfFirstService, indexOfLastService);

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

                    <SearchBar search={search} setSearch={handleSearchChange} />

                    {/* Список городов */}
                    {showCities && currentCities.map((city) => (
                        <div
                            key={city}
                            onClick={() => {
                                setCurrentPage(1);
                                setSearch(`${city} `); // Добавляем пробел после города
                                setShowCities(false);
                            }}
                            className="category-service-item"
                            style={{cursor: 'pointer'}}
                        >
                            <div className="service-name">{city}</div>
                        </div>
                    ))}

                    {/* Список организаций */}
                    {!showCities && currentServices.map((service) => (
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
                        {[...Array(Math.ceil(
                            (showCities ? filteredCities.length : filteredServices.length) / servicesPerPage
                        )).keys()].map(number => (
                            <button
                                key={number + 1}
                                onClick={() => paginate(number + 1)}
                                className={currentPage === number + 1 ? 'active' : ''}
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