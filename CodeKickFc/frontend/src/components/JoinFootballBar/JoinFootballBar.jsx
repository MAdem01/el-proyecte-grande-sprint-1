export default function JoinFootballBar() {
    return( <div className="joinFootballBox">
        <h2 className="joinFootballBoxText">
            Do you want to play football? Join CodeKickFC today for Free!
        </h2>
        <button onClick={() => navigate("/users/register")} className="joinFootballBoxButton">
            Join us
        </button>
    </div>)

}