import React, { useEffect, useState } from "react";
import "../CSS/mainmenuCSS.css";
import AddGame from "./AddGame"; // putem folosi AddGame și pentru editare

export default function MainMenu({ user, onLogout, onPurchaseClick }) {
    const [games, setGames] = useState([]);
    const [gameTypes, setGameTypes] = useState([]);
    const [selectedType, setSelectedType] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [selectedGameId, setSelectedGameId] = useState(null);
    const [showAddEditGame, setShowAddEditGame] = useState(false);
    const [editGameData, setEditGameData] = useState(null); // null = adăugare, obiect = editare

    useEffect(() => {
        loadGames();
    }, []);

    const loadGames = () => {
        fetch("http://localhost:8080/basket/games", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then((res) => {
                if (!res.ok) throw new Error("Failed to load games");
                return res.json();
            })
            .then((data) => {
                setGames(data);
                const types = [...new Set(data.map((g) => g.type))];
                setGameTypes(types);
                if (types.length > 0) setSelectedType(types[0]);
            })
            .catch(() => setErrorMessage("Failed to load games"));
    };

    const filteredGames = selectedType
        ? games.filter((g) => g.type === selectedType)
        : games;

    const handlePurchase = () => {
        const selectedGame = filteredGames.find((g) => g.id === selectedGameId);
        if (!selectedGame) {
            setErrorMessage("Please select a game to purchase tickets.");
            return;
        }
        if (selectedGame.seats === 0) {
            setErrorMessage("There are no more tickets left.");
            return;
        }
        setErrorMessage("");
        onPurchaseClick(selectedGame);
    };

    // Deschide formularul AddGame pentru adăugare
    const handleAddNew = () => {
        setEditGameData(null);
        setShowAddEditGame(true);
    };

    // Deschide formularul AddGame pentru editare cu datele jocului selectat
    const handleEdit = () => {
        if (!selectedGameId) {
            setErrorMessage("Please select a game to edit.");
            return;
        }
        const gameToEdit = games.find(g => g.id === selectedGameId);
        setEditGameData(gameToEdit);
        setShowAddEditGame(true);
    };

    // Șterge jocul selectat după confirmare
    const handleDelete = () => {
        if (!selectedGameId) {
            setErrorMessage("Please select a game to delete.");
            return;
        }
        if (!window.confirm("Are you sure you want to delete this game?")) {
            return;
        }
        fetch(`http://localhost:8080/basket/games/${selectedGameId}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => {
                if (!res.ok) throw new Error("Failed to delete game");
                setErrorMessage("");
                setSelectedGameId(null);
                loadGames();
            })
            .catch(() => setErrorMessage("Failed to delete game"));
    };

    return (
        <div className="mainmenu-container">
            <div className="mainmenu-box">
                <h2>TIMETABLE</h2>
                <button onClick={onLogout}>Logout</button>
                <button onClick={handleAddNew}>Add New Game</button>
                <button onClick={handleEdit} disabled={!selectedGameId}>Edit Selected Game</button>
                <button onClick={handleDelete} disabled={!selectedGameId}>Delete Selected Game</button>

                {showAddEditGame && (
                    <AddGame
                        gameData={editGameData}  // dacă e null, e adăugare, altfel editare
                        onGameAdded={() => {
                            loadGames();
                            setShowAddEditGame(false);
                            setSelectedGameId(null);
                        }}
                        onCancel={() => setShowAddEditGame(false)}
                    />
                )}

                <div style={{ marginTop: 20 }}>
                    <label>
                        Filter by game type:{" "}
                        <select
                            value={selectedType}
                            onChange={(e) => setSelectedType(e.target.value)}
                        >
                            {gameTypes.map((type) => (
                                <option key={type} value={type}>
                                    {type}
                                </option>
                            ))}
                        </select>
                    </label>
                </div>

                <table>
                    <thead>
                    <tr>
                        <th></th>
                        <th>Team A</th>
                        <th>Team B</th>
                        <th>Date</th>
                        <th>Price</th>
                        <th>Seats</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredGames.map((game) => (
                        <tr
                            key={game.id}
                            style={{
                                backgroundColor: game.seats === 0 ? "#fdd" : "transparent",
                            }}
                        >
                            <td>
                                <input
                                    type="radio"
                                    name="selectedGame"
                                    value={game.id}
                                    checked={selectedGameId === game.id}
                                    onChange={() => setSelectedGameId(game.id)}
                                />
                            </td>
                            <td>{game.teamA}</td>
                            <td>{game.teamB}</td>
                            <td>{new Date(game.date).toLocaleString()}</td>
                            <td>{game.price.toFixed(2)}</td>
                            <td>{game.seats}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                <div style={{ marginTop: 10 }}>
                    <button onClick={handlePurchase} disabled={!selectedGameId}>
                        Purchase Tickets
                    </button>
                    <p className="error-message">{errorMessage}</p>
                </div>
            </div>
        </div>
    );
}
