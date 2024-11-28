import './NavigationBar.css'
import {useNavigate} from 'react-router-dom';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFutbol, faUser} from '@fortawesome/free-solid-svg-icons';


export default function NavigationBar({isLoggedIn}) {
    const navigate = useNavigate();
    const user = localStorage.getItem("user");
    const userRoles = user ? JSON.parse(user).roles : null;

    return (
        <nav className="navbar">
            <div className="navbar-left">
                <ul className="navbar-left-links">
                    <li>
                        <FontAwesomeIcon className="logo" icon={faFutbol}/>
                    </li>
                    <li>
                        <a className="homeButton"
                           onClick={() => navigate("/")}>Home</a>
                    </li>
                    <li>
                        <a className="homeButton" onClick={() => navigate("/football-games")}>Play Football</a>
                    </li>
                    <li>
                        <a className="homeButton" onClick={() => navigate("/admin/addmatch")}>Add Match</a>
                    </li>
                </ul>
            </div>
            <div className="navbar-center">
                <a className="website-name" href="/">CodeKickFC</a>
            </div>
            <div className="navbar-right">
                    {isLoggedIn ?
                    (
                        <ul className="navbar-right-links">
                            <li>
                                <FontAwesomeIcon
                                icon={faUser}
                                style={{color: "000000",scale: "2"}}
                                onClick={() => navigate(`/user/${userId}`)}/>
                            </li>
                        </ul>
                    ) :
                    (
                        <ul className="navbar-right-links">
                            <li>
                                <a className="login-button" href="/users/login">Login</a>
                            </li>
                            <li>
                                <a className="register-button" href="/users/register">Register</a>
                            </li>
                        </ul>
                    )}
            </div>
        </nav>
    )
}
