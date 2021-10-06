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

    public static function addTable($data) {
        $json=json_encode($data);
        $ch = curl_init(DAOTables::$url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
        curl_setopt($ch, CURLOPT_POSTFIELDS,http_build_query($data));

        $response = curl_exec($ch);

        if (!$response)
        {
            return false;
        }
    }

    public static function affichageTable() {
        $liste=DAOTables::getTables();
        $tab=array();
        foreach($liste as $value){
            $string ="<a href=''>";
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
                $string=$string. '<i class="fas fa-exclamation-triangle red"> Occupé</i> </div>';
            }
            else {
                $string=$string. '</div>';
            }
            $tab[]=$string;
        }
        return $tab;
    }

}

?>