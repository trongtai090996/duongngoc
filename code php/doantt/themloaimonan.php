<?php 
	require_once 'connect.php';
	$tenloaimonan=$_POST['tenloaimonan'];
	$linkanh=$_POST['linkanh'];
	$ketqua="";
	$sql="INSERT INTO loaimonan(iddanhmuc,tenloaimonan,linkanh) VALUES (NULL,'$tenloaimonan','$linkanh')";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>