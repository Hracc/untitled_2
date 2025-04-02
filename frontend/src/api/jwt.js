import { jwtDecode } from "jwt-decode"
import { getCookie } from "./utils";
export const getUserRole = () => {
    const token = getCookie("token")
    
    if (!token) {
      return null
    }
  
    try {
      const decodedToken = jwtDecode(token)
      return decodedToken.role
    } catch (error) {
      console.error('Ошибка при декодировании токена:', error);
      return null;
    }
  }