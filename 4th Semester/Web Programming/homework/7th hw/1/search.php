<?php
header('Content-Type: application/json');


$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
$data = json_decode(file_get_contents("php://input"), true);
$plecare = $conn->real_escape_string($data['plecare']);
$sosire = $conn->real_escape_string($data['sosire']);
$legatura = $data['legatura'];

$rezultate = [];

$sql_direct = "SELECT * FROM trenuri WHERE plecare = '$plecare' AND sosire = '$sosire'";
$res = $conn->query($sql_direct);
while ($row = $res->fetch_assoc()) {
    $rezultate[] = [
        "descriere" => "Tren direct: {$row['nr_tren']} ({$row['tip_tren']}) - {$row['plecare']} {$row['ora_plecare']} → {$row['sosire']} {$row['ora_sosire']}"
    ];
}

if ($legatura) {
    $sql1 = "SELECT * FROM trenuri WHERE plecare = '$plecare'";
    $res1 = $conn->query($sql1);
    while ($r1 = $res1->fetch_assoc()) {
        $intermediar = $r1['sosire'];

        $ora1 = $r1['ora_sosire'];
        $sql2 = "SELECT * FROM trenuri
                 WHERE plecare = '$intermediar'
                   AND sosire = '$sosire'
                   AND ora_plecare > '$ora1'
                 ORDER BY ora_plecare ASC";

        $res2 = $conn->query($sql2);
        while ($r2 = $res2->fetch_assoc()) {
            $rezultate[] = [
                "descriere" => "Cu legătură: {$r1['nr_tren']} ({$r1['tip_tren']}) {$r1['plecare']} {$r1['ora_plecare']} → {$r1['sosire']} {$r1['ora_sosire']}, apoi {$r2['nr_tren']} ({$r2['tip_tren']}) {$r2['plecare']} {$r2['ora_plecare']} → {$r2['sosire']} {$r2['ora_sosire']}"
            ];
        }
    }
}

echo json_encode($rezultate);
