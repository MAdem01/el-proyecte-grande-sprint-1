import {Navigate} from "react-router-dom";

export default function ProtectedRoute({children}) {
    const token = localStorage.getItem("user");

    if (!token) {
        return <Navigate to="/users/login"/>;
    }

    return children;
}