import { useParams } from "react-router-dom";
import "./profilePage.css";
import {useState, useEffect} from "react";

export default function ProfilePage() {
    const { id } = useParams();

    const[userData, setUserData] = useState(null);

    useEffect(() => {
        fetchUserData();
    },[]);

    const fetchUserData = async () => {
        const response = await fetch(`/api/users/${id}`);
        const data = await response.json();
        setUserData(data);
    }

    return (
        <section>
            {userData != null ?
            (
                <div>
                    <p>Hi</p>
                    <p>{userData.username}</p>
                </div>
            ) : 
            (
                <div>
                </div>
            )}
        </section>
    )
}