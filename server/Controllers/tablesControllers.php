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
        $response = $this->notFoundResponse();	
        switch ($this->requestMethod) {
            case 'GET':
                if ($this->tablesId) {
                    $response = $this->getTable($this->tableId);
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
                    $reponse=$this->updateTables($this->tablesId);
                }
                break;
            case 'DELETE':
                if($this->tablesId)
                {
                    $reponse=$this->deleteTables($this->tablesId);
                }
                break;
            default:
                $response = $this->notFoundResponse();
                break;
        }
    }

        public function getAllTables() {		
            $result = tablesDAO::getList();
            $response['body'] = json_encode($result);
            return $response;
        }

        private function getTable($id) {	
            $result = produitDAO::get($id);
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createTables()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (!isset($input["numeroPlaces"])) 
            {
                return $this->unprocessableEntityResponse();
            }
            else
            {
                $tables=new tablesDTO($input["numeroPlaces"]);
                tablesDAO::insert($tables);
                $response['body'] = json_encode($Carte);
                return $response;
            }
        }
        private function updateTables() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["numeroPlaces"]))
            {
                $tables=newtablesDTO($input["numeroPlaces"]);
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
}
