import "./LoginPage.css"
import InputField from "../../components/InputField/InputField.jsx";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function LoginPage() {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    function handleRegisterClick(e){
        e.preventDefault();
        navigate("/users/register");
    }

    async function handleSubmit(e){
        e.preventDefault();

        await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({username: username, password: password}),
        })

        navigate("/")
    }

    return (
        <div className="pageWrapper">
            <div className="formContainer">
                <form className="loginForm" onSubmit={handleSubmit}>
                    <InputField className="loginPageInputField" placeholder="Username" type="text" value={username}
                                onChange={(e) => setUsername(e.target.value)}/>
                    <InputField className="loginPageInputField" placeholder="Password" type="password"
                                value={password} onChange={(e) => setPassword(e.target.value)}/>
                    <button className="loginPageRegisterButton" onClick={handleRegisterClick}>Register</button>
                    <button className="loginPageLoginButton">Login</button>
                </form>
            </div>
        </div>
    )
}