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
                    $response=$this->updateContient($this->contientId);
                    $response['status_code_header'] = 'HTTP/1.1 200 Successfull Update';
                }
                break;
            case 'DELETE':
                if($this->contientId)
                {
                    $response=$this->deleteContient($this->contientId);
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

        public function getAllContient() {		
            $result = contientDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function getContient($id) {	
            $result = contientDAO::get($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createContient()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idCommande"],$input["idProduit"],$input["quantite"])) 
            {
                $contient=new contientDTO($input["idProduit"],$input["quantite"]);
                $contient->setIdCommande($input["idCommande"]);
                contientDAO::insert($contient);
                $response['body'] = json_encode($contient);
                $response['status_code_header'] = 'HTTP/1.1 200 Successfull';
                return $response;
            }
            else 
            {
                $response['status_code_header'] = 'HTTP/1.1 200 Error';
                return $response;
            }
        }
        private function updateContient() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idCommande"],$input["idProduit"],$input["quantite"]))
            {
                $contient=new contientDTO($input["idProduit"],$input["quantite"]);
                $contient->setIdCommande($input["idCommande"]);
                contientDAO::update($contient);
            }
            return null;
        }
        private function deleteContient($id) 
        {
            contientDAO::delete($id);	
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
