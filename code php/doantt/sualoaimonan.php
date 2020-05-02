<?php 
	require_once 'connect.php';
	$iddanhmuc=$_POST['iddanhmuc'];
	$tenmoi=$_POST['tenmoi'];
	$anhmoi=$_POST['anhmoi'];
	$sql="UPDATE loaimonan SET tenloaimonan='$tenmoi',linkanh='$anhmoi' WHERE iddanhmuc=$iddanhmuc";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>