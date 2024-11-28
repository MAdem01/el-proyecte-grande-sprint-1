import "./PaymentPage.css";
import {useLocation, useParams} from "react-router-dom";
import {formatDate} from "../../utils/ReusableFunctions.js";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheck} from '@fortawesome/free-solid-svg-icons';


export default function PaymentPage() {
    const token = JSON.parse(localStorage.getItem("user")).jwt;
    const {userId, matchId} = useParams();
    const location = useLocation();
    const matchDetails = location.state.matchDetails;
    const [submitted, setSubmitted] = useState(false);
    const [cardNumber, setCardNumber] = useState("");
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");

    async function handleSubmit(event) {
        event.preventDefault();

        const response = await fetch(`/api/users/${userId}/matches/${matchId}/add`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });

        const data = await response.json();

        if (data.error) {
            setSubmitted({error: "You Already Signed Up for this match!"});
            return;
        } else {
            try {
                const response = await fetch('/api/email/confirmation', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        subject: "Confirmation",
                        description: "Hey, you successfully booked a game. Don't you dare not showing up!",
                        emailAddress: email
                    }),
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
            } catch (error) {
                console.error('Error sending email:', error);
            }
        }

        setSubmitted(true);
    }

    return submitted ?
        submitted.error ?
            <h1>{submitted.error}</h1>
            :
            <div className="paymentPageBox">
                <div className="bg">

                    <div className="card">

                        <span className="card__success"><FontAwesomeIcon icon={faCheck}
                                                                         className="checkMarkIcon"/></span>

                        <h1 className="card__msg">Payment Complete</h1>
                        <h2 className="card__submsg">Thank you for your transfer</h2>

                        <div className="card__body">
                            <div className="card__recipient-info">
                                <p className="card__recipient">{name}</p>
                                <p className="card__email">{email}</p>
                            </div>

                            <h1 className="card__price"><span>{matchDetails.price}</span>Ft</h1>

                            <p className="card__method">Payment method</p>
                            <div className="card__payment">
                                <img src="https://seeklogo.com/images/V/VISA-logo-F3440F512B-seeklogo.com.png"
                                     className="card__credit-card"/>
                                <div className="card__card-details">
                                    <p className="card__card-type">Credit / debit card</p>
                                    <p className="card__card-number">Visa ending in **{cardNumber.slice(-2)}</p>
                                </div>
                            </div>

                        </div>

                        <div className="card__tags">
                            <span className="card__tag">completed</span>
                            <span className="card__tag">#123456789</span>
                        </div>

                    </div>

                </div>

            </div>
        :
        <div className="row">
            <div className="col-75">
                <div className="container">
                    <h2>{matchDetails.pitchName}, {matchDetails.city} {matchDetails.district} ({formatDate(matchDetails.date)})
                    </h2>
                    <form onSubmit={handleSubmit}>
                        <div className="row">
                            <div className="col-50">
                                <h3>Billing Address</h3>
                                <label htmlFor="fname"><i className="fa fa-user"></i> Full Name</label>
                                <input type="text" id="fname" name="firstname" placeholder="John M. Doe"
                                       value={name}
                                       onChange={(event) =>
                                           setName(event.target.value)}
                                       required/>
                                <label htmlFor="email"><i className="fa fa-envelope"></i> Email</label>
                                <input type="email" id="email" name="email" placeholder="john@example.com"
                                       value={email}
                                       onChange={(event) =>
                                           setEmail(event.target.value)}
                                       required/>
                                <label htmlFor="adr"><i className="fa fa-address-card-o"></i> Address</label>
                                <input type="text" id="adr" name="address" placeholder="Example Street 12"
                                       required/>
                                <label htmlFor="city"><i className="fa fa-institution"></i> City</label>
                                <input type="text" id="city" name="city" placeholder="Budapest" required/>

                                <div className="row">
                                    <div className="col-50">
                                        <label htmlFor="county">County</label>
                                        <input type="text" id="county" name="county" placeholder="Pest"
                                               required/>
                                    </div>
                                    <div className="col-50">
                                        <label htmlFor="postcode">Postcode</label>
                                        <input type="text" id="postcode" name="postcode" placeholder="1234"
                                               required/>
                                    </div>
                                </div>
                            </div>

                            <div className="col-50">
                                <h3>Payment</h3>
                                <label htmlFor="fname">Accepted Cards</label>
                                <div className="icon-container">
                                    <i className="fa fa-cc-visa"></i>
                                    <i className="fa fa-cc-amex"></i>
                                    <i className="fa fa-cc-mastercard"></i>
                                    <i className="fa fa-cc-discover"></i>
                                </div>
                                <label htmlFor="cname">Name on Card</label>
                                <input type="text" id="cname" name="cardname"
                                       placeholder="John More Doe" required/>
                                <label htmlFor="ccnum">Credit card number</label>
                                <input type="text" id="ccnum" name="cardnumber"
                                       placeholder="1111-2222-3333-4444"
                                       value={cardNumber}
                                       onChange={(event) =>
                                           setCardNumber(event.target.value)}
                                       required/>
                                <label htmlFor="expmonth">Exp Month</label>
                                <input type="text" id="expmonth" name="expmonth"
                                       placeholder="September" required/>

                                <div className="row">
                                    <div className="col-50">
                                        <label htmlFor="expyear">Exp Year</label>
                                        <input type="number" id="expyear" name="expyear"
                                               placeholder="2018"
                                               required/>
                                    </div>
                                    <div className="col-50">
                                        <label htmlFor="cvv">CVV</label>
                                        <input type="password" id="cvv" name="cvv" placeholder="352"
                                               required/>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <label>
                            <input type="checkbox" name="sameadr"/>
                            Shipping address same as billing
                        </label>
                        <button type="submit" className="btn">
                            Pay and continue
                        </button>
                    </form>
                </div>
            </div>
        </div>

}