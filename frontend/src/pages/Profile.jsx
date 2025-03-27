import React, { useEffect, useState } from "react";
import { Header } from "../components/Header";
import { SearchBar } from "../components/SearchBar";
import { ServicesList } from "../components/ServicesList";
import { Modal } from "../components/Modal";

import "../styles.scss";

import { getCustomer, putCustomer, deleteCustomer } from "../api/client/customer";

export function Profile() {

    const [customer, setCustomer] = useState(
        {}
    );


    const handleFetchData = async () => {
        try {
            const data = await getCustomer();
            setCustomer(data) 
        } catch (error) {
            console.error('Ошибка при выполнении запроса:', error);
        }
    };
    useEffect(()=> {
        handleFetchData()
    }, [])

    
    const fields = [
        { label: 'Имя', key: 'customerName' },
        { label: 'Фамилия', key: 'customerSurname' },
        { label: 'Отчество', key: 'customerPatronymic' },
        { label: 'Электронная почта', key: 'email' }
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
                                type="text"
                                value={customer[field.key] ?? ""}
                                onChange={(e) => setCustomer({
                                    ...customer,
                                    [field.key]: e.target.value
                                })}
                            />
                        </div>
                    ))}
                    <div className="profile-buttons">
                        <button onClick={async () => {
                            try {
                                await putCustomer(customer);
                            } catch (error) {
                                console.error('Ошибка при выполнении запроса:', error);
                            }
                        }}>
                            Изменить
                        </button>
                        <button
                            onClick={async () => {
                                try {
                                    await deleteCustomer()
                                    localStorage.setItem("token", "")
                                } catch (error) {

                                }
                            }}
                        >Удалить</button>
                    </div>
                </div>
            </div>
        </div>
    );
}