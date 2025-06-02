import React, { useState, useEffect } from "react";
import "../CSS/signupCSS.css";

export default function SignUp({ onSignUpSuccess, onCancel }) {
    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [isValid, setIsValid] = useState(false);

    useEffect(() => {
        setIsValid(name.trim() !== "" && username.trim() !== "" && password.trim() !== "");
        setErrorMessage("");
    }, [name, username, password]);

    const handleSignUp = () => {
        fetch("http://localhost:8080/basket/signup", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, username, password }),
        })
            .then(async (res) => {
                const data = await res.text(); // ← aici e diferența!

                if (!res.ok) {
                    throw new Error(data.error || data.message || "Signup failed");
                }

                alert(data.message || "Account created successfully!");
                onSignUpSuccess();
            })
            .catch((err) => {
                setErrorMessage(err.message);
            });
    };


    return (
        <div className="signup-container">
            <div className="signup-box">
                <h2>Sign Up</h2>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <div>
                    <button onClick={handleSignUp} disabled={!isValid}>
                        Sign Up
                    </button>
                    <button onClick={onCancel}>
                        Cancel
                    </button>
                </div>
                <p className="error-message">{errorMessage}</p>
            </div>
        </div>
    );
}
