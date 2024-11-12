import "./MatchEntry.css"

export default function MatchEntry(props){
    return (
        <div className="matchEntryBox">
            <h3 className="matchDate">{props.date}</h3>
            <h3 className="matchLocation">{props.city + ", " + props.district}</h3>
            <h3 className="matchPrice">{props.price + "£"}</h3>
            <div className="matchPlayerCountBox">
                <h3 className="currentPlayerCount">{props.currentPlayerCount}</h3>
                <h3 className="maxPlayerCount">{"/ " + props.maxPlayers}</h3>
            </div>
        </div>
    )
}