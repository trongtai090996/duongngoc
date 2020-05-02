<?php
	require_once 'connect.php';
	$ketqua="";
	$iddonhang=$_POST['iddonhang'];
	$tinhtrangmoi=$_POST['tinhtrangmoi'];
	$sql="UPDATE donhang SET tinhtrangdonhang='$tinhtrangmoi' WHERE iddonhang=$iddonhang";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>