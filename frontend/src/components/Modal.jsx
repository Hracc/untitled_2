import { useState, useEffect } from "react";
import "./Modal.scss"; // Подключаем стили для Modal

export function Modal({ isOpen, onClose }) {
    const [isChecked, setIsChecked] = useState(false);
    const [email, setEmail] = useState("");
    const [code, setCode] = useState("");
    const [emailSent, setEmailSent] = useState(false);
    const [emailPlaceholder, setEmailPlaceholder] = useState(" Email");
    const [codePlaceholder, setCodePlaceholder] = useState(" Код из письма");
    const [timer, setTimer] = useState(0);

    useEffect(() => {
        let interval;
        if (timer > 0) {
            interval = setInterval(() => {
                setTimer((prev) => prev - 1);
            }, 1000);
        } else {
            clearInterval(interval);
        }
        return () => clearInterval(interval);
    }, [timer]);

    if (!isOpen) return null;

    const handleOverlayClick = (e) => {
        if (e.target === e.currentTarget) {
            onClose();
        }
    };

    const handleSendCode = () => {
        if (email.trim() !== "" && timer === 0) {
            setEmailSent(true);
            setTimer(60);
        }
    };

    const handleConfirmCode = () => {
        if (code.trim() !== "") {
            onClose(); // Закрываем модалку после успешного ввода кода
        }
    };

    return (
        <div className="modal-overlay" onClick={handleOverlayClick}>
            <div className="modal-content">
                <h2 className="modal-title">Вход или регистрация</h2>

                <input
                    type="email"
                    placeholder={emailPlaceholder}
                    className="modal-input"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    onFocus={() => setEmailPlaceholder("")}
                    onBlur={() => setEmailPlaceholder(" Email")}
                    disabled={emailSent} // Блокируем поле после отправки кода
                />

                {emailSent && (
                    <input
                        type="text"
                        placeholder={codePlaceholder}
                        className="modal-input"
                        value={code}
                        onChange={(e) => setCode(e.target.value)}
                        onFocus={() => setCodePlaceholder("")}
                        onBlur={() => setCodePlaceholder(" Код из письма")}
                    />
                )}

                <div className="modal-checkbox">
                    <input
                        type="checkbox"
                        id="privacy-policy"
                        checked={isChecked}
                        onChange={(e) => setIsChecked(e.target.checked)}
                    />
                    <label htmlFor="privacy-policy" className="modal-checkbox-label">
                        Принимаю условия{" "}
                        <a href="/privacy-policy" className="privacy-link">
                            политики конфиденциальности
                        </a>
                    </label>
                </div>

                {!emailSent ? (
                    <button
                        onClick={handleSendCode}
                        className="modal-send-btn"
                        disabled={!isChecked || email.trim() === ""}
                    >
                        Отправить код
                    </button>
                ) : (
                    <>
                        <button
                            onClick={handleConfirmCode}
                            className="modal-confirm-btn"
                            disabled={code.trim() === ""}
                        >
                            Подтвердить код
                        </button>
                        <button
                            onClick={handleSendCode}
                            className="modal-resend-btn"
                            disabled={timer > 0}
                        >
                            {timer > 0 ? `Запросить код заново (${timer}с)` : "Запросить код заново"}
                        </button>
                    </>
                )}
            </div>
        </div>
    );
}
