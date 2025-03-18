import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { SearchBar } from "../components/SearchBar";
import { Header } from "../components/Header";
import "../styles.scss"; // Импортируем глобальные стили (вместо отдельного CategoryPage.scss)

export function CategoryPage() {
    const { categoryName } = useParams();
    const [search, setSearch] = useState("");
    const [services, setServices] = useState([]);
    const [currentPage, setCurrentPage] = useState(1); // Состояние для текущей страницы
    const servicesPerPage = 10; // Количество сервисов на одной странице

    useEffect(() => {
        // Пример запроса в БД или API:
        // fetch(`/api/services?category=${encodeURIComponent(categoryName)}`)
        //   .then((res) => res.json())
        //   .then((data) => setServices(data))
        //   .catch((err) => console.error(err));

        // Пока что - заглушка
        setServices([
            { id: 1, name: "Авангард", address: "Московское шоссе, 176" },
            { id: 2, name: "АвтоАдмирал", address: "ул.Рыбинская, 55" },
            { id: 3, name: "Автопилот", address: "ул.Ясеневая, 7" },
            { id: 4, name: "АвтоСПА", address: "Походный проезд, 10" },
            { id: 5, name: "АвтоГарант", address: "проспект Мира, 101" },
            { id: 6, name: "АвтоЛюкс", address: "ул. Советская, 20" },
            { id: 7, name: "БайкалАвто", address: "ул. Центральная, 5" },
            { id: 8, name: "ТехноКар", address: "пер. Лесной, 8" },
            { id: 9, name: "МастерМотор", address: "ул. Гоголя, 17" },
            { id: 10, name: "АвтоДрайв", address: "ул. Кирова, 3" },
            { id: 11, name: "Скорость", address: "проспект Ленина, 45" },
            { id: 12, name: "АвтоСервис+", address: "ул. Парковая, 11" },
            { id: 13, name: "ГлобусАвто", address: "ул. Пионерская, 14" },
            { id: 14, name: "Форсаж", address: "ул. Транспортная, 6" },
            { id: 15, name: "ТурбоМастер", address: "ул. Заводская, 22" },
            { id: 16, name: "АвтоГуру", address: "ул. Академическая, 9" },
            { id: 17, name: "ТехАвто", address: "ул. Строителей, 30" },
            { id: 18, name: "МоторЛайн", address: "ул. Космонавтов, 7" },
            { id: 19, name: "СервисАвто", address: "ул. Ленина, 99" },
            { id: 20, name: "Пит-Стоп", address: "ул. Высотная, 19" },
            { id: 21, name: "ЭлитАвто", address: "ул. Северная, 5" },
            { id: 22, name: "Вихрь", address: "ул. Южная, 12" },
            { id: 23, name: "Формула-1", address: "ул. Гаражная, 8" },
            { id: 24, name: "ГарантСервис", address: "ул. Новая, 77" },
            { id: 25, name: "ДрайвАвто", address: "ул. Озерная, 6" }
        ]);
    }, [categoryName]);

    // Фильтрация по названию
    const filteredServices = services.filter((service) =>
        service.name.toLowerCase().includes(search.toLowerCase())
    );

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
                                onClick={() => paginate(number)}
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
