import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles.scss";
import { Header } from "../components/Header.jsx";
import { getStatements } from "../api/administration/statements.js";

export function RequestsPage() {
    // Фильтр статусов, включая "Все"
    const statuses = ["Все", "Новая", "В работе", "Исполнена", "Отклонена"];
    const [filterStatus, setFilterStatus] = useState("Все");
    const [requests, setRequests] = useState([]);
    const statements = async () => {
        try {
            const data = await getStatements()
            const formattedData = data.map((item) => ({
                id: item.connectionRequestId,
                regNo: item.regNumber,
                createdDate: item.dateBegin,
                shortName: item.organizationName,
                status: item.status, 
            }))
            setRequests(formattedData)
        } catch (error) {
            console.error("Ошибка при выполнении запроса:", error);
        }
    }
    useEffect(() => {
        statements()
    }, []);

    // Если выбран фильтр "Все", не фильтруем, иначе фильтруем по статусу
    const filteredRequests =
        filterStatus === "Все"
            ? requests
            : requests.filter((req) => req.status === filterStatus);

    const navigate = useNavigate();
    const handleRowClick = (regNo) => {
        // Переход к маршруту /admin/requests/:regNo
        navigate(`/admin/requests/${regNo}`);
    };

    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h2>Список заявок</h2>
                    <div className="requests-filter">
                        <label htmlFor="status-filter"><h3>Фильтр: </h3></label>
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
