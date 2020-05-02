<?php
  require_once 'connect.php';
  $mangtaikhoan=array();
  $sql = "SELECT * FROM taikhoan ORDER BY level ASC";
    $result = mysqli_query($conn,$sql);
    while ($row=mysqli_fetch_assoc($result)) {
           array_push($mangtaikhoan,new TaiKhoan(
            $row['idtaikhoan'],
            $row['hoten'],
            $row['sodienthoai'],
            $row['username'],
            $row['password'],
            $row['level']
        ));
        } 
        echo(json_encode($mangtaikhoan));
    
  
  class TaiKhoan{
        public function TaiKhoan($idtaikhoan,$hoten,$sodienthoai,$username,$password,$level)
        {
           $this->idtaikhoan=$idtaikhoan;
           $this->hoten=$hoten;
           $this->sodienthoai=$sodienthoai;
           $this->username=$username;
           $this->password=$password;
           $this->level=$level;
        }
    }
?>