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
            if ($this->historiqueId) {
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
                $response['status_code_header'] = 'HTTP/1.1 200 Successfull Update';
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
    public function getAllhistorique() {		
        $result = historiqueDAO::getList();
        $response['body'] = json_encode($result);
        $response['status_code_header'] = 'HTTP/1.1 200 OK';
        return $response;
    }

    private function gethistorique($id) {	
	$result = historiqueDAO::get($id);
        $response['body'] = json_encode($result);
        $response['status_code_header'] = 'HTTP/1.1 200 OK';
        if ($result == null) 
        {
            return null;
        }
        return $response;
    }

    private function createhistorique()
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (isset($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"])) 
        {
            $historique=new historiqueDTO($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"]);
            historiqueDAO::insert($historique);
            $response['body'] = json_encode($historique);
            $response['status_code_header'] = 'HTTP/1.1 200 Successfull';
            return $response;
        }
        else 
        {
                $response['status_code_header'] = 'HTTP/1.1 200 Error';
                return $response;
        }
    }
    private function updatehistorique() 
    {
        $input = (array) json_decode(file_get_contents('php://input'), TRUE);
        if (isset($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"]))
        {
            $historique=new historiqueDTO($input["nomProduit"],$input["quantite"],$input["prixProduit"],$input["dateCommande"],$input["numeroTables"]);
            $historique->setIdHistorique($input["idHistorique"]);
            historiqueDAO::update($historique);
        }
        return null;
    }
    private function deletehistorique($id) 
    {
	historiqueDAO::delete($id);	
        $response['status_code_header'] = 'HTTP/1.1 200 Successfull deletion';
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
