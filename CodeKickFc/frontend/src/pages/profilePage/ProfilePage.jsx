import { useParams, useNavigate } from "react-router-dom";
import "./profilePage.css";
import {useState, useEffect} from "react";
import MatchEntry from "../../components/MatchEntry/MatchEntry.jsx";
import "./ProfilePage.css"

export default function ProfilePage() {
    const { id } = useParams();
    const navigate = useNavigate();

    const[userData, setUserData] = useState(null);

    useEffect(() => {
        fetchUserData();
    },[]);

    const fetchUserData = async () => {
        const response = await fetch(`/api/users/${id}`);
        const data = await response.json();
        setUserData(data);
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    return (
        <section className="userPage">
            {userData != null ?
            (
                <div className="userDataContainer">
                    <div className="userNameContainer">
                        <h2>{userData.firstName} {userData.lastName}</h2>
                        <h3>{userData.username}</h3> 
                    </div>
                    <div className="userMatchesContainer">
                        {userData.matches.map((footballMatch) => (
                                <MatchEntry
                                key={footballMatch.match_id}
                                date={formatDate(footballMatch.match_date)}
                                city={footballMatch.footballPitch.city}
                                district={footballMatch.footballPitch.district}
                                price={footballMatch.match_fee_per_players}
                                currentPlayerCount={footballMatch.subscribedPlayers.length}
                                maxPlayers={footballMatch.maxPlayers}
                                matchId={footballMatch.match_id}
                                navigate={navigate}
                                match={footballMatch}
                            />
                        ))}
                    </div>
                </div>
            ) : 
            (
                <div>
                </div>
            )}
        </section>
    )
}