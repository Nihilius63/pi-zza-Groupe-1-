<?php
include_once("view/header.html");
include_once ("php/DAOCommande.php");
include_once ("php/DAOContenir.php");
include_once ("php/DAOProduit.php");
include_once ("php/DAOTables.php")

?>
<div class="content">
    <div id="gauche">
        <h1> Commande </h1>

<?php
$commandes=DAOCommande::getCommandeByIdTable($_GET["idTable"]);
foreach ($commandes as $commande) {
    $contients=DAOContenir::getContenirByIdCommande($commande->idCommande);
    foreach ($contients as $contient) {
        $produit=DAOProduit::getProduitByIdProduit($contient->idProduit);
        echo '<div class="produit">
                <div class="ligne">
                    <img src="'.$produit->imageProduit.'" alt="">
                    <div class="colonne">
                        <p>Nom</p>
                        <p>'.$produit->nomProduit.'</p>
                    </div>
                    <div class="colonne">
                        <p>Prix</p>
                        <p>'.$produit->prixProduit.'€</p>
                    </div>
                    <div class="colonne">
                        <p>Quantité</p>
                        <p>'.$contient->quantite.'</p>
                    </div>
                    <div class="delete">
                        <img class="imgdelete" src="img/delete.png" alt="'.$produit->idProduit.'">
                    </div>
                </div></div>';
    }
}

?>
    </div>
    <div class="droitetable">
        <h1>Nombres de personnes</h1>
        <div class="nbPersonne">
        <?php
        $table=DAOTables::getTableById($_GET['idTable']);
        echo '<p>'.$table->nbPersonne.'</p>';
        ?>
        <input id="modifNbPersonne" type="button" value="Modifier"></div>


        <H1> Ajouter produit</H1>
        <H3>Pizzas</H3>
        <?php
        $produit=DAOProduit::getProduits();
        foreach ($produit as $prod) {
            if ($prod->idCategorie==1) {
                echo '<div class="ligne">
                    <img class="imgProduit" src="'.$prod->imageProduit.'" alt="">
                    <div class="colonne">
                        <p>'.$prod->nomProduit.'</p>
                        <p>'.$prod->prixProduit.'€</p>
                    </div><img class="ajouterProduit" src="img/add.png" alt="'.$prod->idProduit.'"></div>';
            }
        }?>
    </div>
    <script src="js/tables.js"></script>