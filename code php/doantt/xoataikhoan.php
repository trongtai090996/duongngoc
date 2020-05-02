<?php 
	require_once 'connect.php';
	$idtaikhoan=$_POST['idtaikhoan'];
	$ketqua="";
	$sql="DELETE FROM taikhoan WHERE idtaikhoan=$idtaikhoan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>