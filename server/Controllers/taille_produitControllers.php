<?php
include_once('tools/DataBaseLinker.php');
include_once('DAO/taille_produitDAO.php');
class taille_produitControllers 
{
    private $requestMethod;
    private $tailleId = null;
    private $produitId=null;
    public function getTailleId() 
    {
        return $this->tailleId;
    }

    public function getProduitId() 
    {
        return $this->produitId;
    }

    public function setTailleId($tailleId): void 
    {
        $this->tailleId = $tailleId;
    }

    public function setProduitId($produitId): void 
    {
        $this->produitId = $produitId;
    }
    public function getRequestMethod() {
        return $this->requestMethod;
    }

    public function setRequestMethod($requestMethod): void {
        $this->requestMethod = $requestMethod;
    }

        public function processRequest() 
    {
        $response=$this->notFoundResponse();
        switch ($this->requestMethod) {
            case 'GET':
                if (isset($this->tailleId,$this->produitId)) 
                {
                    $response = $this->getTaille_Produit($this->tailleId,$this->produitId);
                } 
                else if ($this->produitId)
                {
                    $response = $this->getTaille_ProduitByProduit($this->produitId);
                }
                else if ($this->tailleId)
                {
                    $response=$this->getTaille_ProduitByTaille($this->tailleId);
                }
                else 
                {
                    $response = $this->getAllTaille_Produit();
                }
                break;
            case 'POST':
                if (empty($this->Taille_produitId)) 
                {
                    $response = $this->createTaille_Produit();
                }
                break;
            case 'PUT':
                if (empty($this->Taille_produitId))
                {
                    $reponse=$this->updateTaille_produit();
                    $response['status_code_header'] = 'HTTP/1.1 200 Succes';
                }
                break;
            case 'DELETE':
                if (isset($this->tailleId,$this->produitId)) 
                {
                    $response = $this->deleteTaille_produit($this->tailleId,$this->produitId);
                } 
                else if ($this->produitId)
                {
                    $response = $this->deleteTaille_produitByProduit($this->produitId);
                }
                else if ($this->tailleId)
                {
                    $response=$this->deleteTaille_produitByTaille($this->tailleId);
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
        public function getAllTaille_Produit() {		
            $result = taille_produitDAO::getList();
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            return $response;
        }

        private function getTaille_Produit($id1,$id2) {	
            $result = taille_produitDAO::get($id1,$id2);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }
        private function getTaille_ProduitByProduit($id) {	
            $result = taille_produitDAO::getByProduit($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }
        private function getTaille_ProduitByTaille($id) {	
            $result = taille_produitDAO::getByTaille($id);
            $response['body'] = json_encode($result);
            $response['status_code_header'] = 'HTTP/1.1 200 OK';
            if ($result == null) 
            {
                return null;
            }
            return $response;
        }

        private function createTaille_Produit()
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idTaille"],$input["idProduit"])) 
            {
                $Taille_produit=new taille_produitDTO();
                $Taille_produit->setIdTaille($input["idTaille"]);
                $Taille_produit->setIdProduit($input["idProduit"]);
                taille_produitDAO::insert($Taille_produit);
                $response['status_code_header'] = 'Success';
                return $response;
            }
            else
            {
                $response['status_code_header'] = 'Error';
                return $response;
            }

        }
        private function updateTaille_produit() 
        {
            $input = (array) json_decode(file_get_contents('php://input'), TRUE);
            if (isset($input["idTaille"],$input["idProduit"]))
            {
                $Taille_produit=new taille_produitDTO();
                $Taille_produit->setIdTaille($input["idTaille"]);
                $Taille_produit->setIdProduit($input["idProduit"]);
                taille_produitDAO::update($Taille_produit);
            }
            return null;
        }
        private function deleteTaille_produit($id1,$id2) 
        {
            taille_produitDAO::delete($id1,$id2);	
            $response['status_code_header'] = 'HTTP/1.1 200 Successful deletion';
            $response['body'] = null;
            return $response;
        }
        private function deleteTaille_produitByTaille($id) 
        {
            taille_produitDAO::deleteByTaille($id);	
            $response['status_code_header'] = 'HTTP/1.1 200 Successful deletion';
            $response['body'] = null;
            return $response;
        }
        private function deleteTaille_produitByProduit($id) 
        {
            taille_produitDAO::deleteByProduit($id);	
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
