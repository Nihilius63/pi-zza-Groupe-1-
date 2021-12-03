<?php
include_once('DTO/contientDTO.php');
class contientDAO 
{
    public static function get($id1,$id2)
    {
        $contient = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM contient WHERE idCommande = :idCommande AND idProduit=:idProduit');
        $state->bindValue(":idCommande", $id1);
        $state->bindValue(":idProduit", $id2);
        $state->execute();
        $resultats = $state->fetchAll();

        if (sizeof($resultats) > 0)
        {
            $result = $resultats[0];
            $contient = new contientDTO();
            $contient->setIdCommande($result["idCommande"]);
            $contient->setIdProduit($result["idProduit"]);
            $contient->setQuantite($result["quantite"]);
        }
        return $contient;
    }

        public static function getByCommande($id)
    {
        $contients = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM contient WHERE idCommande = :idCommande');
        $state->bindValue(":idCommande", $id);
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
            $contient = new contientDTO();
            $contient->setIdCommande($result["idCommande"]);
            $contient->setIdProduit($result["idProduit"]);
            $contient->setQuantite($result["quantite"]);
            $contients[] = $contient;
        }
        return $contients;
    }
        public static function getByProduit($id)
    {
        $contients = array();
        $contient = null;
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM contient WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
            $contient = new contientDTO();
            $contient->setIdCommande($result["idCommande"]);
            $contient->setIdProduit($result["idProduit"]);
            $contient->setQuantite($result["quantite"]);
            $contients[] = $contient;
        }
        return $contients;
    }
    public static function getList()
    {
        $contients = array();
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('SELECT * FROM contient');
        $state->execute();
        $resultats = $state->fetchAll();
        foreach ($resultats as $result)
        {
            $contient = new contientDTO();
            $contient->setIdCommande($result["idCommande"]);
            $contient->setIdProduit($result["idProduit"]);
            $contient->setQuantite($result["quantite"]);
            $contients[] = $contient;
        }
        return $contients;
    }

    public static function delete($id1,$id2)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM contient WHERE idCommande = :idCommande AND idProduit=:idProduit');
        $state->bindValue(":idCommande", $id1);
        $state->bindValue(":idProduit", $id2);
        $state->execute();
    }
        public static function deleteByCommande($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM contient WHERE idCommande = :idCommande');
        $state->bindValue(":idCommande", $id);
        $state->execute();
    }
        public static function deleteByProduit($id)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('DELETE FROM contient WHERE idProduit = :idProduit');
        $state->bindValue(":idProduit", $id);
        $state->execute();
    }

    public static function insert($contient)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('INSERT INTO contient(idCommande,idProduit,quantite) VALUES(:idCommande,:idProduit,:quantite)');
        $state->bindValue(":idCommande", $contient->getIdCommande());
        $state->bindValue(":idProduit", $contient->getIdProduit());
        $state->bindValue(":quantite", $contient->getQuantite());
        $state->execute();
    }

    public static function update($contient)
    {
        $connex = DatabaseLinker::getConnexion();
        $state = $connex->prepare('UPDATE Contient SET idProduit=:idProduit,idCommande=:idCommande,quantite=:quantite WHERE idCommande = :idCommande AND idProduit=:idProduit');
        $state->bindValue(":idProduit",$contient->getIdProduit());
        $state->bindValue(":idCommande", $contient->getIdCommande());
        $state->bindValue(":quantite", $contient->getQuantite());
        $state->bindValue(":idCommande", $contient->getIdCommande());
        $state->bindValue(":idProduit",$contient->getIdProduit());
        $state->execute();
    }
}
