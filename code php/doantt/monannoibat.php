<?php
	require_once 'connect.php';
    $sql = "SELECT * FROM monan Where tinhtrang='nổi bật' LIMIT 6";
    $result = $conn->query($sql);
    $mangmonannoibat=array();
    while ($row=mysqli_fetch_assoc ($result)) {
           array_push($mangmonannoibat,new Monannoibat(
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
        echo(json_encode($mangmonannoibat));
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