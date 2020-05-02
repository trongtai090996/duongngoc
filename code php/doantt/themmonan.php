<?php 
	require_once 'connect.php';
	$iddanhmuc=$_POST['iddanhmuc'];
	$tenmonan=$_POST['tenmonan'];
	$mota=$_POST['mota'];
	$gia=$_POST['gia'];
	$tinhtrang=$_POST['tinhtrang'];
	$soluong=$_POST['soluong'];
	$linkanh=$_POST['linkanh'];
	$ketqua="";
	$sql="INSERT INTO monan(idmonan,iddanhmuc,tenmonan,mota,gia,tinhtrang,soluong,hinhanhmonan) VALUES (NULL,$iddanhmuc,'$tenmonan','$mota',$gia,'$tinhtrang',$soluong,'$linkanh')";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>