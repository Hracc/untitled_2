import { useState } from "react";
import "./OrganizationForm.scss";

export function OrganizationForm() {
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

    // Проверяем, заполнены ли все обязательные поля
    const isFormValid = () => {
        // Все обязательные поля (кроме additionalInfo) и чекбокс
        return (
            fullName.trim() !== "" &&
            shortName.trim() !== "" &&
            inn.trim() !== "" &&
            kpp.trim() !== "" &&
            ogrn.trim() !== "" &&
            surname.trim() !== "" &&
            name.trim() !== "" &&
            email.trim() !== "" &&
            phone.trim() !== "" &&
            acceptedPolicy
        );
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!isFormValid()) return;

        // Сбор данных формы
        const formData = {
            fullName,
            shortName,
            inn,
            kpp,
            ogrn,
            contact: {
                surname,
                name,
                patronymic,
                email,
                phone
            },
            additionalInfo
        };

        console.log("Отправка формы:", formData);
        // Здесь можно вызвать API
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
                />
            </label>

            {/* ИНН */}
            <label>
                ИНН
                <input
                    type="text"
                    value={inn}
                    onChange={(e) => setInn(e.target.value)}
                    required
                />
            </label>

            {/* КПП */}
            <label>
                КПП
                <input
                    type="text"
                    value={kpp}
                    onChange={(e) => setKpp(e.target.value)}
                    required
                />
            </label>

            {/* ОГРН */}
            <label>
                ОГРН
                <input
                    type="text"
                    value={ogrn}
                    onChange={(e) => setOgrn(e.target.value)}
                    required
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
                />
            </label>

            {/* Отчество */}
            <label>
                Отчество
                <input
                    type="text"
                    value={patronymic}
                    onChange={(e) => setPatronymic(e.target.value)}
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
                />
            </label>

            {/* Телефон */}
            <label>
                Телефон
                <input
                    type="tel"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    required
                />
            </label>

            {/* Дополнительная информация (необязательное) */}
            <label>
                Доп. Информация
                <textarea
                    value={additionalInfo}
                    onChange={(e) => setAdditionalInfo(e.target.value)}
                    rows={3}
                />
            </label>

            <div className="policy-row">
                <input
                    type="checkbox"
                    id="privacy-policy"
                    checked={acceptedPolicy}
                    onChange={(e) => setAcceptedPolicy(e.target.checked)}
                    required
                />
                <label htmlFor="privacy-policy">
                    Принимаю условия{" "}
                    <a href="/privacy-policy" target="_blank" rel="noreferrer">
                        политики конфиденциальности
                    </a>
                </label>
            </div>

            <button type="submit" disabled={!isFormValid()}>
                Отправить
            </button>
        </form>
    );
}
