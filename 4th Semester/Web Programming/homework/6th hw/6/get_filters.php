<?php
header('Content-Type: application/json');

$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
$data = [];

$fields = ['producator', 'procesor', 'memorie'];

foreach ($fields as $field) {
    $result = $conn->query("SELECT DISTINCT $field FROM notebooks ORDER BY $field ASC");
    $values = [];
    while ($row = $result->fetch_assoc()) {
        $values[] = $row[$field];
    }
    $data[$field] = $values;
}

echo json_encode($data);
