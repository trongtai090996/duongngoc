<?php
  require_once 'connect.php';
	$iddanhmuc=$_POST['iddanhmuc'];
  $ketqua="";
  $sql="SELECT * FROM loaimonan WHERE iddanhmuc=$iddanhmuc";
  $result=mysqli_query($conn,$sql);
  while ($row=mysqli_fetch_assoc ($result)) {
     $ketqua=$row['tenloaimonan'];      
  }
  echo(json_encode($ketqua));
?>