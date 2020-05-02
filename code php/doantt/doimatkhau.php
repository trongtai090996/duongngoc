<?php 
	require_once 'connect.php';
	$idtaikhoan=$_POST['idtaikhoan'];
	$matkhaumoi=$_POST['matkhaumoi'];
	$ketqua="";
	$sql="UPDATE taikhoan SET password='$matkhaumoi'WHERE idtaikhoan=$idtaikhoan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>