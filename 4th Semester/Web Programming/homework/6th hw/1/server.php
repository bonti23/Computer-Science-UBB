<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
header("Content-Type: text/html; charset=UTF-8");

$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
if ($conn->connect_error) {
    die("Conexiunea a eșuat: " . $conn->connect_error);
}

$plecare = $_GET['plecare'] ?? '';

$options = '<option value="">-- Alege sosirea --</option>';

if (!empty($plecare)) {
    $stmt = $conn->prepare("SELECT oras_sosire FROM rute WHERE oras_plecare = ?");
    $stmt->bind_param("s", $plecare);
    $stmt->execute();
    $result = $stmt->get_result();

    while ($row = $result->fetch_assoc()) {
        $oras = htmlspecialchars($row['oras_sosire']);
        $options .= "<option value=\"$oras\">$oras</option>";
    }

    $stmt->close();
}

$conn->close();
echo $options;
