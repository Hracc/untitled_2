// Скрипт для обработки заявок от организации
import { getWithToken, postWithToken } from "../utils"

const urlStatement = {
    statements : '/api/admin/Connection/all',
    currentStatement: '/api/admin/Organization',
    updateStatus : '/api/admin/Connection/UpdateStatus',
    state: '/api/admin/Connection/status',
}

// Все заявки
export const getStatements = () => {
    return getWithToken(urlStatement.statements)
}

export const getCurStatement = (id) => {
    return getWithToken(`${urlStatement.currentStatement}/${id}`)
}

export const getStatus = (id) => {
    return getWithToken(`${urlStatement.state}?id=${id}`)
}

// Изменить статус
export const postStatus = async (connectionRequestId, status) => {
    const url = `${urlStatement.updateStatus}?connectionRequestId=${encodeURIComponent(connectionRequestId)}&status=${encodeURIComponent(status)}`
    await postWithToken(url, null)
};
