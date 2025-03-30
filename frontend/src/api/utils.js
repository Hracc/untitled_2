//Работа с куки
export const setCookie = (name, value, expiresDays = 7) => {
  const expires = new Date(Date.now() + expiresDays * 86400e3).toUTCString();
  document.cookie = `${name}=${value}; expires=${expires}; path=/; Secure; SameSite=Strict`;
  return value;
};

export const getCookie = (name) => {
  const match = document.cookie.match(new RegExp(`${name}=([^;]+)`));
  return match ? decodeURIComponent(match[1]) : null;
};

// Проверка наличия токена
export const getTokenOrThrow = () => {
    const token = getCookie("token")
    if (!token) {
        throw new Error('Токен не найден')
    }
    return token
}

// - GET-запрос, где требуется только токен
export const getWithToken = async (url) => {
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getTokenOrThrow()}`,
        }
    })

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    }

    return response.json();
}

// - POST-запрос, где требуется только токен и JSON тело
export const postWithToken = async (url, requestBody) => {
    const response = fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    })
    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    }
} 
// Работа с localStorage в со JSON значением
export const addLocalJSON = (item, key, value) => {
    const storedData = localStorage.getItem(item)
    let data = storedData ? JSON.parse(storedData) : {}
    data[key] = value
    localStorage.setItem(item, JSON.stringify(data))

}

export const getLocalJSON = (item, key) => {
    const storedData = localStorage.getItem('selectedData')
    const data = storedData ? JSON.parse(storedData) : {}
    return key ? data[key] : data
}