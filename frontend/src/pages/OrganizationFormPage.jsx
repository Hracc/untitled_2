import React from "react";
import { OrganizationForm } from "../components/OrganizationForm";
import {Header} from "../components/Header.jsx";
import "../styles.scss";

export function OrganizationFormPage() {
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <div className="OrganizationFormHeader">
                        <h1>Форма подключения организации</h1> {/* Вместо "организации" ее название и номер в скобках, к примеру "форма подключения Моечка (#001)" */}
                        <h3>Текущий статус: Новая</h3>
                        <h5>Дата создания: 21.10.2024</h5>
                        <h5>Дата исполнения/отклонения: 25.12.2024</h5>
                    </div>
                    <OrganizationForm readOnly={true}/>
                </div>
            </div>
        </div>
    );
}