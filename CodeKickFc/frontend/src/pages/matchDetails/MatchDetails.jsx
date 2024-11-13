import {useLocation} from "react-router-dom";
import WhenAndWhereBox from "../../components/WhenAndWhereBox/WhenAndWhereBox.jsx";
import "./MatchDetails.css"
import SubscribedPlayers from "../../components/SubscribedPlayers/SubscribedPlayers.jsx";
import MatchDescription from "../../components/MatchDiscription/MatchDescription.jsx";

export default function MatchDetails() {
    const location = useLocation();
    const match = location.state.match;

    return (
        <div className="matchDetailsBox">
            <WhenAndWhereBox date={match.match_date}
                             city={match.footballPitch.city}
                             district={match.footballPitch.district}
                             postcode={match.footballPitch.postcode}
                             streetName={match.footballPitch.streetName}
                             streetNumber={match.footballPitch.streetNumber}
                             price={match.match_fee_per_players}/>
            <SubscribedPlayers subscribedPlayers={match.subscribedPlayers}/>
            <MatchDescription image={match.footballPitch.imgUrl}
                              description={match.footballPitch.description}
                              pitchType={match.footballPitch.pitchType}
                              rules={match.matchRules}/>
        </div>
    )
}