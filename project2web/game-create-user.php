<?php
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

create_user($_POST['name'], $_POST['password']);
function create_user($name, $password){
    $pdo = pdo_connect();
    $nameQ = $pdo->quote($name);
    $passQ = $pdo->quote($password);
    $query = <<<QUERY
INSERT INTO gameuser(user, password) VALUES ($nameQ, $passQ)
QUERY;
    if (!$pdo->query($query)) {
        echo "<game status=\"no\" msg=\"Insert error\"/>";
        exit;
    }
    echo "<game status=\"yes\" msg=\"User created\"/>";
    exit;

}