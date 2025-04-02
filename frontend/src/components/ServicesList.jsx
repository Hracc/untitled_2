import { Link } from "react-router-dom";
import './ServicesList.scss';

import { addLocalJSON } from "../api/utils";
import {serviceItem} from "../api/client/services";

export function ServicesList({ services, search }) {
    return (
        <div className="services-list">
            {services
                .filter((s) => s.typeName.toLowerCase().includes(search.toLowerCase()))
                .map((s) => (
                    <Link
                        key={s.typeCode}
                        to={`/${encodeURIComponent(s.typeName)}`}
                        className="service-item"
                        onClick={() => {addLocalJSON(serviceItem.selectedData,'serviceTypeCode', s.typeCode);}}
                    >
                        {s.typeName}
                    </Link>
                ))}
        </div>
    );
}
