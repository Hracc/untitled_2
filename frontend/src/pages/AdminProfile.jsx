import React, { useEffect, useState } from "react";
import { Header } from "../components/Header";
import { getAdminInfo } from "../api/administration/administration";
import "../styles.scss";

export function AdminProfile() {

    const [admin, setAdmin] = useState(
        {}
    );


    const handleFetchData = async () => {
        try {
            const data = await getAdminInfo();
            setAdmin(data) 
        } catch (error) {
            console.error('Ошибка при выполнении запроса:', error);
        }
    };
    useEffect(()=> {
        handleFetchData()
    }, [])

    
    const fields = [
        { label: 'Электронная почта', key: 'aggregatorSpecialistsEmail' }
    ];
    
    return (
        <div>
            <Header />
            <div className="container">
                <div className="content">
                    <h1>Мой профиль</h1>

                    {fields.map((field) => (
                        <div className="profile-info" key={field.key}>
                            <h5>{field.label}</h5>
                            <input
                                readOnly
                                type="text"
                                value={admin[field.key] ?? ""}
                                onChange={(e) => setAdmin({
                                    ...admin,
                                    [field.key]: e.target.value
                                })}
                            />
                        </div>
                    ))}
                    <div className="profile-buttons">
                        <button
                            onClick={async () => {
                                localStorage.clear()
                                document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'
                                window.location.href = "/"
                            }}
                        >Выйти</button>
                    </div>
                </div>
            </div>
        </div>
    );
}