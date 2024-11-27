import * as React from "react";
import {useState, useEffect} from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import NavigationBar from './components/NavigationBar/NavigationBar.jsx';
import HomePage from './pages/HomePage/HomePage.jsx';
import PlayFootballPage from "./pages/playFootballPage/PlayFootballPage.jsx";
import RegisterPage from "./pages/registerPage/RegisterPage.jsx";
import ProfilePage from "./pages/profilePage/ProfilePage.jsx";
import MatchDetails from "./pages/matchDetails/MatchDetails.jsx";
import LoginPage from "./pages/loginPage/LoginPage.jsx";
import PaymentPage from "./pages/paymentPage/PaymentPage.jsx";


function Layout({isLoggedIn}) {
    return (
        <>
            <NavigationBar
                isLoggedIn={isLoggedIn}
            />
            <Outlet/>
        </>
    );
}


function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);


    const router = createBrowserRouter([
        {
            path: "/",
            element: <Layout
                isLoggedIn={isLoggedIn}
            />,
            children: [
                {
                    path: "/",
                    element: <HomePage/>
                },
                {
                    path: "/football-games",
                    element: <PlayFootballPage/>
                },
                {
                    path: "/users/register",
                    element: <RegisterPage
                        setIsLoggedIn={setIsLoggedIn}/>
                },
                {
                    path: "/users/login",
                    element: <LoginPage
                        setIsLoggedIn={setIsLoggedIn}/>
                },
                {
                    path: "/user/:id",
                    element: <ProfilePage/>
                },
                {
                    path: "/matchdetails/:matchId",
                    element: <MatchDetails/>
                },
                {
                    path: "/payment/:userId/:matchId",
                    element: <PaymentPage/>
                }
            ],
        },
    ]);

    return (
        <RouterProvider router={router}/>
    );
}

export default App;
