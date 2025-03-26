
const urlCustomer = {
    customerSearch: '/api/customer/search',
    customerEdit: '/api/customer/edit',
    customerDelete: '/api/customer/delete'
}

export const getCustomer = async () => {
    const token = localStorage.getItem('token');

    if (!token) {
        throw new Error('Токен не найден в localStorage');
    }

    const response = await fetch(urlCustomer.customerSearch, {
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

export const putCustomer = async (requestBody) => {
    const token = localStorage.getItem('token');

    if (!token) {
        throw new Error('Токен не найден в localStorage');
    }

    const response = await fetch(urlCustomer.customerEdit, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    })
}

export const deleteCustomer = async () => {
    const token = localStorage.getItem('token');

    if (!token) {
        throw new Error('Токен не найден в localStorage');
    }

    const response = await fetch(urlCustomer.customerDelete, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}