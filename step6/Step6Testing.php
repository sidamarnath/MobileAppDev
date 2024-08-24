<?php
$title = "CSE476 Step 6 Test Page";
//
//  The user and password below are those you used in your
//  android application.
//
$user = "amarnat4";  // verify that your userid is correct
$password = "themaestro"; // change this to use your application password

$base_url = "https://webdev.cse.msu.edu/~amarnat4/cse476/step6/"; // verify that this is the correct path to your web site
$magic = "NechAtHa6RuzeR8x";
?>
<html>
<head>
    <title><?php echo $title; ?></title>
</head>
<body>
<h1><?php echo $title; ?></h1>
<h2>Hatter Save Testing</h2>
<p>Paste the valid xml created to save a hatting in the input box below,
    then click the "Test Save" Button. On the new page that appears use the
    browser "View page source" option to see the results.</p>
<form method="post" target="_blank" action="<?php echo $base_url; ?>hatter-save.php">
    <p>Userid: <input type="text" name="user" value="<?php echo $user;?>"/></p>
    <p>magic: <input type="text" name="magic" value="<?php echo $magic;?>"/></p>
    <p>Password: <input type="text" name="pw" value="<?php echo $password;?>"/></p>
    <p>XML: <textarea name="xml"></textarea></p>
    <p><input type="submit" value="Test Save" /></p>
</form>
<hr />
<h2>Hatter Catalog Testing</h2>
<form method="get" target="_blank" action="<?php echo $base_url; ?>hatter-cat.php">
    <input type="hidden" name="magic" value="<?php echo $magic; ?>" />
    <input type="hidden" name="user" value="<?php echo $user; ?>" />
    <input type="hidden" name="pw" value="<?php echo $password; ?>" />
    <input type="submit" value="Test Catalog" />
</form>

<hr />
<h2>Hatter Load Testing</h2>
<p>Enter the id for a hatting in the input box below then click the "Test Load" button to test.</p>
<form method="get" target="_blank" action="<?php echo $base_url; ?>hatter-load.php">
    <input type="hidden" name="magic" value="<?php echo $magic; ?>" />
    <input type="hidden" name="user" value="<?php echo $user; ?>" />
    <input type="hidden" name="pw" value="<?php echo $password; ?>" />
    <p>Hatter ID: <input type="text" name="id" value="" /></p>
    <p> <input type="submit" value="Test Load" /></p>
</form>

<hr />
<h2>Hatter Delete Testing</h2>
<p>Enter the id for a hatting in the input box below then click the "Test Load" button to test. Looking at the results of this
    is different than the other pages. If you View Page Source with the delete you will probably see the status as No and the messages
    as failed. This is because the view source causes sends another request which tries to delete a hatting that has already been deleted. Instead use the "Inspect Element" instead.</p>
<form method="get" target="_blank" action="<?php echo $base_url; ?>hatter-delete.php">
    <input type="hidden" name="magic" value="<?php echo $magic; ?>" />
    <input type="hidden" name="user" value="<?php echo $user; ?>" />
    <input type="hidden" name="pw" value="<?php echo $password; ?>" />
    <p>Hatter ID: <input type="text" name="id" value="" /></p>
    <p> <input type="submit" value="Test Delete" /></p>
</form>

</body>
</html>