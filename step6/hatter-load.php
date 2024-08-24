<?php
require_once "db.inc.php";

/*
 * Hatter application load operation
 */
require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if(!isset($_GET['magic']) || $_GET['magic'] != "NechAtHa6RuzeR8x") {
    echo '<hatter status="no" msg="magic" />';
    exit;
}

if(!isset($_GET['id']) || !isset($_GET['user']) || !isset($_GET['pw'])) {
    echo '<hatter status="no" msg="parameter" />';
    exit;
}

// Process in a function
process($_GET['id'], $_GET['user'], $_GET['pw']);

/**
 * Process the query
 * @param $id the hatting ID to load
 * @param $user the user name
 * @param $password the user password
 */
function process($id, $user, $password) {
    // Connect to the database
    $pdo = pdo_connect();

    $userid = getUser($pdo, $user, $password);
    $idQ = $pdo->quote($id);
    $query = "select name, uri, type, x, y, angle, scale, color, feather from hatting where id=$idQ";
    $rows = $pdo->query($query);

    if($row = $rows->fetch()) {
        // We found the record in the database
        $name = $row['name'];
        $uri = $row['uri'];
        $type = $row['type'];
        $x = $row['x'];
        $y = $row['y'];
        $angle = $row['angle'];
        $scale = $row['scale'];
        $color = $row['color'];
        $feather = ($row['feather'] == 1) ? "yes" : "no";

        echo "<hatter status=\"yes\">/r/n";
        echo "<hatting id=\"$id\" name=\"$name\" uri=\"$uri\" type=\"$type\" x=\"$x\" y=\"$y\" angle=\"$angle\" scale=\"$scale\" color=\"$color\" feather=\"$feather\" />\r\n";
        echo "</hatter>";
    } else {
        echo '<hatter status="no" msg="image" />';
        exit;
    }
}

/**
 * Ask the database for the user ID. If the user exists, the password
 * must match.
 * @param $pdo PHP Data Object
 * @param $user The user name
 * @param $password Password
 * @return id if successful or exits if not
 */
function getUser($pdo, $user, $password) {
    // Does the user exist in the database?
    $userQ = $pdo->quote($user);
    $query = "SELECT id, password from hatteruser where user=$userQ";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // We found the record in the database
        // Check the password
        if($row['password'] != $password) {
            echo '<hatter status="no" msg="password error" />';
            exit;
        }

        return $row['id'];
    }

    echo '<hatter status="no" msg="user error" />';
    exit;
}

