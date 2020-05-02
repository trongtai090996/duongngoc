<?php
  require_once 'connect.php';
  $tongtien=0;
  
    $sql = "SELECT * FROM donhang Where tinhtrangdonhang='Đã giao hàng'";
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