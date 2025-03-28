import { Link } from "react-router-dom";
import './ServicesList.scss';

export function ServicesList({ services, search }) {
    const handleServiceSelect = (typeCode) => {
        localStorage.setItem('selectedServiceTypeCode', typeCode);
    };
    return (
        <div className="services-list">
            {services
                .filter((s) => s.typeName.toLowerCase().includes(search.toLowerCase()))
                .map((s) => (
                    <Link
                        key={s.typeCode}
                        to={`/${encodeURIComponent(s.typeName)}`}
                        className="service-item"
                        onClick={() => handleServiceSelect(s.typeCode)}
                    >
                        {s.typeName}
                    </Link>
                ))}
        </div>
    );
}
