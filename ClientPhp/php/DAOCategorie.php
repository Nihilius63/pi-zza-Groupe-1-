<?php

class DAOCategorie {
    private static $url="http://localhost/pi-zza-Groupe-1-/server/categorie";
    public static function getCategorie() {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOCategorie::$url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }

    public static function getCategorieById($id) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOCategorie::$url.'/'.$id);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }
}