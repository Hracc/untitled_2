import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Home } from "./pages/Home";
import { CategoryPage } from "./pages/CategoryPage";
import {ServiceDetailsPage} from "./pages/ServiceDetailsPage.jsx"; // Новая страница
import { CartPage } from "./pages/CartPage";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                {/* Маршрут для выбранной категории */}
                <Route path="/:categoryName" element={<CategoryPage />} />
                {/* Маршрут для конкретного автосервиса */}
                <Route path="/:categoryName/:serviceName" element={<ServiceDetailsPage />} />
                <Route path="/cart" element={<CartPage />} />
            </Routes>
        </Router>
    );
}
