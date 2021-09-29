<?php
class tablesDTO 
{
    private $idTables;
    private $nbPlaces;
    public function getIdTables() 
    {
        return $this->idTables;
    }

    public function getNbPlaces() 
    {
        return $this->nbPlaces;
    }

    public function setIdTables($idTables): void 
    {
        $this->idTables = $idTables;
    }

    public function setNbPlaces($nbPlaces): void 
    {
        $this->nbPlaces = $nbPlaces;
    }
}
