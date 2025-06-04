<?php
header("Content-Type: application/json; charset=UTF-8");

$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "Eroare conexiune DB"]);
    exit;
}

$page = intval($_GET['page'] ?? 0);
$limit = intval($_GET['limit'] ?? 3);
$offset = $page * $limit;

$stmt = $conn->prepare("SELECT nume, prenume, telefon, email FROM contacte LIMIT ? OFFSET ?");
$stmt->bind_param("ii", $limit, $offset);
$stmt->execute();
$result = $stmt->get_result();

$records = [];
while ($row = $result->fetch_assoc()) {
    $records[] = $row;
}

$stmt2 = $conn->prepare("SELECT COUNT(*) AS total FROM contacte");
$stmt2->execute();
$totalResult = $stmt2->get_result()->fetch_assoc();
$total = intval($totalResult['total']);

echo json_encode([
    "records" => $records,
    "hasMore" => ($offset + $limit) < $total
]);

$conn->close();
?>
