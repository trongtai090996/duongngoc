<?php
	require_once 'connect.php';
	$response = array();
	/** Array for JSON response*/
		$idmonan=$_POST['idmonan'];
		    $sql = "SELECT * FROM monan Where monan.idmonan=$idmonan";
    $result = $conn->query($sql);
    while ($row=mysqli_fetch_assoc ($result)) {
           array_push($response,new Monannoibat(
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
        echo(json_encode($response));
    
	
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