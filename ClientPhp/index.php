<!doctype html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>Titre de la page</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/tables.js"></script>
    <script src="https://kit.fontawesome.com/de5caafa4a.js" crossorigin="anonymous"></script>
</head>
<body>
<header>
    <div class="header">
        <img src="img/logo.png" alt="">
        <p>πzza</p>
    </div>
</header>

<div class="content">
    <div class="droite">
        <h1> Toutes les tables </h1>
        <div class="affichageTable">
            <?php
            include_once ("php/DAOTables.php");
            $tab= DAOTables::affichageTable();
            foreach ($tab as $value) {
                echo $value;
            }
            ?>
        </div>
    </div>
</div>

</body>
</html>



