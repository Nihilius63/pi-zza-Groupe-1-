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
                    $marqueId = null;
                    if (isset($urlParts[2])) 
                    {
                            $marqueId = (int) $urlParts[2];
                    }
                    $controller = new produitControllers($requestMethod, $marqueId);
                    $controller->processRequest();
                    break;
		case "commande" : 
                    $CarteId = null;
                    if (isset($urlParts[2])) 
                    {
                            $CarteId = (int) $urlParts[2];
                    }
                    $controller = new commandeControllers($requestMethod, $CarteId);
                    $controller->processRequest();
                    break;
                case "contient" : 
                    $CarteId = null;
                    if (isset($urlParts[2])) 
                    {
                            $CarteId = (int) $urlParts[2];
                    }
                    $controller = new contientControllers($requestMethod, $CarteId);
                    $controller->processRequest();
                    break;
                case "historique" : 
                    $CarteId = null;
                    if (isset($urlParts[2])) 
                    {
                            $CarteId = (int) $urlParts[2];
                    }
                    $controller = new historiqueControllers($requestMethod, $CarteId);
                    $controller->processRequest();
                    break;
                case "tables" : 
                    $CarteId = null;
                    if (isset($urlParts[2])) 
                    {
                            $CarteId = (int) $urlParts[2];
                    }
                    $controller = new tablesControllers($requestMethod, $CarteId);
                    $controller->processRequest();
                    break;
		default :
			header("HTTP/1.1 404 Not Found");
			exit();
			break;
	}	
	