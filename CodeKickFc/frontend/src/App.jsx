import * as React from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import NavigationBar from './components/NavigationBar/NavigationBar.jsx';
import HomePage from './pages/HomePage/HomePage.jsx';


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
                    path: "/football-games"

                },
                {
                    path: "/users/register",
                },
                {
                    path: "/users/login",
                },
            ],
        },
    ]);

    return (
        <RouterProvider router={router} />
    );
}

export default App;
