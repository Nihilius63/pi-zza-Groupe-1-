<?php
include_once('DTO/contientDTO.php');
class contientDAO 
{
    public static function get($id)
    {
        $contient = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM contient WHERE idCommande = :idCommande');
        $state->bindValue(":idCommande", $id);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $contient = new commandeDTO($result["idProduit"],$result["quantite"]);
            $contient->setIdCommande($result["idCommande"]);
        }
        return $contient;
    }

    public static function getList()
    {
        $contient = array();

        $connex = DatabaseLinker::getConnexion();

        $state = $connex->prepare('SELECT * FROM commande');
        $state->execute();
        $resultats = $state->fetchAll();

        foreach ($resultats as $result)
        {
                $contient = new contientDTO($result["idProduit"],$result["quantite"]);
                $contient->setIdCommande($result["idCommande"]);

                $contients[] = $contient;
        }
        return $contient;
    }

    public static function delete($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM contient WHERE idCommande = :idCommande');
        $state->bindValue(":idCommande", $id);
        $state->execute();
    }

    public static function insert($contient)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO contient(idCommande,idProduit,quantite) VALUES(:idCommande,:idProduit,:quantite)');
        $state->bindValue(":idCommande", $contient->getIdCommande());
        $state->bindValue(":idProduit", $contient->getIdProduit());
        $state->bindValue(":quantite", $contient->getQuantite());
    }

    public static function update($contient)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE Contient SET idProduit=:idProduit,idCommande=:idCommande,quantite=:quantite WHERE idCommande = :idCommande');
        $state->bindValue(":idProduit",$contient->getIdProduit());
        $state->bindValue(":idCommande", $contient->getIdCommande());
        $state->bindValue(":quantite", $contient->getQuantite());
        $state->bindValue(":idCommande", $contient->getIdCommande());
        $state->execute();
    }
}
