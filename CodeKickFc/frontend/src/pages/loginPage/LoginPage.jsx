import "./LoginPage.css"
import InputField from "../../components/InputField/InputField.jsx";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import LoginWithGoogleSvg from "../../assets/images/btn_google_light.svg"

export default function LoginPage() {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isUserNotFound, setIsUserNotFound] = useState(false);

    function handleRegisterClick(e) {
        e.preventDefault();
        navigate("/users/register");
    }

    async function handleSubmit(e) {
        e.preventDefault();

        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({username: username, password: password}),
        })

        if (response.status === 401) {
            setIsUserNotFound(true);
        } else if (response.status === 200) {
            const data = await response.json();
            localStorage.setItem("user", JSON.stringify(data));
            navigate("/");
        }
    }

    function handleRedirection() {
        navigate("/users/register");
    }

    return (
        <div className="pageWrapper">
            <div className="loginFormContainer">
                <form className="loginForm" onSubmit={handleSubmit}>
                    <h1 className="loginFormTitle">Login</h1>
                    <InputField className="loginPageInputField" placeholder="Username" type="text" value={username}
                                onChange={(e) => setUsername(e.target.value)}/>
                    <InputField className="loginPageInputField" placeholder="Password" type="password"
                                value={password} onChange={(e) => setPassword(e.target.value)}/>
                    <button className="loginPageRegisterButton" onClick={handleRegisterClick}>Register</button>
                    <button className="loginPageLoginButton">Login</button>
                </form>
                <h3 className="loginPageRegisterLink" onClick={handleRedirection}>First Time Here? Register!</h3>
                <button className="googleLoginButton"
                        onClick={() => window.location.href = "http://localhost:8080/oauth2/authorization/google"}>
                    <img src={LoginWithGoogleSvg} alt="test"/>
                </button>
                {isUserNotFound && <h3>Username Or Password Is Incorrect</h3>}
            </div>
        </div>
    )
}