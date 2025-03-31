import { useEffect, useState } from "react";
import { Header } from "../components/Header";
import { SearchBar } from "../components/SearchBar";
import { ServicesList } from "../components/ServicesList";
import { Modal } from "../components/Modal";

import { getServiceTypes } from "../api/client/services";
export function Home() {
    const [search, setSearch] = useState("");
    const [isModalOpen, setModalOpen] = useState(false);
    
    const [serviceTypes, setServiceTypes] = useState([]);

    const handleFetchData = async () => {
        try {
            const data = await getServiceTypes();
            setServiceTypes(data)            
        } catch (error) {
            console.error('Ошибка при выполнении запроса:', error);
        }
    };

    useEffect(()=> {
        handleFetchData();
    }, [])

    
    return (
        <div>
            <Header onLoginClick={() => setModalOpen(true)} />
            <div className="container"> {/* Все остальное в контейнере */}
                <div className="content">
                    <h1>Все автоуслуги в одном месте.</h1>
                    <h5>Мы ищем - вы выбираете!</h5>
                    <SearchBar search={search} setSearch={setSearch} />
                    <h3>Поручите дела специалистам</h3>
                    <ServicesList services={serviceTypes} search={search} />
                </div>
                <Modal isOpen={isModalOpen} onClose={() => setModalOpen(false)} />

            </div>

        </div>
    );
}