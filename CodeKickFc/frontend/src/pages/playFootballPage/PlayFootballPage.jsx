import "./PlayFootballPage.css"
import {useLocation, useNavigate} from "react-router-dom";
import MatchEntry from "../../components/MatchEntry/MatchEntry.jsx";
import {useEffect, useState} from "react";
import JoinFootballBar from "../../components/JoinFootballBar/JoinFootballBar.jsx";
import {formatDateDefault} from "../../utils/ReusableFunctions.js";

export default function PlayFootballPage() {
    const [footballMatches, setFootballMatches] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const area = queryParams.get("area");
    const [pageNumber, setPageNumber] = useState(0);

    useEffect(() => {
        async function fetchMatches() {
            let url;

            if(area && pageNumber || area){
                url = `/api/matches?area=${area}&pageNumber=${pageNumber}`
            }else{
                url = `/api/matches?pageNumber=${pageNumber}`
            }

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
    }, [area, pageNumber]);


    function handleNextClick(e){
        e.preventDefault();
        if(footballMatches.length !== 5){
            return
        }
        setPageNumber(pageNumber + 1);
    }

    function handlePreviousClick(e){
        e.preventDefault();
        if(pageNumber === 0){
            return;
        }
        setPageNumber(pageNumber - 1);
    }

    return (
        footballMatches ? (
            footballMatches.error ? (
                <h1>{footballMatches.error}</h1>
            ) : (
                <section className="playFootballPage">
                    <div className="matchEntryQueryBoxContainer">

                    </div>
                    <JoinFootballBar />
                    <div className="matchEntryContainer">
                        <div className="matchEntryTextBox">
                            <h3 className="column matchDateText">Match Entries</h3>
                            <h3 className="column">Location</h3>
                            <h3 className="column">Price</h3>
                            <h3 className="column matchPlayerCountText">Player Count</h3>
                            <h3 className="column">Details</h3>
                        </div>
                        <div className="matchEntriesBox">
                            {footballMatches.map((footballMatch) => (
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
                        <div className="pageButtonContainer">
                            <button className="pageButton" onClick={handlePreviousClick}>Previous</button>
                            <h5 className="pageNumber">{pageNumber}</h5>
                            <button className="pageButton" onClick={handleNextClick}>Next</button>
                        </div>
                    </div>
                </section>
            )
        ) : (
            <h1>Loading...</h1>
        )
    );

}