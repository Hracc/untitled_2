const urlSendEmail = '/api/auth/send-code';
const urlVerify = '/api/auth/verify';

export const postSendCode = async (email) => {
    const response = await fetch(`${urlSendEmail}?${new URLSearchParams({ email })}`, {
        method: 'POST',
    });

    if (!response.ok) throw new Error(`Ошибка при отправке email. Статус: ${response.status}`);
    console.log('Запрос успешно отправлен!');
};

export const postVerify = async (email, code) => {
    const requestBody= {email: email, code: code}
    const response = await fetch(urlVerify, {
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