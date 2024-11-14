import avatarImg from "../../assets/images/avatar.png";
import "./SubscribedPlayers.css"

export default function SubscribedPlayers(props) {
    return (
        <div className="subscribedPlayersBox">
            <div className="subscribedPlayersTitle">Signed Up Players</div>
            <ul className="subscribedPlayerDetailsBox">
                {props.subscribedPlayers.map((player) => (
                    <li key={player.id} className="subscribedPlayerDetailsList">
                        <img src={avatarImg} alt="Avatar" className="avatarImg"/>
                        <p className="username">{player.username}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}