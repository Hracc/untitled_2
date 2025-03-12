import './ServicesList.scss';

export function ServicesList({ services, search }) {
    return (
        <div className="services-list">
            {services.filter((s) => s.toLowerCase().includes(search.toLowerCase())).map((s, i) => (
                <a key={i} href="#" className="service-item">{s}</a>
            ))}
        </div>
    );
}
