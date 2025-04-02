import React from "react";
import { Link } from "react-router-dom";
import "../styles.scss";

export function NotFoundPage() {
    return (
        <div className="error-page">
            <h1>404</h1>
            <p>Страница не найдена</p>
            <Link to="/">Вернуться на главную</Link>
        </div>
    );
}
