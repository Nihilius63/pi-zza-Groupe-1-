<?php
include_once('DTO/produitDTO.php');
class produitDAO 
{
    public static function get($id)
    {
        $Produit = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM produit WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $Produit = new produitDTO($result["nomProduit"],$result["prixProduit"],$result["imageProduit"],$result["tailleProduit"]);
            $Produit->setIdProduit($result["idProduit"]);
        }
        return $Produit;
    }

    public static function getList()
    {
        $Produit = array();

        $connex = DatabaseLinker::getConnexion();

        $state = $connex->prepare('SELECT * FROM produit');
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
                $Produit = new produitDTO($result["nomProduit"],$result["prixProduit"],$result["imageProduit"],$result["tailleProduit"]);
                $Produit->setIdProduit($result["idProduit"]);

                $Produits[] = $Produit;
        }
        return $Produits;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM Produit WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
    }

    public static function insert($Produit)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO Produit(nomProduit,prixProduit,imageProduit,tailleProduit) VALUES(:nomProduit,:prixProduit,:imageProduit,:tailleProduit)');
        $state->bindValue(":nomProduit", $Produit->getNomProduit());
        $state->bindValue(":prixProduit", $Produit->getPrixProduit());
        $state->bindValue(":imageProduit", $Produit->getImageProduit());
        $state->bindValue(":tailleProduit", $Produit->getTailleProduit());
        $state->execute();
        $marque->setIdMarque($connex->lastInsertId());
    }

    public static function update($Produit)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE Marque SET nomProduit=:nomProduit,prixProduit=:prixProduit,imageProduit=:imageProduit,tailleProduit=:tailleProduit WHERE idProduit = :idProduit');
        $state->bindValue(":nomProduit",$Produit->getNomProduit());
        $state->bindValue(":prixProduit", $Produit->getPrixProduit());
        $state->bindValue(":imageProduit", $Produit->getImageProduit());
        $state->bindValue(":tailleProduit", $Produit->getTailleProduit());
        $state->bindValue(":idProduit", $Produit->getIdProduit());
        $state->execute();
    }
}
