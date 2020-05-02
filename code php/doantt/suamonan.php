<?php 
	require_once 'connect.php';
	$idmonan=$_POST['idmonan'];
	$tenmoi=$_POST['tenmoi'];
	$anhmoi=$_POST['anhmoi'];
	$giamoi=$_POST['giamoi'];
	$soluongmoi=$_POST['soluongmoi'];
	$motamoi=$_POST['motamoi'];
	$tinhtrangmoi=$_POST['tinhtrangmoi'];
	$iddanhmucmoi=$_POST['iddanhmucmoi'];
	$sql="UPDATE monan SET iddanhmuc=$iddanhmucmoi, tenmonan='$tenmoi', mota='$motamoi', gia=$giamoi, tinhtrang='$tinhtrangmoi',soluong=$soluongmoi,hinhanhmonan='$anhmoi' WHERE idmonan=$idmonan";
	if (mysqli_query($conn,$sql)) {
		$ketqua="SUCCES";
	}else{
		$ketqua="FAIL";
	}
	echo(json_encode($ketqua));
?>