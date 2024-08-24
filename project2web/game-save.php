<?php
/**
 * Game save
 */
require_once "db.inc.php";
require_once "get-user.php";
require_once "ensure.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

function save($pdo, $user, $xmltext) {
    $xml = new XMLReader();
    if (!$xml->XML($xmltext)) {
        echo '<game status="no" msg="invalid XML" />';
        exit;
    }

    while ($xml->read()) {
        if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == "game") {
            $name = $xml->getAttribute("name");
            $hostid = $xml->getAttribute("hostid");
            $guestid = $xml->getAttribute("guestid");
            if ($guestid == '') $gidQ = "NULL";
            $state = $xml->getAttribute("state");

            // sanitization
            $nameQ = $pdo->quote($name);
            $hidQ = $pdo->quote($hostid);
            if ($guestid != '') $gidQ = $pdo->quote($guestid);
            $stateQ = $pdo->quote($state);

            $query = <<<QUERY
REPLACE INTO game(name, hostid, guestid, state)
    VALUES($nameQ, $hidQ, $gidQ, $stateQ)
QUERY;

            //echo "\n\n Query: $query";

            if (!$pdo->query($query)) {
                echo '<game status="no" msg="insertfail">' . $query . '</game>';
                exit;
            }

            echo '<game status="yes" msg="save successful" />';
            exit;

        }
        echo '<game status="no" msg="invalid XML" />';
        exit;
    }
}

if (!ensure('post')) exit;
$pdo = pdo_connect();
$user = getUser($pdo, $_POST['user'], $_POST['pw']);
save($pdo, $user, stripslashes($_POST['xml']));
