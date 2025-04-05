import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { Home } from "./pages/Home";
import { CategoryPage } from "./pages/CategoryPage";
import { ServiceDetailsPage } from "./pages/ServiceDetailsPage";
import { CartPage } from "./pages/CartPage";
import { Profile } from "./pages/Profile";
import { OrganizationProfile } from "./pages/OrganizationProfile";
import { OrganizationRegPage } from "./pages/OrganizationRegPage";
import { RequestsPage } from "./pages/RequestsPage";
import { OrganizationFormPage } from "./pages/OrganizationFormPage";
import { OrganizationRequestPage } from "./pages/OrganizationRequestPage";
import { OrganizationPage } from "./pages/OrganizationPage";
import { AdminPage } from "./pages/AdminPage";
import { AdminProfile } from "./pages/AdminProfile";
import { getUserRole } from "./api/jwt";
// import { ProtectedRoute } from "./pages/Profile.jsx";
import "./styles.scss";

const ProtectedRoute = ({ element, allowedRoles }) => {
    const role = getUserRole();
    
    if (!role) {
        return allowedRoles.includes("guest") ? element : <Navigate to="/" replace />;
    }
    if (!allowedRoles.includes(role)) {
        const defaultRoute = role === "administration" ? "/admin/requests" : "/partner";
        return <Navigate to={defaultRoute} replace />;
    }
    return element;
};

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<ProtectedRoute element={<Home />} allowedRoles={["CUSTOMER", "guest"]} />} />
                <Route path="/:categoryName" element={<ProtectedRoute element={<CategoryPage />} allowedRoles={["CUSTOMER", "guest"]} />} />
                <Route path="/:categoryName/:serviceName" element={<ProtectedRoute element={<ServiceDetailsPage />} allowedRoles={["CUSTOMER", "guest"]} />} />
                <Route path="/cart" element={<ProtectedRoute element={<CartPage />} allowedRoles={["CUSTOMER", "guest"]} />} />
                <Route path="/profile" element={<ProtectedRoute element={<Profile />} allowedRoles={["CUSTOMER"]} />} />
                
                {/* Организации */}
                <Route path="/partner" element={<ProtectedRoute element={<OrganizationPage />} allowedRoles={["guest"]} />} />
                <Route path="/partner/profile" element={<ProtectedRoute element={<OrganizationProfile />} allowedRoles={["ORGANIZATION"]} />} />
                <Route path="/partner/form" element={<ProtectedRoute element={<OrganizationFormPage />} allowedRoles={["ORGANIZATION"]} />} />
                <Route path="/partner/new" element={<ProtectedRoute element={<OrganizationRegPage />} allowedRoles={["guest"]} />} />
                
                {/* Администрация */}
                <Route path="/admin" element={<AdminPage />}/>
                <Route path="/admin/profile" element={<ProtectedRoute element={<AdminProfile />} allowedRoles={["ADMINISTRATION"]} />} />
                <Route path="/admin/requests" element={<ProtectedRoute element={<RequestsPage />} allowedRoles={["ADMINISTRATION"]} />} />
                <Route path="/admin/requests/:regNo" element={<ProtectedRoute element={<OrganizationRequestPage />} allowedRoles={["ADMINISTRATION"]} />} />
            </Routes>
        </Router>
    );
}
