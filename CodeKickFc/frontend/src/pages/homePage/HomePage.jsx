import "./homePage.css"
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function HomePage() {
    const [city, setCity] = useState('');
    const navigate = useNavigate();

    function handleSubmit(e){
        e.preventDefault();
        if(city === ""){
            navigate("/football-games");
            return
        }

        navigate("/football-games?city="+city);
    }


    return (
        <section>
            <div className="homeContainer">
                <h1>
                    Play Football
                </h1>
                <h2>
                    Even if you have no friends...
                </h2>
            <div className="inputBox">
                <h1 className="inputLabel">
                    Find a game near you
                </h1>
                <form onSubmit={handleSubmit} className="inputForm">
                    <div className="inputWrapper">
                        <FontAwesomeIcon className="inputLogo" icon={faMagnifyingGlass} />
                        <input value={city} onChange={e => setCity(e.target.value)} className="inputField" placeholder="Enter a city..."/>
                    </div>
                    <button className="inputButton">Search for games</button>
                </form>
            </div>
            </div>
        </section>
    )
}
