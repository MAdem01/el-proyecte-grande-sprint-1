import './NavigationBar.css';
import {useNavigate} from 'react-router-dom';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faFutbol, faUser} from '@fortawesome/free-solid-svg-icons';
import {useEffect, useState} from 'react';


export default function NavigationBar() {
    const navigate = useNavigate();
    const [userInfo, setUserInfo] = useState(null);

    useEffect(() => {

        async function fetchUserData() {
            if (localStorage.getItem('user')) {
                const data = await fetch("/auth/user/me", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${JSON.parse(localStorage.getItem("user")).jwt}`
                    }
                });
                const info = await data.json();
                console.log(info);
                setUserInfo(info);
            }
        }

        fetchUserData();


    }, [location.pathname]);

    function handleLogOut() {
        navigate("/")
        localStorage.clear()
        setUserInfo(null)
    }

    function isAdmin() {
        if (userInfo && userInfo.authorities && userInfo.authorities) {
            for (const role of userInfo.authorities) {
                if (role.authority === "ROLE_ADMIN") {
                    return true
                }
            }
        }
        return false
    }

    return (
        <nav className="navbar">
            <div className="navbar-left">
                <ul className="navbar-left-links">
                    <li>
                        <FontAwesomeIcon className="logo" icon={faFutbol}/>
                    </li>
                    <li>
                        <a className="homeButton" onClick={() => navigate("/")}>Home</a>

                    </li>
                    <li>
                        <a className="homeButton" onClick={() => navigate("/football-games")}>Play Football</a>
                    </li>
                    {isAdmin() &&
                        <li>
                            <a className="homeButton" onClick={() => navigate("/admin/addmatch")}>Add Match</a>
                        </li>}
                </ul>
            </div>
            <div className="navbar-center">
                <a className="website-name" href="/">CodeKickFC</a>
            </div>
            <div className="navbar-right">
                {
                    userInfo && userInfo.authorities && userInfo.authorities[0]?.authority === "ROLE_USER" ? (

                        <ul className="navbar-right-links">
                            <li>
                                <FontAwesomeIcon
                                    icon={faUser}
                                    style={{color: "black", transform: "scale(2)"}}
                                    onClick={() => navigate(`/user/${userInfo.username}`)}  // Use `userInfo.username` instead of `userId`
                                />
                            </li>
                            <li>
                                <a className="register-button" onClick={handleLogOut}>Log Out</a>

                            </li>
                        </ul>
                    ) : (
                        <ul className="navbar-right-links">
                            <li>
                                <a className="login-button" href="/users/login">Login</a>
                            </li>
                            <li>
                                <a className="register-button" href="/users/register">Register</a>
                            </li>
                        </ul>
                    )
                }
            </div>
        </nav>
    );
}
