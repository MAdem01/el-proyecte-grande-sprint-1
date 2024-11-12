import * as React from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import NavigationBar from './components/NavigationBar/NavigationBar.jsx';
import HomePage from './pages/HomePage/HomePage.jsx';
import PlayFootballPage from "./pages/playFootballPage/PlayFootballPage.jsx";
import RegisterPage from "./pages/registerPage/RegisterPage.jsx";
<<<<<<< HEAD
import ProfilePage from "./pages/profilePage/ProfilePage.jsx";
=======
import LoginPage from "./pages/loginPage/LoginPage.jsx";
>>>>>>> afcbdab2f3cb4c5486184d5e49774210616321fe


function Layout(){
    return (
        <>
            <NavigationBar />
            <Outlet />
        </>
    );
}


function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Layout />,
            children: [
                {
                    path: "/",
                    element: <HomePage/>
                },
                {
                    path: "/football-games",
                    element: <PlayFootballPage />
                },
                {
                    path: "/users/register",
                    element: <RegisterPage />
                },
                {
                    path: "/users/login",
                    element: <LoginPage />
                },
                {
                    path: "/user/:id",
                    element: <ProfilePage/>
                },
            ],
        },
    ]);

    return (
        <RouterProvider router={router} />
    );
}

export default App;
