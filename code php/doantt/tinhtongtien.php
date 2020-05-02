<?php 
	require_once 'connect.php';
	$iddonhang=$_POST['iddonhang'];
	$tongtien=0;
	$sql="SELECT * FROM chitietdonhang WHERE iddonhang=$iddonhang";
	$result = $conn->query($sql);
    while ($row=mysqli_fetch_assoc ($result)) {
           $soluongmua=$row['soluong'];
           $idmonan=$row['idmonan'];
           $gia="SELECT * FROM monan WHERE idmonan=$idmonan";
           $kqgia=$conn->query($gia);
           while ($hang=mysqli_fetch_assoc ($kqgia)) {
           		$giamonan=$hang['gia'];
           		$tongtien=$tongtien+($soluongmua*$giamonan);
           }
        } 
        echo(json_encode($tongtien));
	
?>