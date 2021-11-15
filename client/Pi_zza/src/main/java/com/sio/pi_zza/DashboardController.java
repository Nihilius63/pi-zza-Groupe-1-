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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends LaunchController implements Initializable {

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

            Label lab = new Label("Numéro de la table: "+idTables+"\nNombre de places: "+nbPlaces+"\nNombre de personne: "+nbPersonne);
            Button infoSupl = new Button("Informations");

            vbo.getChildren().add(lab);
            vbo.getChildren().add(infoSupl);

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
            // BUG LIE AU WEBSERVICE QUAND IL N'Y A PAS DE COMMANDE POUR LA TABLE
            tableInfoDTO tableInfo = new tableInfoDTO(idTables, nbPersonne, nbPlaces);
            App.setRoot("infoCommande");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void clickInnocupe(MouseEvent event, int idTables) {

        Stage newStage = new Stage();
        VBox Box = new VBox();
        HBox HuBox = new HBox();

        Box.getChildren().add(HuBox);

        Scene stageScene = new Scene(Box, 500, 200);
        newStage.setScene(stageScene);
        newStage.show();

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
}


