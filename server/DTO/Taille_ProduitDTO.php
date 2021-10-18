<?php
class taille_produitDTO implements JsonSerializable
{
    private $idTaille;
    private $idProduit;
    public function getIdTaille() {
        return $this->idTaille;
    }

    public function getIdProduit() {
        return $this->idProduit;
    }

    public function setIdTaille($idTaille): void {
        $this->idTaille = $idTaille;
    }

    public function setIdProduit($idProduit): void {
        $this->idProduit = $idProduit;
    }
    public function jsonSerialize()
    {
        return array
        (
            'idTaille' => $this->idTaille,
            'idProduit' => $this->idProduit,
        );
    }
}
