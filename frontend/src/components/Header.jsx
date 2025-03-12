import { Logo } from './Logo.jsx';
import './Header.scss';

export function Header({ onLoginClick }) {
    return (
        <header className="header">
            <div className="container">
                <div className="header__logo">
                    <Logo />  {}
                </div>
                <button onClick={onLoginClick} className="header__button">Вход или регистрация</button>
            </div>
        </header>
    );
}
