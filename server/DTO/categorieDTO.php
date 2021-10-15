<?php
class categorieDTO implements JsonSerializable
{
    private $nomCategorie;
    private $idCategorie;
    public function __construct($nomCategorie) {
        $this->nomCategorie = $nomCategorie;
    }
    public function getNomCategorie() {
        return $this->nomCategorie;
    }

    public function getIdCategorie() {
        return $this->idCategorie;
    }

    public function setNomCategorie($nomCategorie): void {
        $this->nomCategorie = $nomCategorie;
    }

    public function setIdCategorie($idCategorie): void {
        $this->idCategorie = $idCategorie;
    }
    public function jsonSerialize()
    {
        return array
        (
            'idCategorie' => $this->idCategorie,
            'nomCategorie' => $this->nomCategorie,
        );
    }
}
