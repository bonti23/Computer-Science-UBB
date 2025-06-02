import React, { useState } from "react";
import "../CSS/loginCSS.css";
import basketballImg from "../images/ChatGPT Image Jun 2, 2025, 11_30_38 PM.png";

export default function Login({ onLoginSuccess, onSignUpClick }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleLogin = () => {
        fetch("http://localhost:8080/basket/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
        })
            .then(async (res) => {
                if (!res.ok) {
                    const err = await res.text();
                    throw new Error(err || "Login failed");
                }
                return res.text();
            })
            .then((token) => {
                localStorage.setItem("token", token);
                if (typeof onLoginSuccess === "function") {
                    onLoginSuccess(token);
                } else {
                    console.error("onLoginSuccess is not a function", onLoginSuccess);
                }
            })
            .catch((err) => {
                setErrorMessage(err.message);
            });
    };

    return (
        <div className="login-container">
            <img src={basketballImg} alt="Basketball" className="basketball-image" />

            <div className="login-box">
                <h2>Welcome, basket fan!</h2>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    autoComplete="username"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="current-password"
                />
                <button onClick={handleLogin}>Login</button>
                <p style={{ color: "red" }}>{errorMessage}</p>
                <p>
                    Don't have an account?{" "}
                    <span onClick={onSignUpClick}>Sign up</span>
                </p>
            </div>
        </div>
    );
}
