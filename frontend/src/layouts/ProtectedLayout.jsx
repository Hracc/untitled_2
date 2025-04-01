import { Outlet, Navigate } from 'react-router-dom';
import { getCookie } from '../api/utils';
export const ProtectedLayout = () => {
  const token = getCookie('token');

  if (!token) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};