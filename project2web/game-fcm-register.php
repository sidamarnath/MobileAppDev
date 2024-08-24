<?php
/**
 * Saves a fcm token to the database
 */
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
//if(!ensure("post")) exit;

if(isset($_POST["fcmid"])) {
    register($_POST["user"], $_POST["fcmid"]);
} else {
    echo "<fcm status=\"no\" msg=\"No fcmid\" />";
}
function register($user, $token) {
    $pdo = pdo_connect();
    $id = $pdo->quote($token);
    $user = $pdo->quote($user);
    $query = "replace into fcm(user, id) values($user, $id)";
    if(!$pdo->query($query)) {
        echo "<fcm status=\"no\" msg=\"Insertion error\"/>";
        exit;
    }
    echo "<fcm status=\"yes\" msg=\"Token saved successfully\"/>";
    exit;
}