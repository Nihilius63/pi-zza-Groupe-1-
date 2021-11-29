package com.sio.pi_zza;

import com.sio.pi_zza.DAO.historiqueDAO;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoriquesController extends DashboardController implements Initializable {

    private int cpt = 0;
    @FXML private ImageView imgReloadSync;
    @FXML private GridPane historiques;
    @FXML private ImageView closeImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EventHandler<MouseEvent> closeButtonEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };

        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, closeButtonEvent);

        JSONArray jsonArray = new JSONArray();
        jsonArray = historiqueDAO.getHistorique();
        float taille = (jsonArray.length()/3) * 172;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            String nomProduit = (String) json.get("nomProduit");
            int quantite = Integer.parseInt((String) json.get("quantite"));
            float prixProduit = Float.parseFloat((String) json.get("prixProduit"));
            String date = (String) json.get("dateCommande");
            int numeroTable = Integer.parseInt((String) json.get("numeroTables"));

            Pane boxHistorique = new Pane();
            VBox vb = new VBox();
            Label Produit = new Label("Produit: " + nomProduit);
            Label Prix = new Label("Prix: " + prixProduit + "€");
            Label DateHistory = new Label("Date: " + date);
            Label Quantite = new Label("Quantite: " + quantite);
            Label Table = new Label("Table numéro: " + numeroTable);

            boxHistorique.getStyleClass().add("boxHistory");

            if(nomProduit.length() <= 15) {
                Produit.getStyleClass().add("boxHistoryInfo");
            } else {
                Produit.getStyleClass().add("boxHistoryInfoLittle");
            }

            Prix.getStyleClass().add("boxHistoryInfo");
            DateHistory.getStyleClass().add("boxHistoryInfo");
            Quantite.getStyleClass().add("boxHistoryInfo");
            Table.getStyleClass().add("boxHistoryInfo");

            vb.setAlignment(Pos.CENTER);

            vb.getChildren().add(Produit);
            vb.getChildren().add(Prix);
            vb.getChildren().add(DateHistory);
            vb.getChildren().add(Quantite);
            vb.getChildren().add(Table);

            boxHistorique.getChildren().add(vb);
            historiques.setPrefHeight(taille);
            historiques.add(boxHistorique, cpt % 3, cpt / 3);

            if (cpt < jsonArray.length() ) {
                cpt++;
            }
        }
    }

    @FXML public void dashboardWindow() throws IOException {
        App.setRoot("dashboard");
    }
    @FXML public void produitsWindow() throws IOException {
        App.setRoot("produit");
    }
    @FXML public void historiqueWindow() throws IOException {
        App.setRoot("historique");
    }
    @FXML public void addProductsWindow() throws IOException {
        App.setRoot("addProduit");
    }
    @FXML private void imgReloadButton() throws  IOException {
        App.setRoot("historique");
    }
    @FXML private void moovRotate() {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(2));
        rotateTransition.setNode(imgReloadSync);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }
}
