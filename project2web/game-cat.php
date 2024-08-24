<?php
/**
 * Game catalog script
 */
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if (!ensure("get")) exit;

process($_GET['user'], $_GET['pw']);

/**
 * Executes a select query for the app catalog
 * @param $user the username of the user
 * @param $password the password of the user
 * @return void
 */
function process($user, $password) {
    // Connect to the database
    $pdo = pdo_connect();
    $userid = getUser($pdo, $user, $password);
    $query = "select id, name from game";
    $rows = $pdo->query($query);
    echo "<boardgame status=\"yes\">";
    foreach($rows as $row ) {
        $id = $row['id'];
        $name = $row['name'];

        echo "<game id=\"$id\" name=\"$name\" />\r\n";
    }
    echo "</boardgame>";

}
