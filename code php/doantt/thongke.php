<?php
  require_once 'connect.php';
  $tongtien=0;
  $ngaybatdau=$_POST['ngaybatdau'];
  $ngayketthuc=$_POST['ngayketthuc'];
  $sql="";
  if ($ngaybatdau===$ngayketthuc) {
    $sql = "SELECT * FROM donhang Where ngaydat='$ngaybatdau' and tinhtrangdonhang='Đã giao hàng'";
  }else{
    $sql = "SELECT * FROM donhang Where ngaydat>='$ngaybatdau' and ngaydat<='$ngayketthuc'and tinhtrangdonhang='Đã giao hàng'";
  }
  
    
    $result = $conn->query($sql);
    while ($row=mysqli_fetch_assoc ($result)) {
      $id=$row['iddonhang'];
           $a = "SELECT * FROM chitietdonhang Where iddonhang=$id";
           $b=$conn->query($a);

           while ($row1=mysqli_fetch_assoc ($b)) {
            $idma=$row1['idmonan'];
            $soluong=$row1['soluong'];
            $c="SELECT * FROM monan Where idmonan=$idma";
            $d=$conn->query($c);
            while ($row2=mysqli_fetch_assoc ($d)) {
              $gia=$row2['gia'];
              
            }
            $tongtien=$tongtien+($gia*$soluong);
            
            
              
           }
        } 
        echo(json_encode($tongtien));
    
  
  
?>