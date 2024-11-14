import "./MatchDescription.css";
import {AdvancedMarker, APIProvider, Map, Pin} from "@vis.gl/react-google-maps";

export default function MatchDescription(props) {
    const position = {lat: props.latitude, lng: props.longitude};

    return (
        <div className="matchDescriptionBox">
            <div className="matchDescriptionTitle">Description
                <h3 className="fieldName">{props.name}</h3>
            </div>
            <ul className="matchDescriptionList">
                <li>
                    <img src={props.image} alt="Football Field" className="pitchImage"/>
                </li>
                <li>
                    <h4 className="gameRulesText">Game Rules:</h4>
                    <ul className="matchRulesBox">
                        {props.rules.map((rule, i) => (
                            <li key={i} className="matchRule">{rule}</li>
                        ))}
                    </ul>
                </li>
                <li>
                    <h4 className="fieldTypeTitle">Field type:</h4>
                    <p className="fieldTypeText">{props.pitchType}</p>
                </li>
                <li>
                    <h4 className="fieldDescriptionTitle">Field description:</h4>
                    <p className="fieldDescriptionText">{props.description}</p>
                </li>
            </ul>
        </div>
    )
}