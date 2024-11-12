import "./RegisterPage.css"
import InputField from "../../components/InputField/InputField.jsx";
import {useState} from "react";

export default function RegisterPage(){
    const [username, setUsername] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    return (
        <div className="registerPageWrapper">
            <div className="formContainer">
                <form className="registerForm">
                    <InputField className="registerPageInputField" placeholder= "Username" type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "First Name" type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Last Name" type="text" value={lastName} onChange={(e) => setLastName(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Email" type="email" value={email} onChange={(e) => setEmail(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                    <InputField className="registerPageInputField" placeholder= "Password confirmation" type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </form>
            </div>
        </div>
    )
}