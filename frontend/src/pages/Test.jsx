import React from "react";
import { OrganizationForm } from "../components/OrganizationForm";
import {Header} from "../components/Header.jsx";
import "../styles.scss";


export function TestPage() {
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h1>Регистрация организации</h1>
                    <OrganizationForm />
                </div>
            </div>
        </div>
    );
}
