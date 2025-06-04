<?php
header('Content-Type: application/json');

$data = json_decode(file_get_contents('php://input'), true);
$board = $data['board'];
$human = $data['player'];
$computer = $human === 'X' ? 'O' : 'X';

function checkWinner($b) {
    $lines = [
        [$b[0][0], $b[0][1], $b[0][2]],
        [$b[1][0], $b[1][1], $b[1][2]],
        [$b[2][0], $b[2][1], $b[2][2]],
        [$b[0][0], $b[1][0], $b[2][0]],
        [$b[0][1], $b[1][1], $b[2][1]],
        [$b[0][2], $b[1][2], $b[2][2]],
        [$b[0][0], $b[1][1], $b[2][2]],
        [$b[0][2], $b[1][1], $b[2][0]],
    ];

    foreach ($lines as $line) {
        if ($line[0] && $line[0] === $line[1] && $line[1] === $line[2]) {
            return $line[0];
        }
    }

    foreach ($b as $row) {
        foreach ($row as $cell) {
            if ($cell === "") return null;
        }
    }

    return "draw";
}

$result = checkWinner($board);
if ($result) {
    echo json_encode([
        "board" => $board,
        "result" => $result === "draw" ? "draw" : "$result won!"
    ]);
    exit;
}

for ($i = 0; $i < 3; $i++) {
    for ($j = 0; $j < 3; $j++) {
        if ($board[$i][$j] === "") {
            $board[$i][$j] = $computer;
            break 2;
        }
    }
}

$result = checkWinner($board);
echo json_encode([
    "board" => $board,
    "result" => $result ? ($result === "draw" ? "draw" : "$result won!") : "continue"
]);
