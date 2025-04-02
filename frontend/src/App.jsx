import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Home } from "./pages/Home";
import { CategoryPage } from "./pages/CategoryPage";
import { ServiceDetailsPage } from "./pages/ServiceDetailsPage";
import { CartPage } from "./pages/CartPage";
import {Profile} from "./pages/Profile.jsx";

import { OrganizationRegPage } from "./pages/OrganizationRegPage.jsx"
import { RequestsPage } from "./pages/RequestsPage";
import { TestOrganization } from "./pages_tests/TestOrganization.jsx"
import { OrganizationFormPage } from "./pages/OrganizationFormPage.jsx";
import { OrganizationRequestPage } from "./pages/OrganizationRequestPage.jsx";
import { OrganizationPage } from "./pages/OrganizationPage.jsx";
import { AdminPage } from "./pages/AdminPage.jsx";
import "./styles.scss";





export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/:categoryName" element={<CategoryPage />} />
                <Route path="/:categoryName/:serviceName" element={<ServiceDetailsPage />} />
                <Route path="/cart" element={<CartPage />} />
                <Route path="/profile" element={<Profile />} />
                {/* Организации */}
                <Route path="/partner" element={<OrganizationPage />} />
                <Route path="/partner/form" element={<OrganizationFormPage />} />
                <Route path="/partner/new" element={<OrganizationRegPage />} />
                {/* Администрация */}
                <Route path="/admin" element={<AdminPage />} />
                <Route path="/admin/requests" element={<RequestsPage />} />
                <Route path="/admin/requests/:regNo" element={<OrganizationRequestPage />} />
                {/* Маршруты для тестирования функционала без вёрстки */}
                <Route path="/testO" element={<TestOrganization />} />
            </Routes>
        </Router>
    );
}
