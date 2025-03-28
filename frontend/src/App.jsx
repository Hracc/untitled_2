import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Home } from "./pages/Home";
import { CategoryPage } from "./pages/CategoryPage";
import { ServiceDetailsPage } from "./pages/ServiceDetailsPage";
import { CartPage } from "./pages/CartPage";
import {Profile} from "./pages/Profile.jsx";
import "./styles.scss";


export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/:categoryName" element={<CategoryPage />} />
                <Route path="/:categoryName/:serviceName" element={<ServiceDetailsPage />} />
                <Route path="/cart" element={<CartPage />} />
                {/* Дополнительный маршрут для профиля */}
                <Route path="/profile" element={<Profile />} />
            </Routes>
        </Router>
    );
}
