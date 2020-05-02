<?php 
	require_once 'connect.php';
	$idtaikhoan=$_POST['idtaikhoan'];
	$hoten=$_POST['hoten'];
	$sodienthoai=$_POST['sodienthoai'];
	$ketqua="sss";
	$sql="UPDATE taikhoan SET hoten='$hoten', sodienthoai='$sodienthoai' WHERE idtaikhoan=$idtaikhoan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>