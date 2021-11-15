<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/tablesDAO.php');
class tablesControllers 
{
    private $requestMethod;
    private $tablesId = null;
    public function __construct($requestMethod, $id) 
    {
        $this->requestMethod = $requestMethod;
        $this->tablesId = $id;
    }

    public function processRequest() 
    {
        $response=$this->notFoundResponse();
        switch ($this->requestMethod) {
            case 'GET':
                if ($this->tablesId) {
                    $response = $this->getTable($this->tablesId);
                } else 
                {
                    $response = $this->getAllTables();
                };
                break;
            case 'POST':
                if (empty($this->tablesId)) 
                {
                    $response = $this->createTables();
                }
                break;
            case 'PUT':
                if (empty($this->tablesId))
                {
                    $reponse=$this->updateTables();
                    $response['status_code_header'] = 'HTTP/1.1 200 Succes';
                }
                break;
            case 'DELETE':
                if($this->tablesId)
                {
                    $response=$this->deleteTables($this->tablesId);
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
        private function getAllTables() {		
            $result = tablesDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function getTable($id) {	
            $result = tablesDAO::get($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createTables()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["nbPlaces"])) 
            {
                $tables=new tablesDTO($input["nbPlaces"],$input["nbPersonne"]);
                tablesDAO::insert($tables);
                $response['status_code_header'] = 'Success';
                return $response;
            }
            else
            {
                $response['status_code_header'] = 'Error';
                return $response;
            }

        }
        private function updateTables() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["nbPlaces"]))
            {
                $tables=new tablesDTO($input["nbPlaces"],$input["nbPersonne"]);
                $tables->setIdTables($input["idTables"]);
                tablesDAO::update($tables);
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
         private function notFoundResponse() 
    {
        $response['status_code_header'] = 'HTTP/1.1 404 Not Found';
        $response['body'] = null;
        return $response;
    }
}
