import "./RegisterPage.css"
import InputField from "../../components/InputField/InputField.jsx";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function RegisterPage() {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [isPasswordValid, setIsPasswordValid] = useState(true);
    const [isEmailValid, setIsEmailValid] = useState(true);
    const [isUsernameValid, setIsUsernameValid] = useState(true);
    const [isRegistered, setIsRegistered] = useState(false);

    function doInputValidation() {
        checkIfPasswordIsValid()
        checkIfEmailIsValid()

        return isEmailValid && isPasswordValid
    }

    function handleRedirection(){
        navigate("/users/login");
    }

    function checkIfEmailIsValid() {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailRegex.test(email)) {
            setIsEmailValid(true);
            return true
        } else {
            setIsEmailValid(false);
            return false
        }
    }


    function handleLoginClick(e) {
        e.preventDefault();

        navigate("/users/login");
    }


    function checkIfPasswordIsValid() {
        if (password !== passwordConfirm || passwordConfirm === "" || password === "") {
            setIsPasswordValid(false);
            return false
        } else if (password === passwordConfirm) {
            setIsPasswordValid(true);
            return true
        }
    }

    async function handleSubmit(e) {
        e.preventDefault();

        doInputValidation()

        const response = await fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password
            }),
        })

        if(response.status === 500){
            setIsUsernameValid(false)
        }

        if(response.status === 200){
            localStorage.setItem("user", JSON.stringify(response));
            setIsRegistered(true);
        }

    }


    return (
        <div className="pageWrapper">
            <form className="registerFormContainer" onSubmit={handleSubmit}>
                <h1 className="registerTitle">Register</h1>
                <h3 className={`registrationMessage ${isRegistered ? "visible" : ""}`}>Successfully Registered!</h3>
                <div className="registrationFormWrapper">
                    <form className="registrationForm">
                        <h3 className="errorMessage">dd</h3>
                        <InputField className="registerPageInputField" placeholder="Username" type="text"
                                    value={username} onChange={(e) => setUsername(e.target.value)}/>
                        <h3 className="errorMessage">.</h3>
                        <InputField className="registerPageInputField" placeholder="First Name" type="text"
                                    value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
                        <h3 className="errorMessage">.</h3>
                        <InputField className="registerPageInputField" placeholder="Last Name" type="text"
                                    value={lastName} onChange={(e) => setLastName(e.target.value)}/>
                    </form>
                    <form className="registrationForm">
                        <h3 className={`errorMessage ${!isEmailValid ? "visible" : ""}`}>Enter a valid email</h3>
                        <InputField className="registerPageInputField" placeholder="Email" type="email" value={email}
                                    onChange={(e) => setEmail(e.target.value)}/>
                        <h3 className={`errorMessage ${!isPasswordValid ? "visible" : ""}`}>Password do not match</h3>
                        <InputField className="registerPageInputField" placeholder="Password" type="password"
                                    value={password} onChange={(e) => setPassword(e.target.value)}/>
                        <h3 className={`errorMessage ${!isPasswordValid ? "visible" : ""}`}>Password do not match</h3>
                        <InputField className="registerPageInputField" placeholder="Password confirmation"
                                    type="password" value={passwordConfirm}
                                    onChange={(e) => setPasswordConfirm(e.target.value)}/>
                    </form>
                </div>
                <div className="registrationButtonWrapper">
                    <button className="registerPageLoginButton" onClick={handleLoginClick}>Login</button>
                    <button type="submit" className="registerPageRegisterButton">Register</button>
                </div>
                <h3 className="loginPageRegisterLink" onClick={handleRedirection}>Already Registered? Login Here!</h3>
            </form>
        </div>
    )
}