<?php
	require_once 'connect.php';
    $sql = "SELECT * FROM loaimonan";
    $result = $conn->query($sql);
    $mangloaimonan=array();
    while ($row=mysqli_fetch_assoc ($result)) {
           array_push($mangloaimonan,new Loaimonan(
            $row['iddanhmuc'],
            $row['tenloaimonan'],
            $row['linkanh']
        ));
        } 
        echo(json_encode($mangloaimonan));
    class loaimonan{
        public function Loaimonan($iddanhmuc,$tenloaimonan,$linkanh)
        {
           $this->iddanhmuc=$iddanhmuc;
           $this->tenloaimonan=$tenloaimonan;
           $this->linkanh=$linkanh;
        }
    }
?>