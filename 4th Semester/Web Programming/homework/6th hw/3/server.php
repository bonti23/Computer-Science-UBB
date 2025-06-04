<?php
header("Content-Type: application/json; charset=UTF-8");

$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "DB error"]);
    exit;
}

$action = $_GET['action'] ?? '';

if ($action === "get_ids") {
    $result = $conn->query("SELECT id FROM persoane");
    $ids = [];
    while ($row = $result->fetch_assoc()) {
        $ids[] = $row['id'];
    }
    echo json_encode($ids);
}

elseif ($action === "get_data" && isset($_GET['id'])) {
    $id = intval($_GET['id']);
    $stmt = $conn->prepare("SELECT nume, prenume, email FROM persoane WHERE id = ?");
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $res = $stmt->get_result()->fetch_assoc();
    echo json_encode($res);
}

elseif ($action === "save") {
    $data = json_decode(file_get_contents("php://input"), true);
    $stmt = $conn->prepare("UPDATE persoane SET nume=?, prenume=?, email=? WHERE id=?");
    $stmt->bind_param("sssi", $data['nume'], $data['prenume'], $data['email'], $data['id']);
    $stmt->execute();
    echo json_encode(["success" => true]);
}

$conn->close();
