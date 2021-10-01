<?php

include_once('tools/DataBaseLinker.php');
include_once('DAO/contientDAO.php');
class contientControllers 
{
    private $requestMethod;
    private $contientId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
        $this->contientId = $id;
    }

    public function processRequest() 
    {
        $response = $this->notFoundResponse();	
        switch ($this->requestMethod) {
            case 'GET':
                if ($this->contientId) {
                    $response = $this->getContient($this->contientId);
                } else 
                {
                    $response = $this->getAllContient();
                };
                break;
            case 'POST':
                if (empty($this->contientId)) 
                {
                    $response = $this->createContient();
                }
                break;
            case 'PUT':
                if (empty($this->contientId))
                {
                    $reponse=$this->updateContient($this->contientId);
                }
                break;
            case 'DELETE':
                if($this->contientId)
                {
                    $reponse=$this->deleteContient($this->contientId);
                }
                break;
            default:
                $response = $this->notFoundResponse();
                break;
        }
    }

        public function getAllContient() {		
            $result = contientDAO::getList();
            $response['body'] = json_encode($result);
            return $response;
        }

        private function getContient($id) {	
            $result = contientDAO::get($id);
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createContient()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (!isset($input["idCommande"],$input["idProduit"],$input["quantite"])) 
            {
                return $this->unprocessableEntityResponse();
            }
            else
            {
                $contient=new contientDTO($input["idProduit"],$input["quantite"]);
                contientDAO::insert($contient);
                $response['body'] = json_encode($contient);
                return $response;
            }
        }
        private function updateContient() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idCommande"],$input["idProduit"],$input["quantite"]))
            {
                $this->contientId=contientDTO($input["idProduit"],$input["quantite"]);
                $this->contientId->setIdCommande($input["idCommande"]);
                commandeDAO::update($this->contientId);
            }
            return null;
        }
        private function deleteContient($id) 
        {
            contientDAO::delete($id);	
            $response['status_code_header'] = 'HTTP/1.1 200 Successful deletion';
            $response['body'] = null;
            return $response;
        }
}
