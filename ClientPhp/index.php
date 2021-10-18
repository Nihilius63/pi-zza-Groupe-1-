<?php
include_once ('view/header.html');
?>

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



