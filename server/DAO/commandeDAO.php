<?php
include_once('DTO/commandeDTO.php');
class commandeDAO 
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
            $Commande = new commandeDTO($result["idTables"]);
            $Commande->setIdCommande($result["idCommande"]);
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
                $Commande = new commandeDTO($result["idTables"]);
                $Commande->setIdCommande($result["idCommande"]);
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
        $state = $connex->prepare('INSERT INTO commande(idCommande,idTables) VALUES(:idCommande,:idTables)');
        $state->bindValue(":idCommande", $Commande->getIdCommande());
        $state->bindValue(":idTables", $Commande->getIdTables());
        $state->execute();
    }

    public static function update($Commande)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE Marque SET idTables=:idTables,idCommande=:idCommande WHERE idCommande = :idCommande');
        $state->bindValue(":idTables",$Commande->getIdTables());
        $state->bindValue(":idCommande", $Commande->getIdCommande());
        $state->bindValue(":idCommande", $Commande->getIdCommande());
        $state->execute();
    }
}
