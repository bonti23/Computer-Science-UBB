import React, { useState, useEffect } from "react";

export default function AddGame({ gameData, onGameAdded, onCancel }) {
    const [teamA, setTeamA] = useState("");
    const [teamB, setTeamB] = useState("");
    const [date, setDate] = useState("");
    const [price, setPrice] = useState("");
    const [seats, setSeats] = useState("");
    const [type, setType] = useState("");

    useEffect(() => {
        if (gameData) {
            setTeamA(gameData.teamA);
            setTeamB(gameData.teamB);
            setDate(new Date(gameData.date).toISOString().slice(0, 16)); // format pentru input type=datetime-local
            setPrice(gameData.price);
            setSeats(gameData.seats);
            setType(gameData.type);
        }
    }, [gameData]);

    const handleSubmit = (e) => {
        e.preventDefault();

        const payload = {
            teamA,
            teamB,
            date: new Date(date).toISOString(),
            price: parseFloat(price),
            seats: parseInt(seats),
            type,
        };

        const method = gameData ? "PUT" : "POST";
        const url = gameData
            ? `http://localhost:8080/basket/games/${gameData.id}`
            : "http://localhost:8080/basket/games";

        fetch(url, {
            method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(payload),
        })
            .then((res) => {
                if (!res.ok) throw new Error("Failed to save game");
                return res.json();
            })
            .then(() => {
                onGameAdded();
            })
            .catch(() => alert("Error saving game"));
    };

    return (
        <div className="add-game-modal">
            <form onSubmit={handleSubmit}>
                <h3>{gameData ? "Edit Game" : "Add New Game"}</h3>
                <label>Team A: <input value={teamA} onChange={e => setTeamA(e.target.value)} required /></label>
                <label>Team B: <input value={teamB} onChange={e => setTeamB(e.target.value)} required /></label>
                <label>Date: <input type="datetime-local" value={date} onChange={e => setDate(e.target.value)} required /></label>
                <label>Price: <input type="number" step="0.01" value={price} onChange={e => setPrice(e.target.value)} required /></label>
                <label>Seats: <input type="number" value={seats} onChange={e => setSeats(e.target.value)} required /></label>
                <label>Type: <input value={type} onChange={e => setType(e.target.value)} required /></label>

                <button type="submit">{gameData ? "Save Changes" : "Add Game"}</button>
                <button type="button" onClick={onCancel}>Cancel</button>
            </form>
        </div>
    );
}
