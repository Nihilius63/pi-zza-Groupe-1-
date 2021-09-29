<?php
class produitDTO 
{
    private $idProduit;
    private $nomProduit;
    private $imageProduit;
    private $prixProduit;
    private $tailleProduit;
    
    public function __construct($nomProduit, $imageProduit, $prixProduit, $tailleProduit) 
    {
        $this->nomProduit = $nomProduit;
        $this->imageProduit = $imageProduit;
        $this->prixProduit = $prixProduit;
        $this->tailleProduit = $tailleProduit;
    }

    public function getImageProduit() 
    {
        return $this->imageProduit;
    }

    public function setImageProduit($imageProduit): void 
    {
        $this->imageProduit = $imageProduit;
    }

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
