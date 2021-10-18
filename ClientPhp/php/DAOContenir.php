<?php

class DAOContenir
{
    private static $url="http://localhost/pi-zza-Groupe-1-/server/contient";
    public static function getContient() {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOContenir::$url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }

    public static function getContenirByIdCommande($id) {
        $contient=DAOContenir::getContient();
        $tab=[];
        foreach ($contient as $value) {
            if ($value->idCommande == $id) {
                $tab[]=$value;
            }
        }
        return $tab;
    }
}