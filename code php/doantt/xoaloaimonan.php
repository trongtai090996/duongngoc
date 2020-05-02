<?php 
	require_once 'connect.php';
	$idloaimonan=$_POST['idloaimonan'];
	$ketqua="";
	$sql="DELETE FROM loaimonan WHERE iddanhmuc=$idloaimonan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>