import React, {useState} from "react";
import { OrganizationForm } from "../components/OrganizationForm";
import {Header} from "../components/Header.jsx";
import "../styles.scss";

export function OrganizationRequestPage() {

    const statuses = ["Новая", "В работе", "Исполнена", "Отклонена"];
    const [filterStatus, setFilterStatus] = useState("Все");

    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <div className="OrganizationFormHeader">
                        <h1>Форма подключения организации</h1> {/* Вместо "организации" ее название и номер в скобках, к примеру "форма подключения Моечка (#001)" */}
                        <div className="requests-filter">
                            <label htmlFor="status-filter"><h3>Текущий статус: </h3></label>
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
                            <button> Сохранить </button>
                        </div>
                        <h5>Дата создания: 21.10.2024</h5>
                        <h5>Дата исполнения/отклонения: 25.12.2024</h5>
                    </div>
                    <OrganizationForm readOnly={true}/>
                </div>
            </div>
        </div>
    );
}