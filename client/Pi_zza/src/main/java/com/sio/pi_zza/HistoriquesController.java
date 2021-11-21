package com.sio.pi_zza;

import com.sio.pi_zza.DAO.historiqueDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoriquesController extends LaunchController implements Initializable {

    private int cpt = 0;

    @FXML private GridPane historiques;

    @FXML private Button dashboardButton;
    @FXML private Button produitsButton;
    @FXML private Button historiqueButton;
    @FXML private Button addProducts;
    @FXML private ImageView closeImg;

    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
        if(event.getSource() == dashboardButton) {
            dashboardWindow();
        }

        if(event.getSource() == produitsButton) {
            produitsWindow();
        }

        if(event.getSource() == historiqueButton) {
            historiqueWindow();
        }

        if(event.getSource() == addProducts) {
            addProductsWindow();
        }
    }

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

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            String nomProduit = (String) json.get("nomProduit");
            int quantite = Integer.parseInt((String) json.get("quantite"));
            int prixProduit = Integer.parseInt((String) json.get("prixProduit"));
            String date = (String) json.get("dateCommande");
            int numeroTable = Integer.parseInt((String) json.get("numeroTables"));

            //Produit: plMoelleuxauxgrgrgg => 15nbr sinon plus petit

            Pane boxHistorique = new Pane();
            VBox.setMargin(boxHistorique, new Insets(10,10,10,10)); //Test Margin
            VBox vb = new VBox();
            Label Produit = new Label("Produit: " + nomProduit);
            Label Prix = new Label("Prix: " + prixProduit + "€");
            Label DateHistory = new Label("Date: " + date);
            Label Quantite = new Label("Quantite: " + quantite);
            Label Table = new Label("Table numéro: " + numeroTable);

            Produit.setPadding(new Insets(10, 0, 0, 0));
            Prix.setPadding(new Insets(10, 0, 0, 0));
            DateHistory.setPadding(new Insets(10, 0, 0, 0));
            Quantite.setPadding(new Insets(10, 0, 0, 0));
            Table.setPadding(new Insets(10, 0, 0, 0));

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

            vb.getChildren().add(Produit);
            vb.getChildren().add(Prix);
            vb.getChildren().add(DateHistory);
            vb.getChildren().add(Quantite);
            vb.getChildren().add(Table);

            boxHistorique.getChildren().add(vb);
            historiques.add(boxHistorique, cpt % 3, cpt / 3);

            if (cpt < jsonArray.length() ) {
                cpt++;
            }

        }

    }

}
