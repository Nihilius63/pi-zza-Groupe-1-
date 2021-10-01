<?php
class commandeDTO 
{
    private $idTables;
    private $idCommande;
    public function __construct($idTables) 
    {
        $this->idTables = $idTables;
    }
        public function getIdTables() 
    {
        return $this->idTables;
    }

    public function getIdCommande() 
    {
        return $this->idCommande;
    }

    public function setIdTables($idTables): void 
    {
        $this->idTables = $$idTables;
    }

    public function setIdCommande($idCommande): void 
    {
        $this->idCommande = $idCommande;
    }


}
