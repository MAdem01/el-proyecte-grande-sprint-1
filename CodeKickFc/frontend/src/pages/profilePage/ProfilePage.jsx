import {useParams, useNavigate} from "react-router-dom";
import "./profilePage.css";
import {useState, useEffect} from "react";
import MatchEntry from "../../components/MatchEntry/MatchEntry.jsx";
import {formatDateDefault} from "../../utils/ReusableFunctions.js";

export default function ProfilePage() {
    const {id} = useParams();
    const navigate = useNavigate();
    const token = JSON.parse(localStorage.getItem("user")).jwt;
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        fetchUserData();
    }, []);

    const fetchUserData = async () => {
        const response = await fetch(`/api/users/${id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });
        const data = await response.json();
        setUserData(data);
    }

    return (
        <section className="userPage">
            {userData &&
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
                                    date={formatDateDefault(footballMatch.match_date)}
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
                )}
        </section>
    )
}