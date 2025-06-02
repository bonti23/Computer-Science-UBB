import React, { useState } from "react";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import MainMenu from "./pages/MainMenu";
import Purchase from "./pages/Purchase";

export default function App() {
    const [view, setView] = useState("login"); // 'login' | 'signup' | 'main' | 'purchase'
    const [user, setUser] = useState(null); // { token }
    const [selectedGame, setSelectedGame] = useState(null);

    console.log("Current view:", view, "User:", user);

    const handleLoginSuccess = (token) => {
        setUser({ token });
        setView("main");
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        setUser(null);
        setView("login");
    };

    const handleSignUpSuccess = () => {
        setView("login");
    };

    const openSignUp = () => setView("signup");
    const cancelSignUp = () => setView("login");

    const openPurchase = (game) => {
        setSelectedGame(game);
        setView("purchase");
    };

    const backToMain = () => {
        setSelectedGame(null);
        setView("main");
    };

    return (
        <>
            {view === "login" && (
                <Login onLoginSuccess={handleLoginSuccess} onSignUpClick={openSignUp} />
            )}

            {view === "signup" && (
                <SignUp onSignUpSuccess={handleSignUpSuccess} onCancel={cancelSignUp} />
            )}

            {view === "main" && user && (
                <MainMenu
                    user={user}
                    onLogout={handleLogout}
                    onPurchaseClick={openPurchase}
                />
            )}

            {view === "purchase" && selectedGame && (
                <Purchase
                    game={selectedGame}
                    onPurchaseComplete={backToMain}
                    onCancel={backToMain}
                />
            )}
        </>
    );
}
