<?php

class DAOCommande {
    private static $url="http://localhost/pi-zza-Groupe-1-/server/commande";
    public static function getCommande() {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, DAOCommande::$url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($ch);
        curl_close($ch);
        return json_decode($output);
    }

    public static function getCommandeByIdTable($id) {
        $tables=DAOCommande::getCommande();
        $tab=[];
        foreach ($tables as $value) {
           if ($value->idTables == $id) {
              $tab[]=$value;
           }
        }

        return $tab;
    }
}