<?php
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';


login($_POST['name'], $_POST['password']);

function login($name, $password) {
    $pdo = pdo_connect();
    $nameQ = $pdo->quote($name);
    $exists = "SELECT id, password FROM gameuser WHERE user = $nameQ";
    $result = $pdo->query($exists);
    if ($row = $result->fetch()) {
        //User already exists
        if($row['password'] != $password) {
            echo '<game status="no" msg="Invalid password" />';
            exit;
        }
        $id = $row['id'];

        echo "<game status=\"yes\" id=\"$id\" user=\"$name\"/>";
        exit;
    }
    echo "<game status=\"no\" msg=\"User does not exist\"/>";
    exit;
}