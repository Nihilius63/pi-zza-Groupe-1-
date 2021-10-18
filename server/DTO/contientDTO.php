<?php
class contientDTO implements JsonSerializable
{
    private $idCommande;
    private $idProduit;
    private $quantite;

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
    public function jsonSerialize()
    {
        return array
        (
            'idCommande' => $this->idCommande,
            'idProduit' => $this->idProduit,
            'quantite'=>$this->quantite
        );
    }


}
