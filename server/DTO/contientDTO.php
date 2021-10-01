<?php
class contientDTO 
{
    private $idCommande;
    private $idProduit;
    public function getIdCommande() 
    {
        return $this->idCommande;
    }

    public function getIdProduit() 
    {
        return $this->idProduit;
    }

    public function setIdCommande($idCommande): void 
    {
        $this->idCommande = $idCommande;
    }

    public function setIdProduit($idProduit): void 
    {
        $this->idProduit = $idProduit;
    }


}
