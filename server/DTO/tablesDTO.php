<?php
class tablesDTO 
{
    private $idTables;
    private $numeroPlaces;
    public function __construct($numeroPlaces) 
    {
        $this->numeroPlaces = $numeroPlaces;
    }

    public function getIdTables() 
    {
        return $this->idTables;
    }

    public function getNbPlaces() 
    {
        return $this->numeroPlaces;
    }

    public function setIdTables($idTables): void 
    {
        $this->idTables = $idTables;
    }

    public function setNbPlaces($numeroPlaces): void 
    {
        $this->numeroPlaces = $numeroPlaces;
    }
}
