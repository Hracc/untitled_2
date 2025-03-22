import { Logo } from './Logo.jsx';
import { Link } from 'react-router-dom';  // импортируем Link из react-router-dom
import './Header.scss';

export function Header({ onLoginClick }) {
    return (
        <header className="header">
            <div className="container">
                <div className="header__logo">
                    <Link to="/" className="header__logo-link">
                        <Logo />
                    </Link>
                </div>
                <button onClick={onLoginClick} className="header__button">Вход или регистрация</button>
            </div>
        </header>
    );
}
