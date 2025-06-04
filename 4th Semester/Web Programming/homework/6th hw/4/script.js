let board = [["", "", ""], ["", "", ""], ["", "", ""]];
let human = Math.random() < 0.5 ? "X" : "O";
let computer = human === "X" ? "O" : "X";
let currentPlayer = "X";
let gameOver = false;

document.getElementById("status").textContent = `you are: ${human}`;

function createBoard() {
    const table = document.getElementById("board");
    table.innerHTML = "";
    for (let i = 0; i < 3; i++) {
        const row = document.createElement("tr");
        for (let j = 0; j < 3; j++) {
            const cell = document.createElement("td");
            cell.dataset.row = i;
            cell.dataset.col = j;
            cell.textContent = board[i][j];
            if (!board[i][j] && !gameOver) {
                cell.addEventListener("click", handleClick);
            } else {
                cell.classList.add("disabled");
            }
            row.appendChild(cell);
        }
        table.appendChild(row);
    }
}

function handleClick(e) {
    if (gameOver || currentPlayer !== human) return;
    const row = parseInt(e.target.dataset.row);
    const col = parseInt(e.target.dataset.col);
    if (board[row][col] !== "") return;

    board[row][col] = human;
    currentPlayer = computer;
    updateUI();

    fetch("game.php", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ board, player: human })
    })
        .then(res => res.json())
        .then(data => {
            board = data.board;
            currentPlayer = human;
            updateUI();
            if (data.result !== "continue") {
                document.getElementById("status").textContent = data.result;
                gameOver = true;
            }
        });
}

function updateUI() {
    createBoard();
}

if (currentPlayer !== human) {
    fetch("game.php", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ board, player: human })
    })
        .then(res => res.json())
        .then(data => {
            board = data.board;
            currentPlayer = human;
            updateUI();
        });
} else {
    createBoard();
}
