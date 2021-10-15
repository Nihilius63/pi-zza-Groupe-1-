<?php
include_once('DTO/historiqueDTO.php');
class historiqueDAO 
{
    public static function get($id)
    {
        $historique = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM historique WHERE idHistorique = :idHistorique');
        $state->bindValue(":idHistorique", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $historique = new historiqueDTO($result["nomProduit"],$result["quantite"],$result["prixProduit"],$result["dateCommande"],$result["numeroTables"]);
            $historique->setIdHistorique($result["idHistorique"]);
        }
        return $historique;
    }

    public static function getList()
    {
        $historiques = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM historique');
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
                $historique = new historiqueDTO($result["nomProduit"],$result["quantite"],$result["prixProduit"],$result["dateCommande"],$result["numeroTables"]);
                $historique->setIdHistorique($result["idHistorique"]);

                $historiques[] = $historique;
        }
        return $historiques;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM historique WHERE idHistorique = :idHistorique');
        $state->bindValue(":idHistorique", $id);
        $state->execute();
    }

    public static function insert($historique)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO historique(nomProduit,quantite,prixProduit,dateCommande,numeroTables) VALUES(:nomProduit,:quantite,:prixProduit,:dateCommande,:numeroTables)');
        $state->bindValue(":nomProduit", $historique->getNomProduit());
        $state->bindValue(":quantite", $historique->getQuantite());
        $state->bindValue(":prixProduit", $historique->getPrixProduit());
        $state->bindValue(":dateCommande", $historique->getDateCommande());
        $state->bindValue(":numeroTables", $historique->getNumeroTables());
        $state->execute();
    }

    public static function update($historique)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE historique SET nomProduit=:nomProduit,quantite=:quantite,prixProduit=:prixProduit,dateCommande=:dateCommande,numeroTables=:numeroTables WHERE idHistorique = :idHistorique');
        $state->bindValue(":nomProduit", $historique->getNomProduit());
        $state->bindValue(":quantite", $historique->getQuantite());
        $state->bindValue(":prixProduit", $historique->getPrixProduit());
        $state->bindValue(":dateCommande", $historique->getDateCommande());
        $state->bindValue(":numeroTables", $historique->getNumeroTables());
        $state->bindValue(":idHistorique", $historique->getIdHistorique());
        $state->execute();
    }
}
