<?php

include_once('tools/DataBaseLinker.php');
include_once('DAO/contientDAO.php');
class contientControllers 
{
    private $requestMethod;
    private $CommandeId = null;
    private $ProduitId = null;
    public function getRequestMethod() {
        return $this->requestMethod;
    }

    public function getCommandeId() {
        return $this->CommandeId;
    }

    public function getProduitId() {
        return $this->ProduitId;
    }

    public function setRequestMethod($requestMethod): void {
        $this->requestMethod = $requestMethod;
    }

    public function setCommandeId($CommandeId): void {
        $this->CommandeId = $CommandeId;
    }

    public function setProduitId($ProduitId): void {
        $this->ProduitId = $ProduitId;
    }

        public function processRequest() 
    {
        $response = $this->notFoundResponse();	
        switch ($this->requestMethod) {
            case 'GET':
                if (isset($this->CommandeId,$this->ProduitId)) 
                {
                    $response = $this->getContient($this->CommandeId,$this->ProduitId);
                } 
                else if ($this->ProduitId)
                {
                    $response = $this->getContientProduit($this->ProduitId);
                }
                else if ($this->CommandeId)
                {
                    $response=$this->getContientCommande($this->CommandeId);
                }
                else 
                {
                    $response = $this->getAllContient();
                }
                break;
            case 'POST':
                if (empty($this->CommandeId)) 
                {
                    $response = $this->createContient();
                }
                break;
            case 'PUT':
                if (empty($this->CommandeId))
                {
                    $response=$this->updateContient($this->CommandeId);
                    $response['status_code_header'] = 'HTTP/1.1 200 Successfull Update';
                }
                break;
            case 'DELETE':
               if (isset($this->CommandeId,$this->ProduitId)) 
                {
                    $response = $this->deleteContient($this->CommandeId,$this->ProduitId);
                } 
                else if ($this->ProduitId)
                {
                    $response = $this->deleteByProduit($this->ProduitId);
                }
                else if ($this->CommandeId)
                {
                    $response=$this->deleteByCommande($this->CommandeId);
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

        private function getAllContient() {		
            $result = contientDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function getContientCommande($id) {	
            $result = contientDAO::getByCommande($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }
        private function getContientProduit($id) {	
            $result = contientDAO::getByProduit($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }
        private function getContient($id1,$id2) {	
            $result = contientDAO::get($id1,$id2);
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
            if (isset($input["idProduit"],$input["quantite"],$input["idCommande"])) 
            {
                $contient=new contientDTO();
                $contient->setIdCommande($input["idCommande"]);
                $contient->setIdProduit($input["idProduit"]);
                $contient->setQuantite($input["quantite"]);
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
                $contient=new contientDTO();
                $contient->setIdCommande($input["idCommande"]);
                $contient->setQuantite($input["quantite"]);
                $contient->setIdProduit($input["idProduit"]);
                contientDAO::update($contient);
            }
            return null;
        }
        private function deleteContient($id1,$id2) 
        {
            contientDAO::delete($id1,$id2);	
            $response['status_code_header'] = 'HTTP/1.1 200 Successfull deletion';
            $response['body'] = null;
            return $response;
        }
        private function deleteByProduit($id) 
        {
            contientDAO::deleteByProduit($id);	
            $response['status_code_header'] = 'HTTP/1.1 200 Successfull deletion';
            $response['body'] = null;
            return $response;
        }
        private function deleteByCommande($id) 
        {
            contientDAO::deleteByCommande($id);	
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
