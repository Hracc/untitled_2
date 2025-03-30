const urlAuthorization = {
    clientSendEmail: '/api/auth/send-code',
    clientVerify: '/api/auth/verify',

    adminSendEmail: '/api/auth/admin/verify',
    adminVerify: '/api/auth/admin/verify',

    organizationSendEmail: '/api/auth/Organization/verify',
    organizationVerify: '/api/auth/Organization/verify',
}

export const postClientEmail = (email) => {
    return postSendEmail(urlAuthorization.clientSendEmail, email)
}
export const postClientVerify = (email, code) => {
    return postVerify(urlAuthorization.clientVerify, email, code)
}

export const postAdminEmail = (email) => {
    return postSendEmail(urlAuthorization.adminSendEmail, email)
}
export const postAdminVerify = (email, code) => {
    return postVerify(urlAuthorization.adminVerify, email, code)
}

export const postOrganizationEmail = (email) => {
    return postSendEmail(urlAuthorization.organizationSendEmail, email)
}
export const postOrganizationVerify = (email, code) => {
    return postVerify(urlAuthorization.organizationVerify, email, code)
}

const postSendEmail = async (url,email) => {
    const response = await fetch(`${url}?${new URLSearchParams({ email })}`, {
        method: 'POST',
    });
    if (!response.ok) {
        throw new Error(`Ошибка при отправке email. Статус: ${response.status}`);
    }
    setCookie("token",result)
};

const postVerify = async (url, email, code) => {
const requestBody= {email: email, code: code}
const response = await fetch(url, {
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