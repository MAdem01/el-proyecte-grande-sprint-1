import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarDays, faCreditCard, faClock, faMapLocationDot} from '@fortawesome/free-solid-svg-icons';
import "./WhenAndWhereBox.css";

export default function WhenAndWhereBox(props) {
    const weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    const month = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const monthName = month[date.getMonth()];
        const day = weekday[date.getDay()];

        return `${day}, ${monthName} ${date.getMonth()}, ${year}`;
    }

    function formatTime(dateString) {
        const date = new Date(dateString);
        const hour = date.getHours();
        let minutes = date.getMinutes().toString();
        if (minutes.length === 1) {
            minutes = `0${minutes}`;
        }

        return `${hour}:${minutes}`;
    }

    function formatAddress(city, district, postcode, streetName, streetNumber) {
        return `${postcode}, ${city}, ${district}. district, ${streetName} Str. ${streetNumber}`;
    }

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
            </ul>
        </div>
    )
}