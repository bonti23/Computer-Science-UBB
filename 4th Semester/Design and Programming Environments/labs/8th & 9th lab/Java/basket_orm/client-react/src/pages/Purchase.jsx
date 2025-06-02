import React, { useState, useEffect } from "react";
import "../CSS/purchaseCSS.css";

export default function Purchase({ game, onPurchaseComplete, onCancel }) {
    const [clientName, setClientName] = useState("");
    const [address, setAddress] = useState("");
    const [seats, setSeats] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        setErrorMessage("");
    }, [clientName, address, seats]);

    const handlePurchase = () => {
        if (!clientName.trim() || !address.trim() || !seats.trim()) {
            setErrorMessage("All fields are required!");
            return;
        }

        const seatsNum = parseInt(seats, 10);
        if (isNaN(seatsNum) || seatsNum <= 0) {
            setErrorMessage("Number of seats must be a valid positive number!");
            return;
        }

        if (seatsNum > game.seats) {
            setErrorMessage("Not enough seats available!");
            return;
        }

        const purchaseData = {
            clientName,
            address,
            seats: seatsNum,
            gameId: game.id,
        };

        fetch("http://localhost:8080/basket/purchases", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(purchaseData),
        })
            .then(async (res) => {
                if (!res.ok) {
                    const err = await res.text();
                    throw new Error(err || "Purchase failed");
                }
                return res.text(); // aici schimbăm față de res.json()
            })
            .then((text) => {
                alert(text);  // afișăm mesajul text primit de la backend
                onPurchaseComplete();
            })
            .catch((err) => setErrorMessage(err.message));
    };

    return (
        <div className="purchase-container">
            <h2>Purchase Tickets</h2>
            <p>
                <strong>{game.teamA}</strong> vs <strong>{game.teamB}</strong> <br />
                <small>{new Date(game.date).toLocaleString()}</small>
            </p>
            <input
                type="text"
                placeholder="Client Name"
                value={clientName}
                onChange={(e) => setClientName(e.target.value)}
            />
            <input
                type="text"
                placeholder="Address"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
            />
            <input
                type="number"
                placeholder="Number of seats"
                value={seats}
                onChange={(e) => setSeats(e.target.value)}
                min="1"
                max={game.seats}
            />

            <div className="purchase-buttons">
                <button onClick={handlePurchase}>Purchase</button>
                <button onClick={onCancel}>Cancel</button>
            </div>

            <p className="error-message">{errorMessage}</p>
        </div>
    );

}
