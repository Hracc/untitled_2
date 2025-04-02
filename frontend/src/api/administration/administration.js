import { getWithToken } from "../utils";
const urlAdmin = {
    info: '/api/admin/search'
}

export const getAdminInfo = () => {
    return getWithToken(urlAdmin.info)
}