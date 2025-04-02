import React from "react";
import { OrganizationForm } from "../components/OrganizationForm";
import {Header} from "../components/Header.jsx";
import "../styles.scss";

export function OrganizationRegPage() {
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h1>Регистрация организации</h1>
                    <OrganizationForm />
                    Нажимая "Отправить" вы принимаете условия
                    <a href="/privacy-policy" target="_blank" rel="noreferrer">
                         политики конфиденциальности</a>
                </div>
            </div>
        </div>
    );
}
