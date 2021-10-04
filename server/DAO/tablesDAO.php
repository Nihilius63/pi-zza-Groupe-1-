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
            $Table = new tablesDTO($result["nbPlaces"]);
            $Table->setIdTables($result["idTables"]);
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
                $Tables = new tablesDTO($result["nbPlaces"]);
                $Tables->setIdTables($result["idTables"]);
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

    public static function insert($table)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO tables(nbPlaces) VALUES(:numeroPlaces)');
        $state->bindValue(":numeroPlaces", $table->getNbPlaces());
        $state->execute();
    }

    public static function update($table)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE tables SET nbPlaces=:nbPlaces WHERE idTables = :idTables');
        $state->bindValue(":nbPlaces",$table->getNbPlaces());
        $state->bindValue(":idTables", $table->getIdTables());
        $state->execute();
    }
}
