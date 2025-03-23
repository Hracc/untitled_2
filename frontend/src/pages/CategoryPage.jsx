import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { SearchBar } from "../components/SearchBar";
import { Header } from "../components/Header";
import "../styles.scss"; // Импортируем глобальные стили (вместо отдельного CategoryPage.scss)

import { getOrganizations, addToLocalStorage } from "../api/client/services";

export function CategoryPage() {
    const { categoryName } = useParams();
    const [search, setSearch] = useState("");
    const [services, setServices] = useState([]);
    const [currentPage, setCurrentPage] = useState(1); // Состояние для текущей страницы
    const servicesPerPage = 10; // Количество сервисов на одной странице

    useEffect(() => {
        const fetchData = async () => {
            try {
                const organizations = await getOrganizations();
    
                const formattedServices = organizations.map((org, index) => ({
                    id: index + 1,
                    name: org.organizationFullName, 
                    address: `${org.cityName}, ${org.streetName}, ${org.houseNumber}`,
                    city: org.cityName,
                }));
    
                setServices(formattedServices);
            } catch (error) {
                console.error('Ошибка при загрузке данных:', error);
            }
        };
    
        fetchData();
    }, [categoryName]);

    // Фильтрация по названию
    const filteredServices = services.filter((service) => {
        const searchParts = search.toLowerCase().split(" "); 
        const searchCity = searchParts[0] || ""; 
        const searchName = searchParts.slice(1).join(" ") || ""; 

        const matchesCity = service.city.toLowerCase().includes(searchCity);
        const matchesName = service.name.toLowerCase().includes(searchName);

        return matchesCity && matchesName;
    });

    // Вычисление сервисов для текущей страницы
    const indexOfLastService = currentPage * servicesPerPage;
    const indexOfFirstService = indexOfLastService - servicesPerPage;
    const currentServices = filteredServices.slice(indexOfFirstService, indexOfLastService);

    // Обработчики для смены страницы
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    // Вычисляем количество страниц
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(filteredServices.length / servicesPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h1>{categoryName}</h1>
                    <nav className="navigate">
                        <Link to="/">Главная
                        </Link>
                        {" — "}
                        <strong>{categoryName}</strong>
                    </nav>

                    <SearchBar search={search} setSearch={setSearch} />

                    {/* Список сервисов */}
                    {currentServices.map((service) => (
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
                        {pageNumbers.map((number) => (
                            <button
                                key={number}
                                onClick={() => {paginate(number);}}
                                className={currentPage === number ? 'active' : ''}
                            >
                                {number}
                            </button>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}
