import { useEffect, useState } from "react";
import { Header } from "../components/Header";
import { SearchBar } from "../components/SearchBar";
import { ServicesList } from "../components/ServicesList";
import { Modal } from "../components/Modal";

import { getCustomer, putCustomer, deleteCustomer } from "../api/client/customer";

export function Test() {

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
        { label: 'Фамилия', key: 'customerSurname' },
        { label: 'Имя', key: 'customerName' },
        { label: 'Отчество', key: 'customerPatronymic' },
        { label: 'Почта', key: 'email' }
    ];
    
    return (
        <div>
            <h1>Тестирование запросов без страниц</h1>
            <h2>- редактировать пользователя</h2>
            
            {fields.map((field) => (
                <div key={field.key}>
                    <h3>{field.label}</h3>
                    <input
                        type="text"
                        value={customer[field.key] ?? ""}
                        onChange={(e) => setCustomer({
                            ...customer,
                            [field.key]: e.target.value
                        })}
                    />
                    <br /><br />
                </div>
            ))}
    
            <button onClick={async () => {
                try {
                    await putCustomer(customer);
                } catch (error) {
                    console.error('Ошибка при выполнении запроса:', error);
                }
            }}>
                Изменить
            </button>
            <br /> <br />
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
    );
}