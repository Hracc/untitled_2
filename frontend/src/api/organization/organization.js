import { getWithToken } from '../utils'
const urlOrganiztion = {
    sendStatement : '/api/organization/create',
    checkStatus : '/api/organization/Status',
    organization: '/api/organization/search',
}

// Подать заявку
export const postStatement = async (requestBody) => {
    const response = await fetch(urlOrganiztion.sendStatement, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    })

    if (!response.ok) {
        throw new Error(`Ошибка HTTP: ${response.status}`)
    }
}

export const getStatus = () => {
    return getWithToken(urlOrganiztion.checkStatus)
}

export const getOrganization = () => {
    return getWithToken(urlOrganiztion.organization)
}