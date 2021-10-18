<?php

	header("Access-Control-Allow-Origin: *");
	header("Content-Type: application/json; charset=UTF-8");
	// méthodes HTTP autorisées
	header("Access-Control-Allow-Methods: OPTIONS,GET,POST,PUT,DELETE");
	// durée d'expiration du cache
	header("Access-Control-Max-Age: 3600");
	header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
	$pathWs = "pi-zza-Groupe-1-/";
	$fullUrl = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
	$url = str_replace($pathWs . "server/", "", $fullUrl);
	$urlParts = explode( '/', $url );
	$requestMethod = $_SERVER["REQUEST_METHOD"];
	function includeFileWithClassName($class_name)
    {
        $directorys = array(
            'controllers/',
            'DAO/',
            'DTO/',
            'tools/'
        );
        foreach($directorys as $directory)
        {
            if(file_exists($directory.$class_name . '.php'))
            {
                require_once($directory.$class_name . '.php');
                return;
            }           
        }
    }
	spl_autoload_register('includeFileWithClassName');
	switch ($urlParts[1]) 
        {
		
            case "produit" :
                $produitId = null;
                if (isset($urlParts[2])) 
                {
                        $produitId = (int) $urlParts[2];
                }
                $controller = new produitControllers($requestMethod, $produitId);
                $controller->processRequest();
                break;
            case "commande" : 
                $commandeId = null;
                if (isset($urlParts[2])) 
                {
                    $commandeId = (int) $urlParts[2];
                }
                $controller = new commandeControllers($requestMethod, $commandeId);
                $controller->processRequest();
                break;
            case "contient" : 
                $contientId = null;
                if (isset($urlParts[2])) 
                {
                        $contientId = (int) $urlParts[2];
                }
                $controller = new contientControllers($requestMethod, $contientId);
                $controller->processRequest();
                break;
            case "historique" : 
                $historiqueId = null;
                if (isset($urlParts[2])) 
                {
                        $historiqueId = (int) $urlParts[2];
                }
                $controller = new historiqueControllers($requestMethod, $historiqueId);
                $controller->processRequest();
                break;
            case "tables" : 
                $tablesId = null;
                if (isset($urlParts[2])) 
                {
                        $tablesId = (int) $urlParts[2];
                }
                $controller = new tablesControllers($requestMethod, $tablesId);
                $controller->processRequest();
                break;
            case "taille" : 
                $tailleId = null;
                if (isset($urlParts[2])) 
                {
                        $tailleId = (int) $urlParts[2];
                }
                $controller = new tailleControllers($requestMethod, $tailleId);
                $controller->processRequest();
                break;
            case "taille_produit" : 
                $taille_produitId = null;
                if (isset($urlParts[2])) 
                {
                        $taille_produitId = (int) $urlParts[2];
                }
                $controller = new taille_produitControllers($requestMethod, $taille_produitId);
                $controller->processRequest();
                break;
            case "categorie" : 
                $categorieId = null;
                if (isset($urlParts[2])) 
                {
                        $categorieId = (int) $urlParts[2];
                }
                $controller = new categorieControllers($requestMethod, $categorieId);
                $controller->processRequest();
                break;
            default :
                    header("HTTP/1.1 404 Not Found");
                    exit();
                    break;
	}	
	