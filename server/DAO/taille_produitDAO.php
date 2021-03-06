<?php
include_once('DTO/taille_produitDTO.php');
class taille_produitDAO 
{
    public static function get($id1,$id2)
    {
        $taille_produit = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM taille_produit WHERE idTaille = :idTaille AND idProduit=:idProduit');
        $state->bindValue(":idTaille", $id1);
        $state->bindValue(":idProduit", $id2);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $taille_produit = new taille_produitDTO();
            $taille_produit->setIdTaille($result["idTaille"]);
            $taille_produit->setIdProduit($result["idProduit"]);
        }
        return $taille_produit;
    }
        public static function getByProduit($id)
    {
        $Taille_produits = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM taille_produit WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
            $Taille_produit = new taille_produitDTO();
            $Taille_produit->setIdTaille($result["idTaille"]);
            $Taille_produit->setIdProduit($result["idProduit"]);
            $Taille_produits[] = $Taille_produit;
        }
        return $Taille_produits;
    }
        public static function getByTaille($id)
    {
        $Taille_produits = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM taille_produit WHERE idTaille = :idTaille');
        $state->bindValue(":idTaille", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
            $Taille_produit = new taille_produitDTO();
            $Taille_produit->setIdTaille($result["idTaille"]);
            $Taille_produit->setIdProduit($result["idProduit"]);
            $Taille_produits[] = $Taille_produit;
        }
        return $Taille_produits;
    }

    public static function getList()
    {
        $Taille_produits = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM taille_produit');
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
            $Taille_produit = new taille_produitDTO();
            $Taille_produit->setIdTaille($result["idTaille"]);
            $Taille_produit->setIdProduit($result["idProduit"]);
            $Taille_produits[] = $Taille_produit;
        }
        return $Taille_produits;
    }

    public static function delete($id1,$id2)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM taille_produit WHERE idTaille = :idTaille AND idProduit=:idProduit');
        $state->bindValue(":idTaille", $id1);
        $state->bindValue(":idProduit", $id2);
        $state->execute();
    }
        public static function deleteByTaille($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM taille_produit WHERE idTaille = :idTaille');
        $state->bindValue(":idTaille", $id);
        $state->execute();
    }
        public static function deleteByProduit($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM taille_produit WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
    }

    public static function insert($contient)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO taille_produit(idTaille,idProduit) VALUES(:idTaille,:idProduit)');
        $state->bindValue(":idTaille", $contient->getIdTaille());
        $state->bindValue(":idProduit", $contient->getIdProduit());
        $state->execute();
    }

    public static function update($contient)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE taille_produit SET idProduit=:idProduit,idTaille=:idTaille WHERE idTaille = :idTaille');
        $state->bindValue(":idProduit",$contient->getIdProduit());
        $state->bindValue(":idTaille", $contient->getIdTaille());
        $state->bindValue(":idTaille", $contient->getIdTaille());
        $state->execute();
    }
}
