<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/categorieDAO.php');
class categorieControllers 
{
    private $requestMethod;
    private $categorieId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
        $this->categorieId = $id;
    }

    public function processRequest() 
    {
        $response=$this->notFoundResponse();
        switch ($this->requestMethod) {
            case 'GET':
                if ($this->categorieId) {
                    $response = $this->getCategorie($this->categorieId);
                } else 
                {
                    $response = $this->getAllCategorie();
                };
                break;
            case 'POST':
                if (empty($this->categorieId)) 
                {
                    $response = $this->createCategorie();
                }
                break;
            case 'PUT':
                if (empty($this->categorieId))
                {
                    $reponse=$this->updateCategorie();
                    $response['status_code_header'] = 'HTTP/1.1 200 Succes';
                }
                break;
            case 'DELETE':
                if($this->categorieId)
                {
                    $response=$this->deleteCategorie($this->categorieId);
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
        private function getAllCategorie() {		
            $result = categorieDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function getCategorie($id) {	
            $result = categorieDAO::get($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createCategorie()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["nomCategorie"])) 
            {
                $categorie=new categorieDTO($input["nomCategorie"]);
                categorieDAO::insert($categorie);
                $response['status_code_header'] = 'Success';
                return $response;
            }
            else
            {
                $response['status_code_header'] = 'Error';
                return $response;
            }

        }
        private function updateCategorie() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["nomCategorie"]))
            {
                $categorie=new categorieDTO($input["nomCategorie"]);
                $categorie->setIdCategorie($input["idCategorie"]);
                categorieDAO::update($categorie);
            }
            return null;
        }
        private function deleteCategorie($id) 
        {
            categorieDAO::delete($id);	
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
