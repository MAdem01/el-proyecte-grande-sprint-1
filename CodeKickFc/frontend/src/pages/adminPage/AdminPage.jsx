import {useEffect, useState} from "react";
import "./AdminPage.css";


export default function AdminPage() {
    const token = JSON.parse(localStorage.getItem("user")).jwt;
    const [maxPlayers, setMaxPlayers] = useState(0);
    const [matchFee, setMatchFee] = useState(0);
    const [matchDate, setMatchDate] = useState("");
    const [matchTime, setMatchTime] = useState("");
    const [matchRules, setMatchRules] = useState("");
    const [footballPitchId, setFootballPitchId] = useState(0);
    const [footballPitches, setFootballPitches] = useState(null);


    function setMinimumDate() {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        return tomorrow.toISOString().split('T')[0];
    }

    useEffect(() => {
        async function fetchFootballPitches() {
            const response = await fetch("/api/footballpitches", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            });
            const footballPitches = await response.json();
            setFootballPitches(footballPitches);
            setFootballPitchId(footballPitches[0].id);
        }

        fetchFootballPitches();
    }, [token]);

    async function handleSubmit(event) {
        event.preventDefault();

        const match = {
            maxPlayers: maxPlayers,
            matchFeePerPerson: matchFee,
            matchDate: `${matchDate}T${matchTime}`,
            matchRules: matchRules,
            footballPitch: {
                id: footballPitchId,
            }
        };

        await fetch("/api/matches/admin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(match)
        });

        setMaxPlayers(0);
        setMatchFee(0);
        setMatchDate("");
        setMatchTime("");
        setFootballPitchId(0);
        setMatchRules("");
    }

    return (
        <div className="addMatchFormBox">
            <form onSubmit={handleSubmit} className="addMatchForm">
                <label className="adminPageLabel">
                    Max Players
                    <input className="adminPageInput"
                           type="number"
                           min="8"
                           placeholder="Add match capacity"
                           value={maxPlayers}
                           onChange={(e) =>
                               setMaxPlayers(e.target.value)}
                           required/>
                </label>
                <label className="adminPageLabel">
                    Match Fee Per Player
                    <input className="adminPageInput"
                           type="number"
                           min="1000"
                           placeholder="Add match fee"
                           value={matchFee}
                           onChange={(e) =>
                               setMatchFee(e.target.value)}
                           required/>
                </label>
                <label className="adminPageLabel">
                    Match Date
                    <input className="adminPageInput"
                           type="date"
                           min={setMinimumDate()}
                           value={matchDate}
                           onChange={(e) =>
                               setMatchDate(e.target.value)}
                           required/>
                </label>
                <label className="adminPageLabel">
                    Match Time
                    <input className="adminPageInput"
                           type="time"
                           value={matchTime}
                           onChange={(e) =>
                               setMatchTime(e.target.value)}
                           required/>
                </label>
                <label className="adminPageLabel">
                    Match Rules
                    <textarea className="adminPageInput"
                              cols="50" rows="10" placeholder="Add match rules"
                              value={matchRules}
                              onChange={(e) =>
                                  setMatchRules(e.target.value)}
                              required/>
                </label>
                <select
                    className="adminPageInput"
                    value={footballPitchId}
                    onChange={(e) =>
                        setFootballPitchId(e.target.value)}
                    required>
                    {footballPitches && footballPitches.map((footballPitch) => (
                        <option key={footballPitch.id} value={footballPitch.id}>
                            {footballPitch.name}
                        </option>
                    ))}
                </select>
                <button type="submit" className="adminPageButton">Submit</button>
            </form>
        </div>
    )
}