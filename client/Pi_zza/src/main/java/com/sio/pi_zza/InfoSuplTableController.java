package com.sio.pi_zza;

import com.sio.pi_zza.DAO.*;
import com.sio.pi_zza.DTO.tableInfoDTO;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class InfoSuplTableController extends LaunchController implements Initializable {

    @FXML private Button dashboardButton;
    @FXML private Button produitsButton;
    @FXML private Button historiqueButton;
    @FXML private Button addProducts;
    @FXML private ImageView closeImg;

    @FXML public Label numeroTable;
    @FXML private Label nombrePlaceOcu;
    @FXML private Label nombreCommandeEff;
    @FXML private Label sommeCommandes;
    @FXML private Accordion accordionCommande;
    @FXML private Button tableInnocupe;
    @FXML private Button retour;

    private int idCommandeInfo;
    private VBox commandProduct;
    private Label scrolTextCommande;
    private HBox productLigne;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };
        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        EventHandler<MouseEvent> eventReturn = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    App.setRoot("dashboard");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        retour.addEventHandler(MouseEvent.MOUSE_CLICKED, eventReturn);

        int idTables = tableInfoDTO.idTable;
        int nbPersonne = tableInfoDTO.nbPersonne;
        int nbPlaces = tableInfoDTO.nbPlace;

        int sommeCommande = 0;
        int totalCommande = 0;

        JSONArray jsonCommande = commandeDAO.getCommandeByIdTables(idTables);

        totalCommande = jsonCommande.length();

        productLigne = new HBox();
        scrolTextCommande = new Label();

        for (int i = 0; i < jsonCommande.length(); i++) {

            JSONObject json1 = new JSONObject(jsonCommande.get(i).toString());
            int idCommande = Integer.parseInt((String) json1.get("idCommande"));
            int idTable = Integer.parseInt((String) json1.get("idTables"));

            idCommandeInfo = idCommande;
            scrolTextCommande.setText("Commande numéro: " + idCommandeInfo);

            JSONArray jsonContient = contientDAO.getContientByIdCommande(idCommande);

            for (int j = 0; j < jsonContient.length(); j++) {
                commandProduct = new VBox();
                JSONObject json2 = new JSONObject(jsonContient.get(j).toString());
                int idCommande2 = Integer.parseInt((String) json2.get("idCommande"));
                int idProduit = Integer.parseInt((String) json2.get("idProduit"));
                int quantite = Integer.parseInt((String) json2.get("quantite"));

                JSONObject jsonProduit = produitDAO.getProduitById(idProduit);

                int produitId = Integer.parseInt((String) jsonProduit.get("idProduit"));
                String nomProduit = (String) jsonProduit.get("nomProduit");
                float prixProduit = Float.parseFloat((String) jsonProduit.get("prixProduit"));
                String imageProduit = (String) jsonProduit.get("imageProduit");
                int idCategorie = Integer.parseInt((String) jsonProduit.get("idCategorie"));

                sommeCommande += prixProduit * quantite;


                Label scrolTextProduit = new Label("Produit dans la commande: " + nomProduit);
                Button suprProduct = new Button("Supprimer");
                productLigne.getChildren().add(scrolTextProduit);
                productLigne.getChildren().add(suprProduct);

                EventHandler<MouseEvent> eventSuprProduct = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        suprProduitCommande(idCommande, produitId);
                    }
                };
                suprProduct.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSuprProduct);
            }


            numeroTable.setText("Numéro de la table: " + idTables);
            nombrePlaceOcu.setText("Nombre de place occupé: " + nbPersonne + " / " + nbPlaces);
            nombreCommandeEff.setText("Nombre de commande effectué: " + totalCommande);
            sommeCommandes.setText("Somme des commandes: " + sommeCommande);
        }

        HBox commandeLigne = new HBox();
        Button suprCommande = new Button("Supprimer");
        commandProduct.getChildren().add(scrolTextCommande);
        commandeLigne.getChildren().add(suprCommande);
        commandProduct.getChildren().add(commandeLigne);
        commandProduct.getChildren().add(productLigne);

        EventHandler<MouseEvent> eventSuprCommande = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                suprCommande(idCommandeInfo);
            }
        };
        suprCommande.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSuprCommande);

        TitledPane infoCommande = new TitledPane("Commande numéro " + idCommandeInfo + " /-/ Information sur la commande /-/", commandProduct);
        accordionCommande.getPanes().addAll(infoCommande);

        EventHandler<MouseEvent> eventClickInnocupe = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                clickInnocupe(idTables);
            }
        };

        tableInnocupe.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickInnocupe);
    }

    public void suprCommande(int idCommande) {
        contientDAO.deleteContientByIdCommande(idCommande);
        commandeDAO.deleteByIdCommande(idCommande);

        Stage suprCommandeStage = new Stage();
        VBox box = new VBox();
        Label msgView = new Label("Votre commmande a était supprimé avec succès!");
        box.getChildren().add(msgView);
        Scene suprCommandeScene = new Scene(box, 200, 250);
        suprCommandeStage.setScene(suprCommandeScene);
        suprCommandeStage.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(time -> {
            try {
                suprCommandeStage.hide();
                App.setRoot("infoCommande");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
        pause.play();
    }

    public void suprProduitCommande(int idCommande, int idProduit) {
        contientDAO.deleteContientByIdCommandeProduit(idCommande, idProduit);

        Stage suprProduitCommande = new Stage();
        VBox box = new VBox();
        Label msgView = new Label("Votre produit a était supprimé avec succès!");
        box.getChildren().add(msgView);
        Scene suprProduitScene = new Scene(box, 200, 250);
        suprProduitCommande.setScene(suprProduitScene);
        suprProduitCommande.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(time -> {
            try {
                suprProduitCommande.hide();
                App.setRoot("infoCommande");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
        pause.play();
    }

    public void clickInnocupe(int idTables) {
        tablesDAO.inoccupeTable(idTables);

        JSONArray jsonCommande = commandeDAO.getCommandeByIdTables(idTables);

        for (int j = 0; j < jsonCommande.length(); j++) {
            JSONObject jsoCommande = new JSONObject(jsonCommande.get(j).toString());
            int idCommande = Integer.parseInt((String) jsoCommande.get("idCommande"));

            JSONArray jsonContient = contientDAO.getContientByIdCommande(idCommande);

            for (int i = 0; i < jsonContient.length(); i++) {
                JSONObject jsoContient = new JSONObject(jsonContient.get(i).toString());
                int idProduit = Integer.parseInt((String) jsoContient.get("idProduit"));
                int quantite = Integer.parseInt((String) jsoContient.get("quantite"));

                JSONObject jsonProduit = produitDAO.getProduitById(idProduit);
                String nomProduit = (String) jsonProduit.get("nomProduit");
                float prixProduit = Integer.parseInt((String) jsonProduit.get("prixProduit"));

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();

                historiqueDAO.createHistory(nomProduit, quantite, prixProduit, dateFormat.format(date), idTables);
                contientDAO.deleteContientByIdCommande(idCommande);
                commandeDAO.deleteByIdCommande(idCommande);
            }
        }

        Stage innocuppeTable = new Stage();
        VBox box = new VBox();
        Label msgView = new Label("Votre table est désormais innocupée!");
        box.getChildren().add(msgView);
        Scene innocuppeTableScene = new Scene(box, 200, 250);
        innocuppeTable.setScene(innocuppeTableScene);
        innocuppeTable.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(time -> {
            try {
                innocuppeTable.hide();
                App.setRoot("dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
        pause.play();

    }

    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
        if (event.getSource() == dashboardButton) {
            dashboardWindow();
        }

        if (event.getSource() == produitsButton) {
            produitsWindow();
        }

        if (event.getSource() == historiqueButton) {
            historiqueWindow();
        }

        if (event.getSource() == addProducts) {
            addProductsWindow();
        }
    }

}
