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
                setFootballMatches(matches);
            } catch (error) {
                console.error(error.message);
                setFootballMatches({error: error.message});
            }
        }

        fetchMatches();
    }, [city]);


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
        footballMatches ? (
            footballMatches.error ? (
                <h1>{footballMatches.error}</h1>
            ) : (
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
                            <h2 className="matchEntryText">Match Entries</h2>
                            <h2 className="locationLabel">Location</h2>
                            <h2 className="priceLabel">Price</h2>
                            <h2 className="playerCountLabel">Player Count</h2>
                        </div>
                        <div className="matchEntriesBox">
                            {footballMatches.map((footballMatch) => (
                                <MatchEntry
                                    key={footballMatch.match_id}
                                    date={formatDate(footballMatch.match_date)}
                                    location={footballMatch.footballPitch.address.match(/\b[A-Z][a-z]*\b/)?.[0]}
                                    price={footballMatch.match_fee_per_players}
                                    currentPlayerCount={footballMatch.subscribedPlayers.length}
                                    maxPlayers={footballMatch.maxPlayers}
                                />
                            ))}
                        </div>
                    </div>
                </section>
            )
        ) : (
            <h1>Loading...</h1>
        )
    );

}