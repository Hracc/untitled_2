import { useState } from "react";
import "./SearchBar.scss";

export function SearchBar({ search, setSearch }) {
    const [placeholder, setPlaceholder] = useState("Поиск");

    return (
        <input
            type="text"
            placeholder={placeholder}
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            onFocus={() => setPlaceholder("")}
            onBlur={() => setPlaceholder(" Поиск")}
            className="search-bar"
        />
    );
}
