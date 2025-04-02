import { useEffect, useState } from "react";
import { OrganizationForm } from "../components/OrganizationForm";
import {Header} from "../components/Header.jsx";
import "../styles.scss";
import { getStatus, getOrganization } from "../api/organization/organization.js";

export function OrganizationFormPage() {
    const [statement, setStatement] = useState({})
    const [organization, setOrganization] = useState({})
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
            const data = await getStatus()
            if (data.length > 0) {
                const lastStatement = data[data.length - 1]
                setStatement({
                    ...lastStatement,
                    dateBegin: formatDate(lastStatement.dateBegin),
                    dateEnd: formatDate(lastStatement.dateEnd),
                });
            }
        } catch (error) {
            console.error("Ошибка при выполнении запроса:", error);
        }
    }
    const fetchOrganization = async () => {
        try {
            const data = await getOrganization()
            setOrganization(data)
        } catch (error) {
            console.error("Ошибка при выполнении запроса:", error);
        }
    }
    useEffect(()=>{
        fetchData()
        fetchOrganization()
    },[])
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <div className="OrganizationFormHeader">
                        <h1>Форма подключения организации</h1> {/* Вместо "организации" ее название и номер в скобках, к примеру "форма подключения Моечка (#001)" */}
                        <h3>Текущий статус: {statement.status}</h3>
                        <h5>Дата создания: {statement.dateBegin}</h5>
                        <h5>Дата исполнения/отклонения: {statement.dateEnd}</h5>
                    </div>
                    <OrganizationForm readOnly={true} initialData={organization}/>
                </div>
            </div>
        </div>
    );
}