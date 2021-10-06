<?php
class historiqueDTO implements JsonSerializable
{
    private $idHistorique;
    private $nomProduit;
    private $quantite;
    private $prixProduit;
    private $dateCommande;
    private $numeroTables;
    public function __construct($nomProduit, $quantite, $prixProduit, $dateCommande, $numeroTable) 
    {
        $this->nomProduit = $nomProduit;
        $this->quantite = $quantite;
        $this->prixProduit = $prixProduit;
        $this->dateCommande = $dateCommande;
        $this->numeroTables = $numeroTable;
    }

    public function getIdHistorique() 
    {
        return $this->idHistorique;
    }

    public function getNomProduit() 
    {
        return $this->nomProduit;
    }

    public function getQuantite() 
    {
        return $this->quantite;
    }

    public function getPrixProduit() 
    {
        return $this->prixProduit;
    }

    public function getDateCommande() 
    {
        return $this->dateCommande;
    }

    public function getNumeroTables() 
    {
        return $this->numeroTables;
    }

    public function setIdHistorique($idHistorique): void 
    {
        $this->idHistorique = $idHistorique;
    }

    public function setNomProduit($nomProduit): void 
    {
        $this->nomProduit = $nomProduit;
    }

    public function setQuantite($quantite): void 
    {
        $this->quantite = $quantite;
    }

    public function setPrixProduit($prixProduit): void 
    {
        $this->prixProduit = $prixProduit;
    }

    public function setDateCommande($dateCommande): void 
    {
        $this->dateCommande = $dateCommande;
    }

    public function setNumeroTables($numeroTable): void 
    {
        $this->numeroTables = $numeroTable;
    }

    public function jsonSerialize()
    {
        return array
        (
            'idHistorique' => $this->idHistorique,
            'nomProduit' => $this->nomProduit,
            'quantite'=>$this->quantite,
            'prixProduit'=>$this->prixProduit,
            'dateCommande'=>$this->dateCommande,
            'numeroTables'=>$this->numeroTables
        );
    }
}
