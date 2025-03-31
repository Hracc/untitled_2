// Скрипт для обработки заявок от организации
import { getWithToken, postWithToken } from "../utils"

const urlStatement = {
    statements : '/api/admin/Connection/all',
    updateStatus : '/api/admin/Connection/UpdateStatus'
}

// Все заявки
export const getStatements = () => {
    return getWithToken(urlStatement.statements)
}

// Изменить статус
export const postStatus = async (connectionRequestId, status) => {
    const url = `${urlStatement.updateStatus}?connectionRequestId=${encodeURIComponent(connectionRequestId)}&status=${encodeURIComponent(status)}`
    await postWithToken(url, null)
};
