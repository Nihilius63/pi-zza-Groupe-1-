<?php
include_once('DTO/contientDTO.php');
class contientDAO 
{
           public static function get($id)
    {
        $Commande = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM commande WHERE idCommande = :idCommande');
        $state->bindValue(":idCommande", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $Commande = new commandeDTO($result["idTables"],$result["quantite"]);
            $Commande->setIdProduit($result["idCommande"]);
        }
        return $Commande;
    }

    public static function getList()
    {
        $Commande = array();

        $connex = DatabaseLinker::getConnexion();

        $state = $connex->prepare('SELECT * FROM commande');
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
                $Commande = new CommandeDTO($result["idProduit"],$result["quantite"]);
                $Commande->setIdProduit($result["idProduit"]);

                $Commandes[] = $Commande;
        }
        return $Commandes;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM commande WHERE idCommande = :idCommande');
        $state->bindValue(":idCommande", $id);
        $state->execute();
    }

    public static function insert($Commande)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO commande(idCommande,idProduit,quantite) VALUES(:idCommande,:idProduit,:quantite)');
        $state->bindValue(":idCommande", $Commande->getIdCommande());
        $state->bindValue(":idProduit", $Commande->getIdProduit());
        $state->bindValue(":quantite", $Commande->getQuantite());
        $state->execute();
        $marque->setIdMarque($connex->lastInsertId());
    }

    public static function update($Commande)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE Marque SET idProduit=:idProduit,idCommande=:idCommande,quantite=:quantite WHERE idCommande = :idCommande');
        $state->bindValue(":idProduit",$Commande->getIdProduit());
        $state->bindValue(":idCommande", $Commande->getIdCommande());
        $state->bindValue(":quantite", $Commande->getQuantite());
        $state->bindValue(":idCommande", $Commande->getIdCommande());
        $state->execute();
    }
}
