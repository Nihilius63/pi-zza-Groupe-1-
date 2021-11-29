<?php
class DAOTables {
    private static $url="http://localhost/pi-zza-Groupe-1-/server/tables";
    public static function getTables() {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOTables::$url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }

    public static function getTableById($id) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOTables::$url."/".$id);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }

    public static function affichageTable() {
        $liste=DAOTables::getTables();
        $tab=array();
        foreach($liste as $value){
            $string ="<a href='table.php?idTable=".$value->idTables."'>";
            $string=$string.'<div class="carte">';
            if ($value->nbPlaces>3 && $value->nbPlaces<6){
                $string=$string.'<img src="img/table4p.png" alt="">';
            }
            elseif ($value->nbPlaces<=3) {
                $string=$string.'<img src="img/table.png" alt="">';
            }
            elseif ($value->nbPlaces>=6) {
                $string=$string.'<img src="img/table6.png" alt="">';
            }
            $string=$string.'<p> Table '.$value->idTables.'  </p>';
            if ($value->nbPersonne>0){
                $string=$string. '<i class="fas fa-exclamation-triangle red"> Occupé '.$value->nbPersonne.' Personne</i> </div>';
            }
            else {
                $string=$string. '</div></a>';
            }
            $tab[]=$string;
        }
        return $tab;
    }

}

?>