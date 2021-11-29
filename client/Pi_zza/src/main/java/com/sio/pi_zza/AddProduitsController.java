package com.sio.pi_zza;

import com.sio.pi_zza.DAO.categorieDAO;
import com.sio.pi_zza.DAO.produitDAO;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class AddProduitsController extends DashboardController implements Initializable {

    @FXML private ImageView imgReloadSync;
    @FXML private ImageView closeImg;
    @FXML private Label errorMsg;
    @FXML private Button importImage;
    @FXML private ComboBox importCategorie;
    @FXML private Button addProduit;
    @FXML private TextField nomProduits;
    @FXML private TextField prixProduits;
    @FXML private ImageView imageViewImport;
    private String imageSave = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JSONArray categorieJsonList = categorieDAO.getCategorie();
        ObservableList<String> listNameCategorie = FXCollections.observableArrayList();

        for (int i = 0; i < categorieJsonList.length(); i++) {
            JSONObject jsonList = new JSONObject(categorieJsonList.get(i).toString());
            String nomCategorie = (String) jsonList.get("nomCategorie");
            listNameCategorie.add(nomCategorie);
        }

        importCategorie.setItems(listNameCategorie);
        importCategorie.getSelectionModel().selectFirst();

        if (imageSave == "") {
            Image img = new Image("file:imgTools/interoImgAdd.png");
            imageViewImport.setImage(img);
        }
        EventHandler<MouseEvent> closeButtonEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                addProduits(nomProduits.getText(), prixProduits.getText(), imageSave, importCategorie.getSelectionModel().getSelectedItem().toString());
            }
        };

        addProduit.addEventHandler(MouseEvent.MOUSE_CLICKED, closeButtonEvent);

        EventHandler<MouseEvent> importImageEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                importImages(importImage);
            }
        };

        importImage.addEventHandler(MouseEvent.MOUSE_CLICKED, importImageEvent);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };

        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void addProduits(String nomProduits, String prixProduits, String imageProduits, String categorieProduits) {

        if (nomProduits == "" || prixProduits == "" || imageProduits == "" || categorieProduits == "") {

            errorMsg.setText("Une erreur est survenue, veuillez remplir tout les champs!");

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(time -> {
                        errorMsg.setText("");
                        imageSave = "";
                    }
            );
            pause.play();

        } else {
            try {
                if (categorieProduits.equals("Pizzas")) {
                    categorieProduits = "1";
                } else if (categorieProduits.equals("Boissons")) {
                    categorieProduits = "2";
                } else {
                    categorieProduits = "3";
                }

                String image = "images/" + nomProduits.replace(" ", "_") + ".png";

                produitDAO.createProduit(nomProduits, Float.parseFloat(prixProduits), image, Integer.parseInt(categorieProduits));

                Path copied = Paths.get("images/" + nomProduits.replace(" ", "_") + ".png");
                Path originalPath = Paths.get(imageSave.replace("file:/", ""));

                Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

                imageSave = "";
                App.setRoot("addProduit");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void importImages(Button importImage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Window.getWindows().get(0));

        imageSave = selectedFile.toURI().toString();

        if (imageSave != null) {
            importImage.setText(imageSave);
            Image img = new Image(imageSave);
            imageViewImport.setImage(img);
            imageViewImport.setFitHeight(374);
            imageViewImport.setFitWidth(322);
        } else {
            Image img = new Image("file:imgTools/interoImgAdd.png");
            imageViewImport.setImage(img);
            imageViewImport.setFitHeight(374);
            imageViewImport.setFitWidth(322);
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
        App.setRoot("addProduit");
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
