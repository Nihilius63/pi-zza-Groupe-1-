<?php
include_once("view/header.html");
include_once ("php/DAOCommande.php");
include_once ("php/DAOContenir.php");
include_once ("php/DAOProduit.php");
include_once ("php/DAOTables.php");
include_once ("php/DAOCategorie.php");

?>
<div class="content" >
    <div id="gauche">
        <h1> Commande </h1>

<?php
$commandes=DAOCommande::getCommandeByIdTable($_GET["idTable"]);
foreach ($commandes as $commande) {
    $contients=DAOContenir::getContenirByIdCommande($commande->idCommande);
    foreach ($contients as $contient) {
        $produit=DAOProduit::getProduitByIdProduit($contient->idProduit);
        echo '<div class="produit" id="idProduit'.$produit->idProduit.'">
                <div class="ligne2">
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
                        <p id="quantiteProduit'.$produit->idProduit.'">'.$contient->quantite.'</p>
                    </div>
                    <div class="delete">
                        <img class="imgdelete" src="img/delete.png" alt="'.$produit->idProduit.'">
                    </div>
                </div></div>';
    }
}

?>
        <div class="valider">
            <p>Valider</p>
        </div>
    </div>
    <div class="droitetable">
        <h1>Nombre de personnes</h1>
        <div class="nbPersonne">
        <?php
        $table=DAOTables::getTableById($_GET['idTable']);
        echo '<p>'.$table->nbPersonne.'</p>';
        ?>

        <input id="modifNbPersonne" type="button" value="Modifier"><input type="hidden" id="idTable" value="<?php echo $table->idTables;?>"><input type="hidden" id="nbPlaces" value="<?php echo $table->nbPlaces;?>"></div>


        <H1> Ajouter produit</H1>
        <form method="post" action="traitement.php">
            <p>
                <select name="categorie" id="categorie">
                    <option value="null">Choissisez une categorie</option>
                    <?php
                    $categorie=DAOCategorie::getCategorie();
                    foreach ($categorie as $value) {
                        echo "<option id='".$value->idCategorie."' value='".$value->nomCategorie."'>".$value->nomCategorie."</option>";
                    }
                    ?>
                </select>
            </p>
        </form>
        <?php
        $produit=DAOProduit::getProduits();
        foreach ($produit as $prod) {
                echo '<div class="ligne">
                    <img class="imgProduit" src="../client/Pi_zza/'.$prod->imageProduit.'">
                    <div class="colonne">
                        <p>'.$prod->nomProduit.'</p>
                        <p>'.$prod->prixProduit.'€</p>
                    </div><img class="ajouterProduit" src="img/add.png" alt="'.$prod->idProduit.'"></div>';
        }?>
    </div>
    <script src="js/tables.js"></script>