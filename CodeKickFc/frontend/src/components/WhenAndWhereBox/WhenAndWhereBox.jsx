import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarDays, faClock, faCreditCard, faMapLocationDot} from '@fortawesome/free-solid-svg-icons';
import "./WhenAndWhereBox.css";
import {useNavigate} from "react-router-dom";
import "../../utils/ReusableFunctions.js";
import {formatAddress, formatDate, formatTime} from "../../utils/ReusableFunctions.js";
import {useEffect, useState} from "react";

export default function WhenAndWhereBox(props) {
    const navigate = useNavigate();
    const [userId, setUserId] = useState(null);

    useEffect(() => {
        function getUserId() {
            if (localStorage.getItem("user")) {
                setUserId(JSON.parse(localStorage.getItem("user")).id);
            }
        }

        getUserId()
    }, []);

    return (
        <div className="whenAndWhereBox">
            <div className="whenAndWhereTitle">When and Where</div>
            <ul>
                <li>
                    <div className="whenAndWhereDateBox">
                        <span className="whenAndWhereDateIcon">
                            <FontAwesomeIcon icon={faCalendarDays}/>
                        </span>
                        <p className="whenAndWhereDateText">{formatDate(props.date)}</p>
                    </div>
                </li>
                <li>
                    <div className="whenAndWhereTimeBox">
                        <span className="whenAndWhereTimeIcon">
                            <FontAwesomeIcon icon={faClock}/>
                        </span>
                        <p className="whenAndWhereTimeText">{formatTime(props.date)}</p>
                    </div>
                </li>
                <li>
                    <div className="whenAndWhereLocationBox">
                        <span className="whenAndWhereLocationIcon">
                            <FontAwesomeIcon icon={faMapLocationDot}/>
                        </span>
                        <p className="whenAndWhereLocationText">{formatAddress(props.city, props.district, props.postcode,
                            props.streetName, props.streetNumber)}</p>
                    </div>
                </li>
                <li>
                    <div className="whenAndWherePaymentBox">
                        <span className="whenAndWherePaymentIcon">
                            <FontAwesomeIcon icon={faCreditCard}/>
                        </span>
                        <p className="whenAndWherePaymentText">{props.price + "Ft /person"}</p>
                    </div>
                </li>
                <li>
                    <div className="whenAndWhereJoinGameButtonBox">
                        <button
                            className={props.subscribedPlayers >= props.maxPlayers ?
                                "joinGameButton disabled" : "joinGameButton"}
                            disabled={props.subscribedPlayers >= props.maxPlayers}
                            onClick={() => navigate(
                                `/payment/${userId}/${props.matchId}`,
                                {state: {matchDetails: props}}
                            )}>
                            Join Game
                        </button>
                    </div>
                </li>
            </ul>
        </div>
    )
}