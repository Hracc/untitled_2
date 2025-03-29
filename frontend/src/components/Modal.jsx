import { useState, useEffect } from "react";
import "./Modal.scss"; // Подключаем стили для Modal

import { postSendCode, postVerify } from "../api/authorization";
import { setCookie } from "../api/utils";

export function Modal({ isOpen, onClose }) {
    const [isChecked, setIsChecked] = useState(false);
    const [email, setEmail] = useState("");
    const [code, setCode] = useState("");
    const [emailSent, setEmailSent] = useState(false);
    const [emailPlaceholder, setEmailPlaceholder] = useState(" Email");
    const [codePlaceholder, setCodePlaceholder] = useState(" Код из письма");
    const [timer, setTimer] = useState(0);

    const [error, setError] = useState("");

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

    const validateEmail = (email) => {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email);
    };
    
    const handleSendCode = async () => {
        if (email.trim() === "" || timer !== 0) return;
        if (!validateEmail(email)) {
            setError("Введите корректный email.");
            return;
        }

        try {
            await postSendCode(email);
            setEmailSent(true);
            setTimer(60);
            setError("");
        } catch (error) {
            console.error("Ошибка при отправке email:", error);
            setError("Не удалось отправить код. Попробуйте еще раз.");
        }
    };

    const handleConfirmCode = async () => { // Добавлено async
        if (code.trim() !== "") {
            try {
                const result = await postVerify(email, code); 
                // onClose(); 
                setCookie("token",result)
                window.location.reload()
            } catch (error) {
                console.error("Ошибка при отправке кода:", error);
                setError("Неверный код. Попробуйте еще раз.");
            }
        }
    };

    return (
        <div className="modal-overlay" onClick={handleOverlayClick}>
            <div className="modal-content">
                <h2 className="modal-title">Войти или зарегистрироваться
                </h2>

                <input
                    type="email"
                    placeholder={emailPlaceholder}
                    className="modal-input"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    onFocus={() => setEmailPlaceholder("")}
                    onBlur={() => setEmailPlaceholder(" Email")}
                    maxLength="255"
                    disabled={emailSent} // Блокируем поле после отправки кода
                />

                {emailSent && (
                    <input
                        type="text"
                        placeholder={codePlaceholder}
                        className="modal-input"
                        value={code}
                        onChange={(e) => {
                            const value = e.target.value.replace(/\D/g, "").slice(0, 4)
                            setCode(value)
                        }}
                        onFocus={() => setCodePlaceholder("")}
                        onBlur={() => setCodePlaceholder(" Код из письма")}
                        inputMode="numeric"
                    />
                )}
                {error && <p className="modal-error">{error}</p>}
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
                            disabled={!isChecked || code.trim().length !== 4}
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