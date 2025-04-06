import React, {useState, useEffect} from "react";
import { OrganizationForm } from "../components/OrganizationForm";
import { useParams } from "react-router-dom";
import {Header} from "../components/Header.jsx";
import "../styles.scss";

import { getCurStatement, postStatus, getStatus } from "../api/administration/statements.js";
export function OrganizationRequestPage() {
    const { regNo } = useParams();
    const statuses = ["Новая", "В работе", "Исполнена", "Отклонена"];
    const [filterStatus, setFilterStatus] = useState("Новая");
    const [statement, setStatement] = useState({})
    const [status, setStatus] = useState({})
    const formatDate = (dateString) => {
        if (!dateString) return ""
        const date = new Date(dateString)
        return date.toLocaleDateString("ru-RU", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        })
    }
    const fetchData = async () => {
        try {
            const data = await getCurStatement(regNo)
            if (data) {
                setStatement(data);
            }
        } catch (error) {
            console.error("Ошибка при выполнении запроса:", error);
        }
    }

    const fetchStatus = async () => {
        try {
            const status = await getStatus(regNo);
            if (status) {
                setStatus({
                    ...status,
                    date: formatDate(status.date), 
                    dateExecution: formatDate(status.dateExecution)
                });
                setFilterStatus(status.status);
            }
        } catch (error) {
            console.error("Ошибка при получении статуса:", error);
        }
    }
    useEffect(()=>{
        fetchData()
        fetchStatus()
    },[regNo])
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <div className="OrganizationFormHeader">
                        <h1>Форма подключения организации</h1> {/* Вместо "организации" ее название и номер в скобках, к примеру "форма подключения Моечка (#001)" */}
                        <div className="requests-filter">
                            <label htmlFor="status-filter"><h3>Текущий статус:</h3></label>
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
                            <button className="confirm-button"
                            onClick={
                                async() => {
                                try {
                                    await postStatus(regNo, filterStatus)
                                    window.alert("Статус изменён")
                                    window.location.replace("/admin/requests")
                                } catch (error) {
                                }
                            }}
                            > Сохранить </button>
                        </div>
                        <h5>Дата создания: {status.date}</h5>
                        <h5>Дата исполнения/отклонения: {status.dateExecution}</h5>
                    </div>
                    <OrganizationForm readOnly={true} initialData={statement}/>
                </div>
            </div>
        </div>
    );
}