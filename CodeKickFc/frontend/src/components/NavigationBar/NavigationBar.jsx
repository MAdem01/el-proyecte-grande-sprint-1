import './NavigationBar.css'

export default function NavigationBar() {
    return (
        <nav className="navbar">
            <div className="navbar-left">
                <ul className="navbar-left-links">
                    <li>
                        <a href="/" className="logo"></a>
                    </li>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        <a href="/football-games">Play Football</a>
                    </li>
                </ul>
            </div>
            <div className="navbar-center">
                <a className="website-name" href="/">CodeKickFC</a>
            </div>
            <div className="navbar-right">
                <ul className="navbar-right-links">
                    <li>
                        <a className="login-button" href="/users/login">Login</a>
                    </li>
                    <li>
                        <a className="register-button" href="/users/register">Register</a>
                    </li>
                </ul>
            </div>
        </nav>
    )
}
