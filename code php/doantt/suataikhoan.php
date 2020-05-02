<?php 
	require_once 'connect.php';
	$idtaikhoan=$_POST['idtaikhoan'];
	$hoten=$_POST['hoten'];
	$sodienthoai=$_POST['sodienthoai'];
	$pass=$_POST['password'];
	$sql="UPDATE taikhoan SET hoten='$hoten', sodienthoai='$sodienthoai', password='$pass' WHERE idtaikhoan=$idtaikhoan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>