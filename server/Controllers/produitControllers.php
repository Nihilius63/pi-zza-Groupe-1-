<?php

include_once('tools/DataBaseLinker.php');
include_once('DAO/produitDAO.php');

class produitControllers 
{
    private $requestMethod;
    private $produitId = null;
    private $request=null;
    public function __construct($requestMethod, $id,$request) 
    {
        $this->requestMethod = $requestMethod;
	$this->produitId = $id;
        $this->request=$request;
    }

public function processRequest() 
{	
    $response=$this->notFoundResponse();
    switch ($this->requestMethod) {
        case 'GET':
            if($this->request=="categorie")
            {
                $response= $this->getproduitbycate($this->produitId);
            }
            else if ($this->produitId) {
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
                $reponse=$this->updateproduit();
                $response['status_code_header'] = 'HTTP/1.1 200 Succes';
            }
            break;
        case 'DELETE':
            if($this->produitId)
            {
                $response=$this->deleteproduit($this->produitId);
            }
            break;
        default:
            $response = $this->notFoundResponse();
            break;
    }
        header($response['status_code_header']);
        if (isset($response['body']))
        {
            if ($response['body'] != null && $response['status_code_header'] === "HTTP/1.1 200 OK") 
            {
                echo $response['body'];
            }
            else 
            {
                echo $response['status_code_header'];
                echo $response['body'];
            }
        }
        else
        {
            echo $response['status_code_header'];
        }
    }
    private function getAllproduit() {		
        $result = produitDAO::getList();
        $response['body'] = json_encode($result);
        $response['status_code_header'] = 'HTTP/1.1 200 OK';
        return $response;
    }
    private function getproduitbycate($id) {		
        $result = produitDAO::getproduitbycate($id);
        $response['body'] = json_encode($result);
        $response['status_code_header'] = 'HTTP/1.1 200 OK';
        return $response;
    }

    private function getproduit($id) {	
	$result = produitDAO::get($id);
        $response['body'] = json_encode($result);
        $response['status_code_header'] = 'HTTP/1.1 200 OK';
        if ($result == null) 
        {
            return null;
        }
        return $response;
    }

    private function createproduit()
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (isset($input["nomProduit"],$input["prixProduit"],$input['imageProduit'],$input['idCategorie'])) 
        {
            $Produit=new produitDTO($input["nomProduit"],$input["imageProduit"],$input["prixProduit"],$input['idCategorie']);
            produitDAO::insert($Produit);
            $response['body'] = json_encode($Produit);
            $response['status_code_header'] = 'Succes';
            return $response;
        }
        else
        {
            $response['status_code_header'] = 'Error';
            return $response;
        }
    }
    private function updateproduit() 
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (isset($input["nomProduit"],$input["prixProduit"],$input["idProduit"],$input['imageProduit'],$input['idCategorie']))
        {
            $produit=new produitDTO($input["nomProduit"],$input["imageProduit"],$input["prixProduit"],$input['idCategorie']);
            $produit->setIdProduit($input["idProduit"]);
            produitDAO::update($produit);
        }
        else 
        {
            $response['status_code_header'] = 'HTTP/1.1 404 Not Found';
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
        private function notFoundResponse() 
    {
        $response['status_code_header'] = 'HTTP/1.1 404 Not Found';
        $response['body'] = null;
        return $response;
    }
}
