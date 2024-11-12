import "./RegisterPage.css"
import InputField from "../../components/InputField/InputField.jsx";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function RegisterPage(){
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [isPasswordValid, setIsPasswordValid] = useState(true);

    function handleLoginClick(e){
        e.preventDefault();

        navigate("/users/login");
    }

    function checkIfPasswordIsValid(){
        if(password !== passwordConfirm || passwordConfirm === "" || password === ""){
            setIsPasswordValid(false);
            return false
        }else if(password === passwordConfirm){
            setIsPasswordValid(true);
            return true
        }
    }

    async function handleSubmit(e){
        e.preventDefault();


        if(!checkIfPasswordIsValid()) return

       const userIdResponse = await fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({username: username, firstName: firstName, lastName: lastName, email: email, password: password}),
        })

        const userId = await userIdResponse.json();
        localStorage.clear();
        localStorage.setItem("userId", userId);


        navigate("/");
    }


    return (
        <div className="registerPageWrapper">
            <div className="formContainer">
                <form className="registerForm" onSubmit={handleSubmit}>
                {isPasswordValid ? null : <h4 className="passwordErrorMessage"> Password does not match</h4>}
                    <InputField className="registerPageInputField" placeholder= "Username" type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "First Name" type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Last Name" type="text" value={lastName} onChange={(e) => setLastName(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Email" type="email" value={email} onChange={(e) => setEmail(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Password confirmation" type="password" value={passwordConfirm} onChange={(e) => setPasswordConfirm(e.target.value)}/>
                    <button type= "submit" className="registerPageRegisterButton">Register</button>
                    <button className="registerPageLoginButton" onClick={handleLoginClick}>Login</button>
                </form>
            </div>
        </div>
    )
}