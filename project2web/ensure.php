<?php

/**
 * Verifies that the expected fields are set.
 * @param
 * @return true if successful, false if not
 */
function ensure($method) {
    if ($method == 'post') {
        // Ensure the userid post item exists
        if (!isset($_POST['user'])) {
            echo '<game status="no" msg="missing user" />';
            return false;
        }
        // Ensure the magic post item exists
        if (!isset($_POST['magic'])) {
            echo '<game status="no" msg="missing magic" />';
            return false;
        }
        // Ensure password post item exists
        if (!isset($_POST['pw'])) {
            echo '<game status="no" msg="missing password" />';
            return false;
        }
        // Ensure xml post item exists
        if (!isset($_POST['xml'])) {
            echo '<game status="no" msg="missing XML" />';
            return false;
        }

        if(!isset($_POST['magic']) || $_POST['magic'] != "NechAtHa6RuzeR8x") {
            echo '<game status="no" msg="magic" /&>';
            return false;
        }

        return true;
    } else if ($method == 'get') {

        // Ensure the userid post item exists
        if (!isset($_GET['user'])) {
            echo '<game status="no" msg="missing user" />';
            return false;
        }
        // Ensure the magic post item exists
        if (!isset($_GET['magic'])) {
            echo '<game status="no" msg="missing magic" />';
            return false;
        }
        // Ensure password post item exists
        if (!isset($_GET['pw'])) {
            echo '<game status="no" msg="missing password" />';
            return false;
        }

        if (!isset($_GET['magic']) || $_GET['magic'] != "NechAtHa6RuzeR8x") {
            echo '<game status="no" msg="magic" /&>';
            return false;
        }

        return true;
    } else return false;
}