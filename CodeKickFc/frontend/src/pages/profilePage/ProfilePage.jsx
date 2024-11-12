import { useParams } from "react-router-dom";
import "./profilePage.css";
import {useState, useEffect} from "react";

export default function ProfilePage() {
    const { id } = useParams();

    const[userData, setUserData] = useState(null);
    const[isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        fetchUserData();
    },[]);

    const fetchUserData = async () => {
        const response = await fetch(`/api/users/${id}`);
        const data = await response.json();
        setUserData(data);
    }

    return (
        <div>
            {isLoggedIn ?
            (
                <div>
                    <p>Hi</p>
                </div>
            ) : 
            (
                <div>
                    <p>Do I know you...</p>
                    <button onClick={() => setIsLoggedIn(true)}>buttomn</button>
                </div>
            )}
        </div>
    )
}