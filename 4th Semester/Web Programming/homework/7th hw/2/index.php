<?php
$host = "localhost";
$user = "";
$pass = "";
$dbname = "";

$conn = new mysqli($host, $user, $pass, $dbname);
if ($conn->connect_error) {
    die("Conexiunea a eșuat: " . $conn->connect_error);
}

$perPage = isset($_GET['perPage']) ? (int)$_GET['perPage'] : 5;
if ($perPage <= 0) $perPage = 5;

$page = isset($_GET['page']) ? (int)$_GET['page'] : 1;
if ($page <= 0) $page = 1;

$offset = ($page - 1) * $perPage;

$totalSql = "SELECT COUNT(*) as total FROM produse";
$totalResult = $conn->query($totalSql);
$totalRow = $totalResult->fetch_assoc();
$total = $totalRow['total'];
$totalPages = ceil($total / $perPage);

$sql = "SELECT * FROM produse LIMIT $perPage OFFSET $offset";
$result = $conn->query($sql);
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Produse paginate</title>
</head>
<body>
<h2>Lista produse</h2>

<form method="get" id="formPerPage">
    <label for="perPage">Produse per pagină:</label>
    <select name="perPage" id="perPage" onchange="document.getElementById('formPerPage').submit()">
        <?php
        foreach ([3, 5, 10, 20] as $option) {
            $selected = ($option == $perPage) ? "selected" : "";
            echo "<option value=\"$option\" $selected>$option</option>";
        }
        ?>
    </select>
</form>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nume</th>
        <th>Descriere</th>
        <th>Preț (RON)</th>
    </tr>
    </thead>
    <tbody>
    <?php while ($row = $result->fetch_assoc()): ?>
        <tr>
            <td><?= htmlspecialchars($row['id']) ?></td>
            <td><?= htmlspecialchars($row['nume']) ?></td>
            <td><?= htmlspecialchars($row['descriere']) ?></td>
            <td><?= number_format($row['pret'], 2, '.', '') ?></td>
        </tr>
    <?php endwhile; ?>
    </tbody>
</table>

<!-- Navigare pagini -->
<div style="margin-top: 20px;">
    <?php if ($page > 1): ?>
        <a href="?page=<?= $page - 1 ?>&perPage=<?= $perPage ?>">⟨ Anterior</a>
    <?php endif; ?>

    <?php for ($i = 1; $i <= $totalPages; $i++): ?>
        <?php if ($i == $page): ?>
            <strong><?= $i ?></strong>
        <?php else: ?>
            <a href="?page=<?= $i ?>&perPage=<?= $perPage ?>"><?= $i ?></a>
        <?php endif; ?>
    <?php endfor; ?>

    <?php if ($page < $totalPages): ?>
        <a href="?page=<?= $page + 1 ?>&perPage=<?= $perPage ?>">Următor ⟩</a>
    <?php endif; ?>
</div>

</body>
</html>
