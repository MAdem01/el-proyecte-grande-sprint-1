import * as React from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import NavigationBar from './components/NavigationBar/NavigationBar.jsx';
import HomePage from './pages/HomePage/HomePage.jsx';
import PlayFootballPage from "./pages/playFootballPage/PlayFootballPage.jsx";
import RegisterPage from "./pages/registerPage/RegisterPage.jsx";
import MatchDetails from "./pages/matchDetails/MatchDetails.jsx";


function Layout() {
    return (
        <>
            <NavigationBar/>
            <Outlet/>
        </>
    );
}


function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Layout/>,
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
                    element: <RegisterPage/>
                },
                {
                    path: "/users/login",
                },
                {
                    path: "/matchdetails/:matchId",
                    element: <MatchDetails/>
                }
            ],
        },
    ]);

    return (
        <RouterProvider router={router}/>
    );
}

export default App;
