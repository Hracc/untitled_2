const urlAuthorization = {
    sendEmail: '/api/auth/send-code',
    verifyCode: '/api/auth/verify',
}

export const postSendCode = async (email) => {
    try {
        const response = await fetch(`${urlAuthorization.sendEmail}?${new URLSearchParams({ email })}`, {
            method: 'POST',
        });

        if (!response.ok) {
            throw new Error(`Ошибка при отправке email. Статус: ${response.status}`);
        }
    } catch (error) {
        throw new Error(error.message || "Ошибка сети. Проверьте соединение.");
    }
};

export const postVerify = async (email, code) => {
    const requestBody= {email: email, code: code}
    const response = await fetch(urlAuthorization.verifyCode, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    });

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    }

    const result = await response.text();
    return result;
}