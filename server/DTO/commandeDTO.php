<?php
class commandeDTO 
{
    private $idProduit;
    private $idTables;
    private $quantite;
    public function getQuantite() 
    {
        return $this->quantite;
    }

    public function setQuantite($quantite): void 
    {
        $this->quantite = $quantite;
    }

        public function getIdProduit() 
    {
        return $this->idProduit;
    }

    public function getIdTables() 
    {
        return $this->idTables;
    }

    public function setIdProduit($idProduit): void 
    {
        $this->idProduit = $idProduit;
    }

    public function setIdTables($idTables): void 
    {
        $this->idTables = $idTables;
    }


}
