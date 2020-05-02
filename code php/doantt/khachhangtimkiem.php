<?php
	require_once 'connect.php';
    $tukhoa=$_POST['tukhoa'];
    $sql = "SELECT * FROM monan WHERE tenmonan like '%$tukhoa%' or gia like '%$tukhoa%' or mota like '%$tukhoa%'";
    $result = $conn->query($sql);
    $mangmonan=array();
    while ($row=mysqli_fetch_assoc ($result)) {
           array_push($mangmonan,new Monannoibat(
            $row['idmonan'],
            $row['iddanhmuc'],
            $row['tenmonan'],
            $row['mota'],
            $row['gia'],
            $row['tinhtrang'],
            $row['soluong'],
            $row['hinhanhmonan'],
        ));
        } 
        echo(json_encode($mangmonan));
    class Monannoibat{
        public function Monannoibat($idmonan,$iddanhmuc,$tenmonan,$mota,$gia,$tinhtrang,$soluong,$hinhanhmonan)
        {
          $this->idmonan=$idmonan;
           $this->iddanhmuc=$iddanhmuc;
           $this->tenmonan=$tenmonan;
           $this->mota=$mota;
           $this->gia=$gia;
           $this->tinhtrang=$tinhtrang;
           $this->soluong=$soluong;
           $this->hinhanhmonan=$hinhanhmonan;

        }
    }
?>