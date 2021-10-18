<?php
include_once("view/header.html");
include_once ("php/DAOCommande.php");
include_once ("php/DAOContenir.php");
include_once ("php/DAOProduit.php");

?>
<div class="content">
    <div class="gauche">
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
                        <img class="imgdelete" src="img/delete.png">
                    </div>
                </div></div>';
    }
}

?>
    </div>
    <div class="droitetable">
        <H1> Ajouter produit</H1>
        <H3>Pizzas</H3>
        <?php
        $produit=DAOProduit::getProduits();
        foreach ($produit as $prod) {
            if ($prod->categorieProduit==1) {
                echo '<div class="ligne">
                    <img src="'.$prod->imageProduit.'" alt="">
                    <div class="colonne">
                        <p>'.$prod->nomProduit.'</p>
                    </div></div>';

            }
        }?>
    </div>