<?php
class contientDTO 
{
    private $idCommande;
    private $idProduit;
    private $quantite;
    public function __construct($idProduit, $quantite) 
    {
        $this->idProduit = $idProduit;
        $this->quantite = $quantite;
    }

    public function getQuantite() 
    {
        return $this->quantite;
    }

    public function setQuantite($quantite): void 
    {
        $this->quantite = $quantite;
    }

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
