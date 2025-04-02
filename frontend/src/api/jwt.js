import { jwtDecode } from "jwt-decode"
import { getCookie } from "./utils";
export const getUserRole = () => {
    const token = getCookie("token")
    
    if (!token) {
      console.log("bruh")
    }
  
    try {
      const decodedToken = jwtDecode(token)
      console.log(decodedToken)
    } catch (error) {
      console.error('Ошибка при декодировании токена:', error);
      return null;
    }
  }