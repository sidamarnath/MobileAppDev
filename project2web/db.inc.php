<?php

function pdo_connect() {
    try {
        // Production server
        $dbhost="mysql:host=mysql-user.cse.msu.edu;dbname=imtiazay";
        $user = "imtiazay";
        $password = "purple2";
        return new PDO($dbhost, $user, $password);
    } catch(PDOException $e) {
        echo '<game status="no" msg="Unable to select database" />';
        exit;
    }
}
