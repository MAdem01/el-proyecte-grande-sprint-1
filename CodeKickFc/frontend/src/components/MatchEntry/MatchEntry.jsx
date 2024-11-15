import "./MatchEntry.css"

export default function MatchEntry(props) {
    return (
        <div className="matchEntryBox">
            <h3 className="matchDate column">{props.date}</h3>
            <h3 className="matchLocation column">{props.city + ", " + props.district}</h3>
            <h3 className="matchPrice column">{props.price + "Ft"}</h3>
            <h3 className="column matchPlayerCount">{props.currentPlayerCount + "/" + props.maxPlayers}</h3>
            <div className="matchDetailsButtonContainer column">
                <button className="matchDetailsButton column"
                    onClick={() => props.navigate(`/matchdetails/${props.match.match_id}`,
                        {state: {match: props.match}})}>Details
                 </button>
            </div>
        </div>
    )
}