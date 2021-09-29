<?php
class contientDTO 
{
    private $idIngredient;
    private $idProduit;
    public function getIdIngredient() 
    {
        return $this->idIngredient;
    }

    public function getIdProduit() 
    {
        return $this->idProduit;
    }

    public function setIdIngredient($idIngredient): void 
    {
        $this->idIngredient = $idIngredient;
    }

    public function setIdProduit($idProduit): void 
    {
        $this->idProduit = $idProduit;
    }


}
