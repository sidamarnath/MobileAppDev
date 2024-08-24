<?php
$title = "Project 2 Test Page";
//
//  The user and password below are those you used in your
//  android application.
//

$user = "";
$password = "";

$xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
<game 
name='examplename' 
hostid='1' 
guestid='' 
state='status' 
/>";

$base_url = "https://webdev.cse.msu.edu/~moranti1/cse476/project2Web/"; // verify that this is the correct path to your web site
$magic = "NechAtHa6RuzeR8x";

function setUserPass($username, $pass)
{
    echo "<script>let users = document.getElementsByName('user');
    for (let i = 0; i < users.length; i++) users[i].value='$username';</script>";
    echo "<script>let pws = document.getElementsByName('pw');
    for (let i = 0; i < pws.length; i++) pws[i].value='$pass';</script>";
}

?>
<html>
<head>
    <title><?php echo $title; ?></title>
</head>
<body>
<h1><?php echo $title; ?></h1>
<h2>Game Save Testing</h2>
<p>Select your user id and input your XML.</p>
<p>XML example (copy and paste to XML): <input type="text" value="<?php echo $xml;?>"</p>

<form method="post">
    <p><button style="margin:5px;" name="capozzo3">capozzo3</button><button style="margin:5px;" name="moranti1">moranti1</button>
        <button style="margin:5px;" name="amarnat4">amarnat4</button><button style="margin:5px;" name="imtiazay">imtiazay</button>
        <button style="margin:5px;" name="mathura5">mathura5</button>
    </p>
</form>
<form method="post" target="_blank" action="<?php echo $base_url; ?>game-save.php">
    <p>Userid: <input type="text" name="user" value="<?php echo $user;?>"/></p>
    <p>Password: <input type="text" name="pw" value="<?php echo $password;?>"/></p>
    <p>magic: <input type="text" name="magic" value="<?php echo $magic;?>"/></p>
    <p>XML: <textarea name="xml"></textarea></p>
    <p><input type="submit" value="Test Save" /></p>
</form>

<hr />
<h2>Games Catalog Testing</h2>
<form method="get" target="_blank" action="<?php echo $base_url; ?>game-cat.php">
    <input type="hidden" name="magic" value="<?php echo $magic; ?>" />
    <input type="hidden" name="user" value="<?php echo $user; ?>" />
    <input type="hidden" name="pw" value="<?php echo $password; ?>" />
    <input type="submit" value="Test Catalog" />
</form>

<hr />
<h2>Game Load Testing</h2>
<p>Enter the id for a hatting in the input box below then click the "Test Load" button to test.</p>
<form method="get" target="_blank" action="<?php echo $base_url; ?>game-load.php">
    <input type="hidden" name="magic" value="<?php echo $magic; ?>" />
    <input type="hidden" name="user" value="<?php echo $user; ?>" />
    <input type="hidden" name="pw" value="<?php echo $password; ?>" />
    <p>Game ID to load: <input type="text" name="id" value="" /></p>
    <p> <input type="submit" value="Test Load" /></p>
</form>

<hr />
<h2>Game Delete Testing</h2>
<p>Enter the id of the game you want to delete.</p>
<form method="get" target="_blank" action="<?php echo $base_url; ?>game-delete.php">
    <input type="hidden" name="magic" value="<?php echo $magic; ?>" />
    <input type="hidden" name="user" value="<?php echo $user; ?>" />
    <input type="hidden" name="pw" value="<?php echo $password; ?>" />
    <p>Game ID to delete: <input type="text" name="id" value="" /></p>
    <p> <input type="submit" value="Test Delete" /></p>
</form>

<hr/>
<h2>Login Testing</h2>
<p>Enter the username and password </p>
<form method="post" target="_blank" action="<?php echo $base_url; ?>game-login.php">
    <p>Username <input type="text" name="name" value=""/></p>
    <p>Password <input type="text" name="password" value=""/></p>
    <p> <input type="submit" value="Test Login" /></p>
</form>

<hr/>
<h2>Create User Testing</h2>
<p>Enter the username and password of the user to be created </p>
<form method="post" target="_blank" action="<?php echo $base_url; ?>game-create-user.php">
    <p>Username <input type="text" name="name" value=""/></p>
    <p>Password <input type="text" name="password" value=""/></p>
    <p> <input type="submit" value="Test Create User" /></p>
</form>

<hr/>
<h2>FCM Saving Testing</h2>
<p>Enter the username and token </p>
<form method="post" target="_blank" action="<?php echo $base_url; ?>game-fcm-register.php">
    <p>Username <input type="text" name="user" value=""/></p>
    <p>FCM Id <input type="text" name="fcmid" value=""/></p>
    <p> <input type="submit" value="Test FCM Save" /></p>
</form>

<?php
if(isset($_POST['capozzo3'])) setUserPass("capozzo3", "PleaseWork");
if(isset($_POST['moranti1'])) setUserPass("moranti1", "password");
if(isset($_POST['amarnat4'])) setUserPass("amarnat4", "set in Project2Testing.php");
if(isset($_POST['imtiazay'])) setUserPass("imtiazay", "set in Project2Testing.php");
if(isset($_POST['mathura5'])) setUserPass("mathura5", "set in Project2Testing.php");
?>

</body>
</html>