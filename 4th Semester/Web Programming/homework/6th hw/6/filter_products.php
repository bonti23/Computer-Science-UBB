<?php
header('Content-Type: application/json');

$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
$data = json_decode(file_get_contents('php://input'), true);

$sql = "SELECT * FROM notebooks WHERE 1=1";

if (!empty($data['producator'])) {
    $p = $conn->real_escape_string($data['producator']);
    $sql .= " AND producator = '$p'";
}
if (!empty($data['procesor'])) {
    $p = $conn->real_escape_string($data['procesor']);
    $sql .= " AND procesor = '$p'";
}
if (!empty($data['memorie'])) {
    $m = intval($data['memorie']);
    $sql .= " AND memorie = $m";
}

$result = $conn->query($sql);

$rows = [];
while ($row = $result->fetch_assoc()) {
    $rows[] = $row;
}

echo json_encode($rows);
