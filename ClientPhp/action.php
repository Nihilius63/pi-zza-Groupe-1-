<?php

$url="http://localhost/Webservice/server/marque";

$post_tojson = array(
    'idMarque' => $_POST["idMarque"],
    'nomMarque' => $_POST["nomMarque"],
);

$options = array(
    'http' => array(
        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
        'method'  => 'PUT',
        'content' => $json=json_encode($post_tojson)

    )
);
$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);
if ($result === FALSE)
    {
        echo "erreur";
    }
else {
    echo "Votre table a bien été ajoutée";
}


?>