<?php
$baseDir = realpath(__DIR__);

if (isset($_GET['file'])) {
    $file = realpath($baseDir . '/' . $_GET['file']);
    if (strpos($file, $baseDir) === 0 && is_file($file)) {
        header('Content-Type: text/plain; charset=UTF-8');
        echo file_get_contents($file);
    } else {
        http_response_code(403);
        echo "Acces interzis.";
    }
    exit;
}

if (isset($_GET['path'])) {
    $path = realpath($baseDir . '/' . $_GET['path']);
    if (strpos($path, $baseDir) !== 0 || !is_dir($path)) {
        http_response_code(403);
        header('Content-Type: application/json');
        echo json_encode([]);
        exit;
    }

    $items = [];
    foreach (scandir($path) as $item) {
        if ($item === '.' || $item === '..') continue;
        $fullPath = $path . '/' . $item;
        $relativePath = substr($fullPath, strlen($baseDir) + 1);
        $items[] = [
            'name' => $item,
            'path' => $relativePath,
            'type' => is_dir($fullPath) ? 'dir' : 'file'
        ];
    }

    header('Content-Type: application/json');
    echo json_encode($items);
    exit;
}

header('Content-Type: application/json');
echo json_encode([]);
