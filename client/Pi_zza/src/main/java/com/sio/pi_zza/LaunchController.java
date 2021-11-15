package com.sio.pi_zza;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class LaunchController implements Initializable{

    @FXML private  Button dashboardButton;
    @FXML private Button produitsButton;
    @FXML private Button historiqueButton;
    @FXML private Button addProducts;

    @FXML private ImageView closeImg;
    @FXML private ImageView logoImg;
    @FXML private ImageView iconDashboard;
    @FXML private ImageView iconProduit;
    @FXML private ImageView iconHistorique;
    @FXML private ImageView iconAddProduit;

    @FXML private VBox boxAll;

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

    @FXML
    public void closeButton() {
        App.close();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };

        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}
