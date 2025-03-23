const urlService = {
    serviceTypes: '/api/Service/ServiceTypes',
    organizations: '/api/Service/OrganizationsByCity?city=',
    serviceDetail: '/api/Service/details',
}

const token = localStorage.getItem('token');

// Шаблон GET-запроса
const getDataService = async (url) => {

    if (!token) {
        throw new Error('Токен не найден в localStorage');
    }

    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        }
    })

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    }

    return response.json();
}

// GET-запросы:
// - типы запросов
export const getServiceTypes = async () => {
    return getDataService(urlService.serviceTypes);
}

// - организации
export const getOrganizations = async () => {
    return getDataService(urlService.organizations);
}

// POST-запросы:
// - детали сервисов
export const postServiceDetail = async (typeCode) => {

    if (!token) {
        throw new Error('Токен не найден в localStorage');
    }

    const params = new URLSearchParams({ typeCode: typeCode.toString()});
    const url = `${urlService.serviceDetail}?${params}`;
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
    })

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    } 

    return response.json();
}

// Функция для заполнения заявки:
// - добавления данных к заявке (JSON-формат)
export const addToLocalStorage = (key, value) => {
    const storedData = localStorage.getItem('serviceQuest');
    let data = storedData ? JSON.parse(storedData) : {};

    data[key] = value;

    localStorage.setItem('myData', JSON.stringify(data));
};

// - чтение заявки (JSON-формат)
export const readFromLocalStorage = () => {
    const storedData = localStorage.getItem('myData');
    return storedData ? JSON.parse(storedData) : null;
};
