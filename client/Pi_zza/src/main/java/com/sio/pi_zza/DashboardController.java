package com.sio.pi_zza;

import com.sio.pi_zza.DAO.commandeDAO;
import com.sio.pi_zza.DAO.contientDAO;
import com.sio.pi_zza.DAO.produitDAO;
import com.sio.pi_zza.DTO.tableInfoDTO;
import com.sio.pi_zza.DAO.tablesDAO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Button dashboardButton;
    @FXML private Button produitsButton;
    @FXML private Button historiqueButton;
    @FXML private Button addProducts;
    @FXML private ImageView closeImg;


    private int cpt = 0;

    @FXML private GridPane tables;

    @FXML public Label numeroTable;
    @FXML private Label nombrePlaceOcu;
    @FXML private Label nombreCommandeEff;
    @FXML private Label sommeCommandes;
    @FXML private Accordion accordionCommande;
    @FXML private Button tableInnocupe;
    @FXML private Button retour;

    @FXML
    public void closeButton() {
        App.close();
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
        jsonArray = tablesDAO.getTables();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            int idTables = Integer.parseInt((String) json.get("idTables"));
            int nbPlaces = Integer.parseInt((String) json.get("nbPlaces"));
            int nbPersonne = Integer.parseInt((String) json.get("nbPersonne"));

            VBox vbo = new VBox();
            vbo.setAlignment(Pos.CENTER);
            vbo.getStyleClass().add("boxTable");

            Label numTable = new Label("Numéro de la table: " + idTables);
            Label nbrPlace = new Label("Nombre de places: " + nbPlaces);
            Label nbrPersonne = new Label("Nombre de personne: " + nbPersonne);

            HBox hub = new HBox();
            hub.setAlignment(Pos.CENTER);
            Button infoSupl = new Button("Informations");
            hub.getChildren().add(infoSupl);

            numTable.getStyleClass().add("textTable");
            nbrPlace.getStyleClass().add("textTable");
            nbrPersonne.getStyleClass().add("textTable");
            infoSupl.getStyleClass().add("infoButton");

            vbo.getChildren().add(numTable);
            vbo.getChildren().add(nbrPlace);
            vbo.getChildren().add(nbrPersonne);
            vbo.getChildren().add(hub);

            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e)
                {
                    clickInfosSupl(e, idTables, nbPersonne, nbPlaces);
                }
            };

            infoSupl.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

            tables.add(vbo, cpt % 3, cpt / 3);

            if (cpt < jsonArray.length() ) {
                cpt++;
            }
        }

    }

    public void clickInfosSupl(MouseEvent e, int idTables, int nbPersonne, int nbPlaces){
        try {
            JSONArray verifTable = commandeDAO.getCommandeByIdTables(idTables);
            if(verifTable.isNull(0)) {
                Stage errorTable = new Stage();
                errorTable.initStyle(StageStyle.UNDECORATED);
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.getStylesheets().add(String.valueOf(getClass().getResource("/assets/css/StylePrimary.css")));
                box.getStyleClass().add("boxNo");
                Label msgView = new Label("Votre table est vide!");
                msgView.getStyleClass().add("titleBar");
                box.getChildren().add(msgView);
                Scene suprCommandeScene = new Scene(box, 320, 110);
                errorTable.setScene(suprCommandeScene);
                errorTable.show();

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(time -> {
                    errorTable.hide();
                    }
                );
                pause.play();
            } else {
                tableInfoDTO tableInfo = new tableInfoDTO(idTables, nbPersonne, nbPlaces);
                App.setRoot("infoCommande");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

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

    public void dashboardWindow() throws IOException {
        App.setRoot("dashboard");
    }
    public void produitsWindow() throws IOException {
        App.setRoot("produit");
    }
    public void historiqueWindow() throws IOException {
        App.setRoot("historique");
    }
    public void addProductsWindow() throws IOException {
        App.setRoot("addProduit");
    }
}


