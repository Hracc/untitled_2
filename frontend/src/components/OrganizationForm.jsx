import { useState } from "react";
import "./OrganizationForm.scss";

import { postStatement } from "../api/organization/organization";

export function OrganizationForm({ readOnly = false }) {
    const [fullName, setFullName] = useState("");
    const [shortName, setShortName] = useState("");
    const [inn, setInn] = useState("");
    const [kpp, setKpp] = useState("");
    const [ogrn, setOgrn] = useState("");
    const [surname, setSurname] = useState("");
    const [name, setName] = useState("");
    const [patronymic, setPatronymic] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [additionalInfo, setAdditionalInfo] = useState("");
    const [acceptedPolicy, setAcceptedPolicy] = useState(false);

    const fieldLengths = {
        inn: 10,
        kpp: 9,
        ogrn: 13,
        phone: 20
    }

    const handleNumberInput = (value, setFunction, field) => {
        const maxLength = fieldLengths[field]
        if (/^\d*$/.test(value) && value.length <= maxLength) {
            setFunction(value)
        }
    }
    

    // Проверяем, заполнены ли все обязательные поля
    const isFormValid = () => {
        // Все обязательные поля (кроме additionalInfo) и чекбокс
        return (
            fullName.trim() !== "" &&
            shortName.trim() !== "" &&
            inn.length === fieldLengths.inn &&
            kpp.length === fieldLengths.kpp &&
            ogrn.length === fieldLengths.ogrn &&
            surname.trim() !== "" &&
            name.trim() !== "" &&
            email.trim() !== "" &&
            phone.length >= 11
        );
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!isFormValid()) return;
    
        // Собираем данные формы в нужный формат JSON
        const requestBody = {
            organizationFullName: fullName,
            organizationShortName: shortName,
            inn: inn,
            kpp: kpp,
            ogrn: ogrn,
            responsiblePersonSurname: surname,
            responsiblePersonName: name,
            responsiblePersonPatronymic: patronymic,
            responsiblePersonEmail: email,
            responsiblePersonPhoneNumber: phone,
            addInfo: additionalInfo
        };
    
        try {
            // Отправка данных с использованием postStatement
            console.log(requestBody)
            await postStatement(requestBody);
            console.log("Данные успешно отправлены");
        } catch (error) {
            console.error("Ошибка при отправке данных:", error);
        }
    };



    return (
        <form className="organization-form" onSubmit={handleSubmit}>
            <h3>Наименование организации:</h3>

            {/* Полное наименование */}
            <label>
                Полное
                <input
                    type="text"
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* Краткое */}
            <label>
                Краткое
                <input
                    type="text"
                    value={shortName}
                    onChange={(e) => setShortName(e.target.value)}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* ИНН */}
            <label>
                ИНН
                <input
                    type="text"
                    value={inn}
                    onChange={(e) => handleNumberInput(e.target.value, setInn, "inn")}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* КПП */}
            <label>
                КПП
                <input
                    type="text"
                    value={kpp}
                    onChange={(e) => handleNumberInput(e.target.value, setKpp, "kpp")}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* ОГРН */}
            <label>
                ОГРН
                <input
                    type="text"
                    value={ogrn}
                    onChange={(e) => handleNumberInput(e.target.value, setOgrn, "ogrn")}
                    required
                    disabled={readOnly}
                />
            </label>

            <h3>Контактное лицо:</h3>

            {/* Имя */}
            <label>
                Имя
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* Фамилия */}
            <label>
                Фамилия
                <input
                    type="text"
                    value={surname}
                    onChange={(e) => setSurname(e.target.value)}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* Отчество */}
            <label>
                Отчество
                <input
                    type="text"
                    value={patronymic}
                    onChange={(e) => setPatronymic(e.target.value)}

                    disabled={readOnly}
                />
            </label>

            {/* Email */}
            <label>
                Email
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    disabled={readOnly}
                />
            </label>

            {/* Телефон */}
            <label>
                Телефон
                <input
                    type="tel"
                    value={phone}
                    onChange={(e) => {
                        const value = e.target.value.replace(/[^0-9+\-()\s]/g, "");
                        if (value.replace(/\D/g, "").length <= fieldLengths.phone) {
                            setPhone(value);
                        }
                    }}
                    pattern="[\d\s\-\+\(\)]{0,20}"
                    required
                    disabled={readOnly}
                />
            </label>
            {/* Дополнительная информация (необязательное) */}
            <label>
                Доп. Информация
                <textarea
                    value={additionalInfo}
                    onChange={(e) => setAdditionalInfo(e.target.value)}
                    rows={3}
                    disabled={readOnly}
                />
            </label>


            {!readOnly && (
                <button type="submit" 
                disabled={!isFormValid()}
                >
                    Отправить
                </button>
            )}
        </form>
    );
}
