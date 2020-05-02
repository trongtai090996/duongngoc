<?php
  require_once 'connect.php';
  $ketqua="";
  $iddonhang=$_POST['iddonhang'];
  $idmonan=$_POST['idmonan'];
  $soluong=$_POST['soluong'];
  $insert="INSERT INTO chitietdonhang(idchitietdonhang,iddonhang,idmonan,soluong) VALUES (NULL,$iddonhang,$idmonan,$soluong)";
  
  if (mysqli_query($conn,$insert)) {
    $ketqua="SUCCES";
    
  }else{
    $ketqua="FAIL";
  }
  echo(json_encode($ketqua));
  
?>