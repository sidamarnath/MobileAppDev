<?php
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

function load($pdo, $user, $id) {
    $query = "SELECT id, name, hostid, guestid, state FROM game WHERE id=$id";
    $rows = $pdo->query($query);
    if ($row = $rows->fetch()) {
        // game found
        $name = $row['name'];
        $hostid = $row['hostid'];
        $guestid = $row['guestid'];
        $state = $row['state'];

        echo "<game status=\"yes\">\n<game id=\"$id\" name=\"$name\" hostid=\"$hostid\" guestid=\"$guestid\" state=\"$state\" />\r\n</game>";
        exit;
    }
    echo '<game status="no" msg="load failed" />';
    exit;
}

if (!ensure('get')) exit;
$pdo = pdo_connect();
$user = getUser($pdo, $_GET['user'], $_GET['pw']);
if (!isset($_GET['id'])) {
    echo '<game status="no" msg="missing load id" />';
    exit;
}
$id = $_GET['id'];
if (!is_numeric($id)) {
    echo '<game status="no" msg="id not numeric" />';
}
load($pdo, $user, $id);
