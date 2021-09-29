<?php
class historiqueDTO 
{
    private $idHistorique;
    private $nomProduit;
    private $quantite;
    private $prixProduit;
    private $dateCommande;
    private $numeroTable;
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

    public function getNumeroTable() 
    {
        return $this->numeroTable;
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

    public function setNumeroTable($numeroTable): void 
    {
        $this->numeroTable = $numeroTable;
    }


}
