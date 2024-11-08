import "./PlayFootballPage.css"
import {useLocation, useNavigate} from "react-router-dom";
import MatchEntry from "../../components/MatchEntry/MatchEntry.jsx";
import {useEffect, useState} from "react";

export default function PlayFootballPage() {
    const [footballMatches, setFootballMatches] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const city = queryParams.get("city");

    useEffect(() => {
        async function fetchMatches() {
            const url = city ? `/api/matches?city=${city}` : "/api/matches";
            try {
                const response = await fetch(url);
                if (!response.ok) {
                    if (response.status === 404) {
                        const errorData = await response.json();
                        throw new Error(errorData.error || "Match not found");
                    } else {
                        throw new Error("Failed to fetch matches");
                    }
                }
                const matches = await response.json();
                console.log(matches);
                setFootballMatches(matches);
            } catch (error) {
                console.error(error.message);
                setFootballMatches({error: error.message});
            }
        }


    return (
        <section className="playFootballPage">
            <div className="joinFootballBox">
                <h2 className="joinFootballBoxText">
                    Do you want to play football? Join CodeKickFC today for Free!
                </h2>
                <button onClick={() => navigate("/users/register")} className="joinFootballBoxButton">
                    Join us
                </button>
            </div>
            <div className="matchEntryContainer">
                <div className="matchEntryTextBox">
                    <h2 className="matchEntryText">
                        Match Entries
                    </h2>
                    <h2 className="locationLabel">
                        Location
                    </h2>
                    <h2 className="priceLabel">
                        Price
                    </h2>
                    <h2 className="playerCountLabel">
                        Player Count
                    </h2>
                </div>
                <div className="matchEntriesBox">{
                    footballMatches
                            .map((footballMatch,index) => <MatchEntry
                                key={index}
                                date={footballMatch.date}
                                location={footballMatch.location}
                                price={footballMatch.price}
                                currentPlayerCount={footballMatch.currentPlayerCount}
                                maxPlayers={footballMatch.maxPlayers
                            }></MatchEntry>)
                    }
                </div>
            </div>
        </section>
    )
}