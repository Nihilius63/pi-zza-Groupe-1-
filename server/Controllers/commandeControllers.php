<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/commandeDAO.php');
class commandeControllers 
{
    private $requestMethod;
    private $commandeId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
        $this->commandeId = $id;
    }

    public function processRequest() 
    {
        $response = $this->notFoundResponse();	
        switch ($this->requestMethod) {
            case 'GET':
                if ($this->commandeId) {
                    $response = $this->getCommandes($this->tableId);
                } else 
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
                    $reponse=$this->updateTables($this->commandeId);
                }
                break;
            case 'DELETE':
                if($this->commandeId)
                {
                    $reponse=$this->deleteTables($this->commandeId);
                }
                break;
            default:
                $response = $this->notFoundResponse();
                break;
        }
    }

        public function getAllCommandes() {		
            $result = commandeDAO::getList();
            $response['body'] = json_encode($result);
            return $response;
        }

        private function getCommandes($id) {	
            $result = commandeDAO::get($id);
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createCommandes()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (!isset($input["idCommande"],$input["idTables"])) 
            {
                return $this->unprocessableEntityResponse();
            }
            else
            {
                $commande=new commandeDTO($input["idCommande"],$input["idTables"]);
                tablesDAO::insert($commande);
                $response['body'] = json_encode($commande);
                return $response;
            }
        }
        private function updateTables() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idCommande"],$input["idTables"]))
            {
                $this->commandeId=commandeDTO($input["idTables"]);
                $this->commandeId->setIdCommande($input["idCommande"]);
                commandeDAO::update($this->commandeId);
            }
            return null;
        }
        private function deleteTables($id) 
        {
            tablesDAO::delete($id);	
            $response['status_code_header'] = 'HTTP/1.1 200 Successful deletion';
            $response['body'] = null;
            return $response;
        }
}
