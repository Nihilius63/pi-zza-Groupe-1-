<?php
class produitDTO implements JsonSerializable
{
    private $idProduit;
    private $nomProduit;
    private $imageProduit;
    private $prixProduit;
    private $tailleProduit;
    private $categorieProduit;
    
    public function __construct($nomProduit, $imageProduit, $prixProduit, $tailleProduit, $categorieProduit) {
        $this->nomProduit = $nomProduit;
        $this->imageProduit = $imageProduit;
        $this->prixProduit = $prixProduit;
        $this->tailleProduit = $tailleProduit;
        $this->categorieProduit = $categorieProduit;
    }
    public function getCategorieProduit() {
        return $this->categorieProduit;
    }

    public function setCategorieProduit($categorieProduit): void {
        $this->categorieProduit = $categorieProduit;
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
    public function jsonSerialize()
    {
        return array
        (
            'idProduit' => $this->idProduit,
            'nomProduit' => $this->nomProduit,
            'prixProduit'=>$this->prixProduit,
            'tailleProduit'=>$this->tailleProduit,
            'imageProduit'=>$this->imageProduit,
            'categorieProduit'=>$this->categorieProduit
        );
    }

}
