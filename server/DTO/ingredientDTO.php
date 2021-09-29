<?php
class ingredientDTO 
{
    private $idIngredient;
    private $nomIngredient;
    public function getIdIngredient() 
    {
        return $this->idIngredient;
    }

    public function getNomIngredient() 
    {
        return $this->nomIngredient;
    }

    public function setIdIngredient($idIngredient): void 
    {
        $this->idIngredient = $idIngredient;
    }

    public function setNomIngredient($nomIngredient): void 
    {
        $this->nomIngredient = $nomIngredient;
    }


}
