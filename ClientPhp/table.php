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
        <h1> Commandes </h1>

<?php
$commandes=DAOCommande::getCommandeByIdTable($_GET["idTable"]);
echo "<div class='lignecommande'><p>Nouvelle Commande</p></div><br>"; 
foreach ($commandes as $commande) 
{   
    echo "<div class='lignecommande' id=".$commande->idCommande."><div id=".$commande->idCommande." class='commande'><p>Commande N°".$commande->idCommande."</p></div><div class='trash'> <i class='fas fa-trash-alt'></i><br></div></div>"; 
}
?>
        <div class="valider" id="hidden">
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

        <div id="hidden">
            <h1> Ajouter produit</h1>
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
        </div>
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