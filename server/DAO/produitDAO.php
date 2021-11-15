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
            $Produit = new produitDTO($result["nomProduit"],$result["imageProduit"],$result["prixProduit"],$result["idCategorie"]);
            $Produit->setIdProduit($result["idProduit"]);
        }
        return $Produit;
    }

    public static function getList()
    {
        $Produits = array();

        $connex = DatabaseLinker::getConnexion();

        $state = $connex->prepare('SELECT * FROM produit');
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
                $Produit = new produitDTO($result["nomProduit"],$result["imageProduit"],$result["prixProduit"],$result["idCategorie"]);
                $Produit->setIdProduit($result["idProduit"]);

                $Produits[] = $Produit;
        }
        return $Produits;
    }
    public static function getproduitbycate($id)
    {
        $Produits = array();

        $connex = DatabaseLinker::getConnexion();

        $state = $connex->prepare('SELECT * FROM produit WHERE idCategorie=:idCategorie');
        $state->bindValue(":idCategorie", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
                $Produit = new produitDTO($result["nomProduit"],$result["imageProduit"],$result["prixProduit"],$result["idCategorie"]);
                $Produit->setIdProduit($result["idProduit"]);

                $Produits[] = $Produit;
        }
        return $Produits;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM produit WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
    }

    public static function insert($Produit)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO produit(nomProduit,prixProduit,imageProduit,idCategorie) VALUES(:nomProduit,:prixProduit,:imageProduit,:idCategorie)');
        $state->bindValue(":nomProduit", $Produit->getNomProduit());
        $state->bindValue(":prixProduit", $Produit->getPrixProduit());
        $state->bindValue(":imageProduit", $Produit->getImageProduit());
        $state->bindValue(":idCategorie", $Produit->getidCategorie());
        $state->execute();
    }

    public static function update($Produit)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE produit SET nomProduit=:nomProduit,prixProduit=:prixProduit,imageProduit=:imageProduit,idCategorie=:idCategorie WHERE idProduit = :idProduit');
        $state->bindValue(":nomProduit",$Produit->getNomProduit());
        $state->bindValue(":prixProduit", $Produit->getPrixProduit());
        $state->bindValue(":imageProduit", $Produit->getImageProduit());
        $state->bindValue(":idCategorie", $Produit->getIdCategorie());
        $state->bindValue(":idProduit", $Produit->getIdProduit());
        $state->execute();
    }
}
