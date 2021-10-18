<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/tailleDAO.php');
class tailleControllers 
{
     private $requestMethod;
    private $tailleId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
        $this->tailleId = $id;
    }

    public function processRequest() 
    {
        $response=$this->notFoundResponse();
        switch ($this->requestMethod) {
            case 'GET':
                if ($this->tailleId) {
                    $response = $this->getTaille($this->tailleId);
                } else 
                {
                    $response = $this->getAllTaille();
                };
                break;
            case 'POST':
                if (empty($this->tailleId)) 
                {
                    $response = $this->createTaille();
                }
                break;
            case 'PUT':
                if (empty($this->tailleId))
                {
                    $reponse=$this->updateTaille();
                    $response['status_code_header'] = 'HTTP/1.1 200 Succes';
                }
                break;
            case 'DELETE':
                if($this->tailleId)
                {
                    $response=$this->deleteCategorie($this->tailleId);
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
        public function getAllTaille() {
            $result = tailleDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function getTaille($id) {	
            $result = tailleDAO::get($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createTaille()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["nomTaille"])) 
            {
                $Taille=new tailleDTO($input["nomTaille"]);
                tailleDAO::insert($Taille);
                $response['status_code_header'] = 'Success';
                return $response;
            }
            else
            {
                $response['status_code_header'] = 'Error';
                return $response;
            }

        }
        private function updateTaille() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idTaille"],$input["nomTaille"]))
            {
                $taille=new tailleDTO($input["nomTaille"]);
                $taille->setIdTaille($input["idTaille"]);
                tailleDAO::update($taille);
            }
            return null;
        }
        private function deleteCategorie($id) 
        {
            taillleDAO::delete($id);	
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
