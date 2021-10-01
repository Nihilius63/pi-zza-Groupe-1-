<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/historiqueDAO.php');
class historiqueControllers 
{
        private $requestMethod;
    private $historiqueId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
		$this->historiqueId = $id;
    }

public function processRequest() 
{	
    $response = $this->notFoundResponse();	
    switch ($this->requestMethod) {
        case 'GET':
            if ($this->tablesId) {
                $response = $this->gethistorique($this->historiqueId);
            } else 
            {
                $response = $this->getAllhistorique();
            };
            break;
        case 'POST':
            if (empty($this->historiqueId)) 
            {
                $response = $this->createhistorique();
            }
            break;
        case 'PUT':
            if (empty($this->historiqueId))
            {
                $reponse=$this->updatehistorique($this->historiqueId);
            }
            break;
        case 'DELETE':
            if($this->historiqueId)
            {
                $reponse=$this->deletehistorique($this->historiqueId);
            }
            break;
        default:
            $response = $this->notFoundResponse();
            break;
    }
}
    public function getAllhistorique() {		
        $result = historiqueDAO::getList();
        $response['body'] = json_encode($result);
        return $response;
    }

    private function gethistorique($id) {	
	$result = historiqueDAO::get($id);
        if ($result == null) 
        {
            return null;
        }
        return $response;
    }

    private function createhistorique()
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (!isset($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"])) 
        {
            return $this->unprocessableEntityResponse();
        }
        else
        {
            $historique=new historiqueDTO($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"]);
            historiqueDAO::insert($historique);
            $response['body'] = json_encode($historique);
            return $response;
        }
    }
    private function updatehistorique() 
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (isset($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"]))
        {
            $historique=new produitDTO($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"]);
            $historique->setIdHistorique($input["idHistorique"]);
            historiqueDAO::update($historique);
        }
        return null;
    }
    private function deletehistorique($id) 
    {
	historiqueDAO::delete($id);	
        $response['status_code_header'] = 'HTTP/1.1 200 Successful deletion';
        $response['body'] = null;
        return $response;
    }
}
