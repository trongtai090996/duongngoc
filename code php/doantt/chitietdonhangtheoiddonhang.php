<?php
	require_once 'connect.php';
	$response = array();
	/** Array for JSON response*/
	
		$iddonhang=$_POST['iddonhang'];
		$sql = "SELECT * FROM chitietdonhang Where iddonhang=$iddonhang";
    $result = $conn->query($sql);
    while ($row=mysqli_fetch_assoc ($result)) {
           array_push($response,new ChiTietDonhang(
            $row['idchitietdonhang'],
            $row['iddonhang'],
            $row['idmonan'],
            $row['soluong'],            
        ));
        } 
        echo(json_encode($response));
    
	
	class ChiTietDonhang{
        public function ChiTietDonhang($idchitietdonhang,$iddonhang,$idmonan,$soluong)
        {
           $this->idchitietdonhang=$idchitietdonhang;
           $this->iddonhang=$iddonhang;
           $this->idmonan=$idmonan;
           $this->soluong=$soluong;         
        }
    }
?>