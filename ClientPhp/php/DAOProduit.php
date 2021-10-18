<?php

class DAOProduit {
    private static $url="http://localhost/pi-zza-Groupe-1-/server/produit";
    public static function getProduits() {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOProduit::$url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }

    public static function getProduitByIdProduit($id) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOProduit::$url.'/'.$id);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }
}