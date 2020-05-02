<?php 
	require_once('connect.php');
	$ketqua="";
	$iddonhang=$_POST['iddonhang'];
	$xoa="DELETE FROM chitietdonhang WHERE iddonhang=$iddonhang";
	$result=mysqli_query($conn,$xoa);
	$sql="DELETE FROM donhang WHERE iddonhang=$iddonhang";
	if (mysqli_query($conn,$sql)) {
		$ketqua="DELETESUCCES";
	}else{
		$ketqua="DELETEFAIL";
	}
	echo(json_encode($ketqua));
?>