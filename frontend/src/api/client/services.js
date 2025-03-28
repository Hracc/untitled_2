import { getWithToken, getTokenOrThrow } from "../utils";

const urlService = {
    serviceTypes: '/api/Service/ServiceTypes',
    cities: '/api/Service/CITY?city=',
    organizations: '/api/Service/OrganizationByCityAndName?city=&name=',
    serviceDetail: '/api/Service/details',
    serviceRequest: '/api/Service/createRequest'
}

// GET-запросы:
// - типы запросов
export const getServiceTypes = async () => {
    return getWithToken(urlService.serviceTypes);
}

// - города
export const getCities = async () => {
    const response = await fetch(urlService.cities, {
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

// - организации
export const getOrganizations = async () => {
    const city  = localStorage.getItem('selectedCity') || ''
    const url = urlService.organizations.replace('city=', `city=${city}`)
    return getWithToken(url);
}

// POST-запросы:
// - детали сервисов
export const postServiceDetail = async (typeCode) => {
    const params = new URLSearchParams({ typeCode: typeCode.toString()});
    const url = `${urlService.serviceDetail}?${params}`;
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${getTokenOrThrow()}`,
            'Content-Type': 'application/json',
        },
    })

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    } 

    return response.json();
}
// - отправка заявки
export const postRequest = async () => {
    const requestBody= JSON.parse(localStorage.getItem("serviceQuest"))
    const response = await fetch(urlService.serviceRequest, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${getTokenOrThrow()}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    })

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`);
    } 
};

// Функция для заполнения заявки:
// - добавления данных к заявке (JSON-формат)
export const addToLocalStorage = (key, value) => {
    const storedData = localStorage.getItem('serviceQuest');
    console.log(JSON.parse(storedData))
    let data = storedData ? JSON.parse(storedData) : {};

    data[key] = value;
    localStorage.setItem('serviceQuest', JSON.stringify(data));
};

// - запись календаря в заявку
export const addDate = (selectedDateTime) => {
    // Преобразуем в строку в формате ISO
    const isoString = selectedDateTime.toISOString();
    addToLocalStorage("dateTime", isoString);
}