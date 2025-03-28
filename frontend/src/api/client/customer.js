import { getTokenOrThrow } from "../utils";

const urlCustomer = {
    customerSearch: '/api/customer/search',
    customerEdit: '/api/customer/edit',
    customerDelete: '/api/customer/delete'
}

export const getCustomer = async () => {


    const response = await fetch(urlCustomer.customerSearch, {
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

export const putCustomer = async (requestBody) => {

    const response = await fetch(urlCustomer.customerEdit, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${getTokenOrThrow()}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    })
}

export const deleteCustomer = async () => {
    const response = await fetch(urlCustomer.customerDelete, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${getTokenOrThrow()}`,
        },
    })
    document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'
    window.location.href = "/"
}