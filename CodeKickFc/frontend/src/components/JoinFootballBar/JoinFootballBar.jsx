import {useNavigate} from "react-router-dom";

export default function JoinFootballBar() {
    const navigate = useNavigate();

    return( <div className="joinFootballBox">
        <h2 className="joinFootballBoxText">
            Do you want to play football? Join CodeKickFC today for Free!
        </h2>
        <div className="joinFootballButtonBox">
        <button onClick={() => navigate("/users/register")} className="joinFootballBoxButton">
            Join us
        </button>
        </div>
    </div>)

}