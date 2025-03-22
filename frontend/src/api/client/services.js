const urlServiceTypes = '/api/Service/ServiceTypes';

export const getServiceTypes = async () => {
    const token = localStorage.getItem('token');

    if (!token) {
        throw new Error('Токен не найден в localStorage');
    }

    const response = await fetch(urlServiceTypes, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        }
    })
    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    }

    const data = await response.json();
    return data;
}