import "./PlayFootballPage.css"
import {useLocation, useNavigate} from "react-router-dom";
import MatchEntry from "../../components/MatchEntry/MatchEntry.jsx";
import {useEffect, useState} from "react";
import JoinFootballBar from "../../components/JoinFootballBar/JoinFootballBar.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";

export default function PlayFootballPage() {
    const [footballMatches, setFootballMatches] = useState(null);
    const [city, setCity] = useState(null);
    const [minPrice, setMinPrice] = useState(null);
    const [maxPrice, setMaxPrice] = useState(null);
    const [date, setDate] = useState(null);

    const navigate = useNavigate();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const cityParam = queryParams.get("city");
    const [pageNumber, setPageNumber] = useState(0);

    useEffect(() => {
        async function fetchMatches() {
            const url = cityParam ? `/api/matches?district=${cityParam}` : `/api/matches`;

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
    }, [cityParam, pageNumber]);


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

    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    async function handleSubmit(e){
        e.preventDefault();
        let queryParams = '';

        if (city != null) {
            queryParams += `district=${city}&`;
        }
        if (minPrice != null) {
            queryParams += `minPrice=${minPrice}&`;
        }
        if (maxPrice != null) {
            queryParams += `maxPrice=${maxPrice}&`;
        }
        if (date !=null) {
            queryParams += `matchDate=${date}&`;
        }

        queryParams = queryParams.endsWith('&') ? queryParams.slice(0, -1) : queryParams;

        const url = `/api/matches?${queryParams}&pageNumber=${pageNumber}`;


        const response = await fetch(url)
        if (!response.ok) {
            if (response.status === 404) {
                setFootballMatches(null);
            } else {
                throw new Error("Failed to fetch matches");
            }
        }
        const matches = await response.json();
        setFootballMatches(matches);
    }

    return (
            <section className="playFootballPage">
                <div className="matchEntryQueryBox">
                    <div className="matchEntryQueryBoxTextContainer">
                        <h2 className="matchEntryQueryBoxText"> Find games</h2>
                    </div>
                    <form onSubmit={handleSubmit} className="matchEntryQueryBoxInputForm">
                        <div className="matchEntryQueryBoxInputWrapper">
                            <FontAwesomeIcon className="inputLogo" icon={faMagnifyingGlass}/>
                            <input value={city} onChange={e => setCity(e.target.value)} className="queryInputField"
                                   placeholder="Enter a district..."/>
                            <input value={date} onChange={e => setDate(e.target.value)} className="queryInputField"
                                   placeholder="Enter a date..." type="date"/>
                            <input value={minPrice} onChange={e => setMinPrice(e.target.value)} className="queryInputField"
                                   placeholder="Enter a min price..."/>
                            <input value={maxPrice} onChange={e => setMaxPrice(e.target.value)} className="queryInputField"
                                   placeholder="Enter a max price..."/>
                        </div>
                        <div>
                            <button className="applyFilterButton">Apply filter</button>
                        </div>
                    </form>
                </div>
                <JoinFootballBar/>
                <div className="matchEntryContainer">
                    <div className="matchEntryTextBox">
                        <h2 className="matchEntryText">Match Entries</h2>
                        <h2 className="locationLabel">Location</h2>
                        <h2 className="priceLabel">Price</h2>
                        <h2 className="playerCountLabel">Player Count</h2>
                    </div>
                    {footballMatches ? !footballMatches.error ?
                        <div className="matchEntriesBox">
                            {footballMatches.map((footballMatch) => (
                                <MatchEntry
                                    key={footballMatch.match_id}
                                    date={formatDate(footballMatch.match_date)}
                                    city={footballMatch.footballPitch.city}
                                    district={footballMatch.footballPitch.district}
                                    price={footballMatch.match_fee_per_players}
                                    currentPlayerCount={footballMatch.subscribedPlayers.length}
                                    maxPlayers={footballMatch.maxPlayers}
                                />
                            ))}
                        </div> : <h1>{footballMatches.error}</h1>  : null}

                        <div className="pageButtonContainer">
                        <button className="pageButton" onClick={handlePreviousClick}>Previous
                        </button>
                             <h5 className="pageNumber">{pageNumber}</h5>
                             <button className="pageButton" onClick={handleNextClick}>Next</button>
                        </div>
                </div>
            </section>
    );

}