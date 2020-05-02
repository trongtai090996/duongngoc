<?php
	require_once 'connect.php';
	$mangsoluongmua=array();
	$sql="SELECT chitietdonhang.idmonan,SUM(chitietdonhang.soluong) AS slm FROM chitietdonhang GROUP BY chitietdonhang.idmonan ORDER BY slm DESC LIMIT 10";
	$result=$conn->query($sql);
	while ($row=mysqli_fetch_assoc ($result)) {
		array_push($mangsoluongmua, new SoLuongDaBan(
			$row['idmonan'],
			$row['slm'],
		));
	}
	echo(json_encode($mangsoluongmua));
	class SoLuongDaBan{
		public function SoLuongDaBan($idmonan,$slm)
		{
			$this->idmonan=$idmonan;
			$this->slm=$slm;
		}
	}
?>