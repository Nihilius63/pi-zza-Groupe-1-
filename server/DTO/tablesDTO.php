<?php
class tablesDTO implements JsonSerializable
{
    private $idTables;
    private $nbPlaces;
    public function __construct($nbPlaces) 
    {
        $this->nbPlaces = $nbPlaces;
    }

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
    public function jsonSerialize()
    {
        return array
        (
            'idTables' => $this->idTables,
            'nbPlaces' => $this->nbPlaces,
        );
    }
}
