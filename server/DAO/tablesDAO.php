<?php
include_once('DTO/tablesDTO.php');
class tablesDAO 
{
    public static function get($id)
    {
        $Table = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM tables WHERE idTables = :idTables');
        $state->bindValue(":idTables", $id);
        $state->execute();
        $resultats = $state->fetchAll();
        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $Table = new tablesDTO($result["numeroPlaces"]);
            $Table->setIdMarque($result["idTables"]);
        }
        return $Table;
    }

    public static function getList()
    {
        $Tables = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM tables');
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
                $Tables = new tablesDTO($result["numeroPlaces"]);
                $Tables->setIdTables($result["idProduit"]);
                $Table[] = $Tables;
        }
        return $Table;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM tables WHERE idTables = :idTables');
        $state->bindValue(":idTables", $id);
        $state->execute();
    }

    public static function insert($Produit)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO tables(numeroPlaces) VALUES(:numeroPlaces)');
        $state->bindValue(":numeroPlaces", $Produit->getNumeroPlaces());
        $state->execute();
    }

    public static function update($Produit)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE Marque SET numeroPlaces=:numeroPlaces WHERE idProduit = :idProduit');
        $state->bindValue(":numeroPlaces",$Produit->getnumeroPlaces());
        $state->bindValue(":idTables", $Produit->getIdTables());
        $state->execute();
    }
}
