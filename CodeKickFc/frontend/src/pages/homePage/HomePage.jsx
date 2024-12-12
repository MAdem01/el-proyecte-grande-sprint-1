import "./homePage.css"
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBug, faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function HomePage() {
    // localStorage.clear();
     console.log(localStorage.user);
    const [area, setArea] = useState('');
    const [isBugButtonClick, setIsBugButtonClick] = useState(false);
    const [bugEmailSubject, setBugEmailSubject] = useState('');
    const [bugEmailDescription, setBugEmailDescription] = useState('');
    const navigate = useNavigate();

    function handleSubmit(e) {
        e.preventDefault();
        if (area === "") {
            navigate("/football-games");
            return
        }

        navigate("/football-games?area=" + area);
    }

    function handleBugButtonClick() {
        setIsBugButtonClick(!isBugButtonClick);
    }

    async function handleBugSubmit(e) {
        e.preventDefault()
        try {
            const response = await fetch('/api/email/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    subject: bugEmailSubject,
                    description: bugEmailDescription,
                }),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            setBugEmailSubject('');
            setBugEmailDescription('');
        } catch (error) {
            console.error('Error sending email:', error);
        }
    }

    function handleLogout() {
        window.location.href = "http://localhost:8080/logout";
    }

    return (
        <section className="homePageSection">
            <div className="homeContainer">
                <h1 className="homePageText-h1">
                    Play Football
                </h1>
                <h2 className="homePageText-h2">
                    Even if you have no friends...
                </h2>
                <div className="inputBox">
                    <h1 className="inputLabel">
                        Find a game near you
                    </h1>
                    <form onSubmit={handleSubmit} className="inputForm">
                        <div className="inputWrapper">
                            <FontAwesomeIcon className="inputLogo" icon={faMagnifyingGlass}/>
                            <input value={area} onChange={e => setArea(e.target.value)} className="inputField"
                                   placeholder="Enter a city or a district..."/>
                        </div>
                        <button className="inputButton">Search for games</button>
                    </form>
                </div>
                {isBugButtonClick ?
                    <form onSubmit={handleBugSubmit} className="bugForm">
                        <input value={bugEmailSubject} onChange={e => setBugEmailSubject(e.target.value)}
                               className="bugSubjectInputField" placeholder="Enter Subject"/>
                        <input value={bugEmailDescription} onChange={e => setBugEmailDescription(e.target.value)}
                               className="bugDescriptionInputField" placeholder="Enter Description"/>
                        <button className="bugFormButton" type="submit">Submit</button>
                    </form> : null
                }
                <div className="bugButtonBox">
                    <FontAwesomeIcon className="bugIcon" icon={faBug} onClick={handleBugButtonClick}/>
                </div>
            </div>
            <button onClick={handleLogout}>log out</button>
        </section>
    )
}
