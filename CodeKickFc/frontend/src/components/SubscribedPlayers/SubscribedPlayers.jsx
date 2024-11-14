import avatarImg from "../../assets/images/avatar.png";
import "./SubscribedPlayers.css"
import {useNavigate} from 'react-router-dom';

export default function SubscribedPlayers(props) {
    const navigate = useNavigate();

    return (
        <div className="subscribedPlayersBox">
            <div className="subscribedPlayersTitle">Signed Up Players</div>
            <ul className="subscribedPlayerDetailsBox">
                {props.subscribedPlayers.map((player) => (
                    <li key={player.id} className="subscribedPlayerDetailsList" onClick={() => navigate(`/user/${player.id}`)}>
                        <img src={avatarImg} alt="Avatar" className="avatarImg"/>
                        <p className="username">{player.username}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}