<?php
include_once('DTO/categorieDTO.php');
class categorieDAO 
{
        public static function get($id)
    {
        $Categorie = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM categorie WHERE idCategorie = :idCategorie');
        $state->bindValue(":idCategorie", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $Categorie = new categorieDTO($result["nomCategorie"]);
            $Categorie->setIdCategorie($result["idCategorie"]);
        }
        return $Categorie;
    }

    public static function getList()
    {
        $Categories = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM categorie');
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
                $Categorie = new categorieDTO($result["nomCategorie"]);
                $Categorie->setIdCategorie($result["idCategorie"]);
                $Categories[] = $Categorie;
        }
        return $Categories;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM categorie WHERE idCategorie = :idCategorie');
        $state->bindValue(":idCategorie", $id);
        $state->execute();
    }

    public static function insert($Categorie)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO categorie(idCategorie,nomCategorie) VALUES(:idCategorie,:nomCategorie)');
        $state->bindValue(":idCategorie", $Categorie->getIdCategorie());
        $state->bindValue(":nomCategorie", $Categorie->getNomCategorie());
        $state->execute();
    }

    public static function update($Categorie)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE categorie SET nomCategorie=:nomCategorie WHERE idCategorie = :idCategorie');
        $state->bindValue(":idCategorie",$Categorie->getIdCategorie($Categorie));
        $state->bindValue(":nomCategorie", $Categorie->getNomCategorie($Categorie));
        $state->execute();
    }
}
