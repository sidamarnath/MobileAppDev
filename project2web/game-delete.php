<?php
/**
 * Game delete query script
 */
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if (!ensure("get")) exit;

delete($_GET['id']);
/**
 * Delete query handler
 * @param $idQ the id of the game being deleted
 * @return void
 */
function delete($idQ) {
    $pdo = pdo_connect();
    $user = getUser($pdo, $_GET['user'], $_GET['pw']);
    $query = "delete from game where id=$idQ";
    $result = $pdo->query($query);
    if (!$result) {
        echo '<game status="no" msg="delete failed" />';
    } else {
        echo '<game status="yes" msg="delete successful" />';
    }

}
