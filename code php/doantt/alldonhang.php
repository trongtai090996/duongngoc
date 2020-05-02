<?php
	require_once 'connect.php';
	$response = array();
	/** Array for JSON response*/

		$sql = "SELECT * FROM donhang";
    $result = $conn->query($sql);
    while ($row=mysqli_fetch_assoc ($result)) {
           array_push($response,new Donhang(
            $row['iddonhang'],
            $row['idtaikhoan'],
            $row['ngaydat'],
            $row['diadiemgiaohang'],
            $row['tinhtrangdonhang'],            
        ));
        } 
        echo(json_encode($response));
    
	
	class Donhang{
        public function Donhang($iddonhang,$idtaikhoan,$ngaydat,$diadiemgiaohang,$tinhtrangdonhang)
        {
           $this->iddonhang=$iddonhang;
           $this->idtaikhoan=$idtaikhoan;
           $this->ngaydat=$ngaydat;
           $this->diadiemgiaohang=$diadiemgiaohang;
           $this->tinhtrangdonhang=$tinhtrangdonhang;         
        }
    }
?>