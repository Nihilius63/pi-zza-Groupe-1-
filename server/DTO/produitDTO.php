<?php
class produitDTO implements JsonSerializable
{
    private $idProduit;
    private $nomProduit;
    private $imageProduit;
    private $prixProduit;
    private $idCategorie;
    public function __construct($nomProduit, $imageProduit, $prixProduit, $idCategorie) {
        $this->nomProduit = $nomProduit;
        $this->imageProduit = $imageProduit;
        $this->prixProduit = $prixProduit;
        $this->idCategorie = $idCategorie;
    }

    public function getIdProduit() {
        return $this->idProduit;
    }

    public function getNomProduit() {
        return $this->nomProduit;
    }

    public function getImageProduit() {
        return $this->imageProduit;
    }

    public function getPrixProduit() {
        return $this->prixProduit;
    }

    public function getIdCategorie() {
        return $this->idCategorie;
    }

    public function setIdProduit($idProduit): void {
        $this->idProduit = $idProduit;
    }

    public function setNomProduit($nomProduit): void {
        $this->nomProduit = $nomProduit;
    }

    public function setImageProduit($imageProduit): void {
        $this->imageProduit = $imageProduit;
    }

    public function setPrixProduit($prixProduit): void {
        $this->prixProduit = $prixProduit;
    }

    public function setIdCategorie($idCategorie): void {
        $this->idCategorie = $idCategorie;
    }

        
    public function jsonSerialize()
    {
        return array
        (
            'idProduit' => $this->idProduit,
            'nomProduit' => $this->nomProduit,
            'prixProduit'=>$this->prixProduit,
            'imageProduit'=>$this->imageProduit,
            'idCategorie'=>$this->idCategorie
        );
    }

}
