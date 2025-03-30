const urlOrganiztion = {
    sendStatement : '/api/organization/create',
    checkStatus : 'api/organization/Status'
}

export const postStatement = (requestBody) => {
    const response = fetch(urlOrganiztion.sendStatement, {
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