<?php
class tablesDTO implements JsonSerializable
{
    private $idTables;
    private $nbPlaces;
    private $nbPersonne;
    public function __construct($nbPlaces, $nbPersonne) 
    {
        $this->nbPlaces = $nbPlaces;
        $this->nbPersonne = $nbPersonne;
    }

    public function getNbPersonne() 
    {
        return $this->nbPersonne;
    }

    public function setNbPersonne($nbPersonne): void 
    {
        $this->nbPersonne = $nbPersonne;
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
            'nbPersonne'=>$this->nbPersonne,
        );
    }
}
