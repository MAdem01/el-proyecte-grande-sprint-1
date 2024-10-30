import "./PlayFootballPage.css"
import {useNavigate} from "react-router-dom";
import MatchEntry from "../../components/MatchEntry/MatchEntry.jsx";

export default function PlayFootballPage(){
    const navigate = useNavigate();
    const footballMatches = [
        {date: "2024-01-01", location: "Archbishop, Waterloo", price: 8, maxPlayers: 16, currentPlayerCount: 8},
        {date: "2024-01-05", location: "Stadium Park, London", price: 10, maxPlayers: 20, currentPlayerCount: 15},
        {date: "2024-01-10", location: "Greenfield, Manchester", price: 12, maxPlayers: 18, currentPlayerCount: 10},
        {date: "2024-01-15", location: "Victoria Ground, Leeds", price: 9, maxPlayers: 14, currentPlayerCount: 9},
        {date: "2024-01-20", location: "Central Park, Birmingham", price: 11, maxPlayers: 22, currentPlayerCount: 20},
        {date: "2024-01-25", location: "Sunnyfield, Liverpool", price: 7, maxPlayers: 16, currentPlayerCount: 6},
    ];


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