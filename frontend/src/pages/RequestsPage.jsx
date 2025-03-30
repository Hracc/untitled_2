import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles.scss";
import { Header } from "../components/Header.jsx";

export function RequestsPage() {
    // Фильтр статусов
    const statuses = ["Все", "Новая", "В работе", "Исполнена", "Отклонена"];
    const [filterStatus, setFilterStatus] = useState("Все");

    // Заглушка: данные заявок
    const [requests, setRequests] = useState([]);
    useEffect(() => {
        const sampleRequests = [
            { id: 1, regNo: "001", createdDate: "2025-03-25", shortName: "Авангард", status: "Новая" },
            { id: 2, regNo: "002", createdDate: "2025-03-24", shortName: "АвтоПлюс", status: "В работе" },
            { id: 3, regNo: "003", createdDate: "2025-03-23", shortName: "Моечка", status: "Исполнена" },
            { id: 4, regNo: "004", createdDate: "2025-03-22", shortName: "МирМасел", status: "Отклонена" },
            // Можно добавить дополнительные данные
        ];
        setRequests(sampleRequests);
    }, []);

    // Если выбран фильтр "все", не фильтруем, иначе фильтруем по статусу
    const filteredRequests =
        filterStatus === "Все"
            ? requests
            : requests.filter((req) => req.status === filterStatus);

    const navigate = useNavigate();
    const handleRowClick = (reqId) => {
        navigate(`/request/${reqId}`);
    };

    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h2>Список заявок</h2>
                    <div className="requests-filter">
                        <label htmlFor="status-filter">Фильтр: </label>
                        <select
                            id="status-filter"
                            value={filterStatus}
                            onChange={(e) => setFilterStatus(e.target.value)}
                        >
                            {statuses.map((status, idx) => (
                                <option key={idx} value={status}>
                                    {status}
                                </option>
                            ))}
                        </select>
                    </div>
                    <table className="requests-table">
                        <thead>
                        <tr>
                            <th className="col-reg">Рег. №</th>
                            <th className="col-date">Дата создания</th>
                            <th className="col-short">Краткое наименование</th>
                            <th className="col-status">Статус</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filteredRequests.map((req) => (
                            <tr
                                key={req.id}
                                className="request-row"
                                onClick={() => handleRowClick(req.id)}
                            >
                                <td className="col-reg">{req.regNo}</td>
                                <td className="col-date">{req.createdDate}</td>
                                <td className="col-short">{req.shortName}</td>
                                <td className="col-status">{req.status}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
