<?php
	require_once 'connect.php';
	$ketqua="";
	$idtaikhoan=$_POST['idtaikhoan'];
	$ngaydat=$_POST['ngaydat'];
	$diadiemgiaohang=$_POST['diadiemgiaohang'];
	$insert="INSERT INTO donhang(iddonhang,idtaikhoan,ngaydat,diadiemgiaohang,tinhtrangdonhang) VALUES (NULL,$idtaikhoan,'$ngaydat','$diadiemgiaohang','Chờ xử lý')";
  /*$saveinsert=mysqli_query($conn,$insert);*/
  /*$select="SELECT MAX(iddonhang) AS idmax FROM donhang";
  $saveselect=mysqli_query($conn,$select);
  while ($row=mysqli_fetch_assoc ($saveselect)) {
     $idmax=$row['idmax'];      
  } */
  if (mysqli_query($conn,$insert)) {
    $ketqua=$conn->insert_id;
    echo(json_encode($ketqua));
  }
  
?>