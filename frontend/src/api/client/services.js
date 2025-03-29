import { getWithToken, getTokenOrThrow, addLocalJSON} from "../utils";

const urlService = {
    serviceTypes: '/api/Service/ServiceTypes',
    cities: '/api/Service/CITY?city=',
    organizations: '/api/Service/OrganizationByCityAndName?city=&name=',
    serviceDetail: '/api/Service/details',
    serviceRequest: '/api/Service/createRequest'
}

export const serviceItem = {
    selectedData : 'selectedData',
    serviceRequest : 'serviceRequest',
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
    const requestBody= JSON.parse(localStorage.getItem(serviceItem.serviceRequest))
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
// - запись календаря в заявку
export const addDate = (selectedDateTime) => {
    // Преобразуем в строку в формате ISO
    const isoString = selectedDateTime.toISOString();
    addLocalJSON(serviceItem.serviceRequest,"dateTime", isoString);
}