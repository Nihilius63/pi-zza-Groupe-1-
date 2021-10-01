<?php

include_once('tools/DataBaseLinker.php');
include_once('DAO/produitDAO.php');

class produitControllers 
{
    private $requestMethod;
    private $produitId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
		$this->produitId = $id;
    }

public function processRequest() 
{	
    $response = $this->notFoundResponse();	
    switch ($this->requestMethod) {
        case 'GET':
            if ($this->tablesId) {
                $response = $this->getproduit($this->produitId);
            } else 
            {
                $response = $this->getAllproduit();
            };
            break;
        case 'POST':
            if (empty($this->produitId)) 
            {
                $response = $this->createproduit();
            }
            break;
        case 'PUT':
            if (empty($this->produitId))
            {
                $reponse=$this->updateproduit($this->produitId);
            }
            break;
        case 'DELETE':
            if($this->produitId)
            {
                $reponse=$this->deleteproduit($this->produitId);
            }
            break;
        default:
            $response = $this->notFoundResponse();
            break;
    }
}
    public function getAllproduit() {		
        $result = produitDAO::getList();
        $response['body'] = json_encode($result);
        return $response;
    }

    private function getproduit($id) {	
	$result = produitDAO::get($id);
        if ($result == null) 
        {
            return null;
        }
        return $response;
    }

    private function createproduit()
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (!isset($input["nomProduit"],$input["prixProduit"],$input["tailleProduit"])) 
        {
            return $this->unprocessableEntityResponse();
        }
        else
        {
            $Produit=new produitDTO($input["nomProduit"],$input["prixProduit"],$input["tailleProduit"]);
            produitDAO::insert($Produit);
            $response['body'] = json_encode($Carte);
            return $response;
        }
    }
    private function updateproduit() 
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (isset($input["nomProduit"],$input["prixProduit"],$input["idProduit"],$input["tailleProduit"]))
        {
            $produit=new produitDTO($input["nomProduit"],$input["prixProduit"],$input["tailleProduit"]);
            $produit->setIdProduit($input["idProduit"]);
            produitDAO::update($produit);
        }
        return null;
    }
    private function deleteproduit($id) 
    {
	produitDAO::delete($id);	
        $response['status_code_header'] = 'HTTP/1.1 200 Successful deletion';
        $response['body'] = null;
        return $response;
    }
}
