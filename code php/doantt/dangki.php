<?php
	require_once 'connect.php';
	$ketqua="";
	$hoten=$_POST['hoten'];
	$username=$_POST['username'];
	$password=$_POST['password'];
  $sodienthoai=$_POST['sodienthoai'];
  $level=$_POST['level'];
	
  if ($hoten!=""&&$username!=""&&$password!=""&&$sodienthoai!="") {
    $sql = "SELECT * FROM taikhoan Where username='$username'";
    $result = mysqli_query($conn,$sql);
    if (mysqli_num_rows($result)!=0) {
      $ketqua="FAIL";
    }else{
      $truyvan="INSERT INTO taikhoan(idtaikhoan,hoten,sodienthoai,username,password,level) VALUES (NULL,'$hoten','$sodienthoai','$username','$password','$level')";
      $thuchien=mysqli_query($conn,$truyvan);
      $ketqua="SUCCES";
    }
    
  }
  
  echo(json_encode($ketqua));
?>