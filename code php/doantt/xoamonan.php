<?php 
	require_once 'connect.php';
	$idmonan=$_POST['idmonan'];
	$ketqua="";
	$sql="DELETE FROM monan WHERE idmonan=$idmonan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>