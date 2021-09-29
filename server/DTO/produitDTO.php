<?php
class produitDTO 
{
    private $idProduit;
    private $nomProduit;
    private $prixProduit;
    private $tailleProduit;
    public function getIdProduit() 
    {
        return $this->idProduit;
    }

    public function getNomProduit() 
    {
        return $this->nomProduit;
    }

    public function getPrixProduit() 
    {
        return $this->prixProduit;
    }

    public function getTailleProduit() 
    {
        return $this->tailleProduit;
    }

    public function setIdProduit($idProduit): void 
    {
        $this->idProduit = $idProduit;
    }

    public function setNomProduit($nomProduit): void 
    {
        $this->nomProduit = $nomProduit;
    }

    public function setPrixProduit($prixProduit): void 
    {
        $this->prixProduit = $prixProduit;
    }

    public function setTailleProduit($tailleProduit): void 
    {
        $this->tailleProduit = $tailleProduit;
    }


}
