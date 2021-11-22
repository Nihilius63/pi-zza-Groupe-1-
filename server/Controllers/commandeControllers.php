<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/commandeDAO.php');
class commandeControllers 
{
    private $requestMethod;
    private $commandeId = null;
    private $tablesId = null;
    private $last =null;
    public function __construct($requestMethod,$id1,$id2,$last) 
    {
        $this->requestMethod = $requestMethod;
        $this->commandeId = $id1;
        $this->tablesId = $id2;
        $this->last=$last ;
    }

    public function processRequest() 
    {
        $response = $this->notFoundResponse();	
        switch ($this->requestMethod) {
            case 'GET': 
                if ($this->tablesId)
                {
                    $response = $this->getCommandesbyTables($this->tablesId);
                }
                else if ($this->last) {
                    $response = $this->getLast();
                } 
                else if ($this->commandeId) {
                    $response = $this->getCommandes($this->commandeId);
                } 
                else 
                {
                    $response = $this->getAllCommandes();
                };
                break;
            case 'POST':
                if (empty($this->commandeId)) 
                {
                    $response = $this->createCommandes();
                }
                break;
            case 'PUT':
                if (empty($this->commandeId))
                {
                    $response=$this->updateTables($this->commandeId);
                    $response['status_code_header'] = 'HTTP/1.1 200 Successfull Update';
                }
                break;
            case 'DELETE':
                if($this->commandeId)
                {
                    $response=$this->deleteTables($this->commandeId);
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

        private function getAllCommandes() {		
            $result = commandeDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }
        private function getLast() {	
            $result = commandeDAO::getLast();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }
        private function getCommandesbyTables($id) {	
            $result = commandeDAO::getbytables($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }
        private function getCommandes($id) {	
            $result = commandeDAO::get($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function createCommandes()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idTables"])) 
            {
                $commande=new commandeDTO($input["idTables"]);
                commandeDAO::insert($commande);
                $response['body'] = json_encode($commande);
                $response['status_code_header'] = 'HTTP/1.1 200 Successfull';
                return $response;
            }
            else 
            {
                $response['status_code_header'] = 'HTTP/1.1 200 Error';
                return $response;
            }
        }
        private function updateTables() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idCommande"],$input["idTables"]))
            {
                $commande=new commandeDTO($input["idTables"]);
                $commande->setIdCommande($input["idCommande"]);
                commandeDAO::update($commande);
            }
            else
            {
                $response['status_code_header']="Error";
            }
            return null;
        }
        private function deleteTables($id) 
        {
            commandeDAO::delete($id);	
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
