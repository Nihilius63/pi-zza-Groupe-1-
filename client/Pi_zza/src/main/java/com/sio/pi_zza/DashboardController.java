package com.sio.pi_zza;

import com.sio.pi_zza.DTO.tableInfoDTO;
import com.sio.pi_zza.DAO.tablesDAO;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private VBox boxAll;
    @FXML private ImageView imgReloadSync;
    @FXML private ImageView closeImg;
    private int cpt = 0;
    @FXML private GridPane tables;

    public void FadeTransition(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(boxAll);
        fadeTransition.setFromValue(0.8);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    public void FadeScene(VBox vbox) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(650));
        fadeTransition.setNode(vbox);
        fadeTransition.setFromValue(0.8);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    public void TransitionButton(Button button){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(500));
                fadeTransition.setNode(button);
                fadeTransition.setFromValue(0.2);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(500));
                fadeTransition.setNode(button);
                fadeTransition.setFromValue(0.2);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }
        });
    }
    public void ImageTransition() {
        closeImg.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(700));
                fadeTransition.setNode(closeImg);
                fadeTransition.setFromValue(0.2);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }
        });

        closeImg.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(700));
                fadeTransition.setNode(closeImg);
                fadeTransition.setFromValue(0.2);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }
        });
    }

    @FXML
    public void closeButton() {
        App.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition();
        ImageTransition();

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
            TransitionButton(infoSupl);

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
            if(nbPersonne == 0) {
                Stage errorTable = new Stage();
                errorTable.initStyle(StageStyle.UNDECORATED);
                VBox box = new VBox();
                FadeScene(box);
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
        App.setRoot("dashboard");
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


