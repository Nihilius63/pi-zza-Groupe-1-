<?php
include_once('DTO/tailleDTO.php');
class tailleDAO 
{
    public static function get($id)
    {
        $Taille = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM taille WHERE idTaille = :idTaille');
        $state->bindValue(":idTaille", $id);
        $state->execute();
        $resultats = $state->fetchAll();
        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $Taille = new tailleDTO($result["nomTaille"]);
            $Taille->setIdTaille($result["idTaille"]);
        }
        return $Taille;
    }

    public static function getList()
    {
        $Taille = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM tables');
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
                $Tailles = new tailleDTO($result["nomTaille"]);
                $Tailles->setIdTaille($result["idTaille"]);
                $Taille[] = $Tailles;
        }
        return $Taille;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM taille WHERE idTaille = :idTaille');
        $state->bindValue(":idTaille", $id);
        $state->execute();
    }

    public static function insert($taille)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO taille(nomTaille) VALUES(:nomTaille)');
        $state->bindValue(":nomTaille", $table->getNomTaille());
        $state->execute();
    }

    public static function update($taille)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE taille SET nomTaille=:nomTaille WHERE idTaille = :idTaille');
        $state->bindValue(":nbPlaces",$taille->getNbPlaces());
        $state->bindValue(":nbPersonne",$taille->getNbPersonne());
        $state->bindValue(":idTables", $taille->getIdTables());
        $state->execute();
    }
}
