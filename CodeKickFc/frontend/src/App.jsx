import * as React from "react";
import {createRoot} from "react-dom/client";
import {createBrowserRouter, RouterProvider,} from "react-router-dom";

function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <div></div>
        },
        {
            path: "/football-games",
            element: <div></div>
        },
        {
            path: "/users/register",
            element: <div></div>
        },
        {
            path: "/users/login",
            element: <div></div>
        }
    ])

    createRoot(document.getElementById("root")).render(
        <RouterProvider router={router} />
    );
}

export default App
