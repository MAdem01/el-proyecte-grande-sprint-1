import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import NavigationBar from './components/NavigationBar/NavigationBar.jsx';
import HomePage from './pages/HomePage/HomePage.jsx';
import PlayFootballPage from "./pages/playFootballPage/PlayFootballPage.jsx";
import RegisterPage from "./pages/registerPage/RegisterPage.jsx";
import ProfilePage from "./pages/profilePage/ProfilePage.jsx";
import MatchDetails from "./pages/matchDetails/MatchDetails.jsx";
import LoginPage from "./pages/loginPage/LoginPage.jsx";
import PaymentPage from "./pages/paymentPage/PaymentPage.jsx";
import AdminPage from "./pages/adminPage/AdminPage.jsx";
import ProtectedRoute from "./ProtectedRoute.jsx";


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
            element: <Layout
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
                    path: "/admin/addmatch",
                    element: <AdminPage/>
                },
                {
                    path: "/users/register",
                    element: <RegisterPage/>
                },
                {
                    path: "/users/login",
                    element: <LoginPage/>
                },
                {
                    path: "/user/:id",
                    element: (
                        <ProtectedRoute>
                            <ProfilePage/>
                        </ProtectedRoute>
                    )
                },
                {
                    path: "/matchdetails/:matchId",
                    element: <MatchDetails/>
                },
                {
                    path: "/payment/:userId/:matchId",
                    element: (
                        <ProtectedRoute>
                            <PaymentPage/>
                        </ProtectedRoute>
                    )
                }
            ],
        },
    ]);

    return (
        <RouterProvider router={router}/>
    );
}

export default App;
