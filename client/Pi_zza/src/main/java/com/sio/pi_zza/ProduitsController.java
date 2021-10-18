package com.sio.pi_zza;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class ProduitsController extends HistoriquesController {

    @FXML private GridPane produitsGrid;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;

    @FXML private GridPane categorieGrid;

    @FXML private Button importImage;
    @FXML private ComboBox importCategorie;
    @FXML private Button addImages;

    @FXML private TextField nomProduits;
    @FXML private TextField prixProduits;

    private String img2 = "";

    public void importImages(MouseEvent event, Button importImage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Window.getWindows().get(0));

        img2 = selectedFile.toURI().toString();

        if(img2 != null) {
            importImage.setText(img2);
        }
    }

    public void addProduits(MouseEvent event, String nomProduits, String prixProduits, String imageProduits, String categorieProduits) {

        if(nomProduits == "" || prixProduits == "" || imageProduits == "" || categorieProduits == "") {

            VBox box = new VBox();

            Stage ajoutProduit = new Stage();
            ajoutProduit.initStyle(StageStyle.UNDECORATED);
            Scene ajoutProduitScene = new Scene(box, 300, 50);
            ajoutProduit.setScene(ajoutProduitScene);

            ajoutProduit.show();

            Label text = new Label("Erreur, veuillez remplir toute les cases correctement!");
            box.getChildren().add(text);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(time -> {
                    ajoutProduit.hide();
                    img2 = "";
                }
            );

            pause.play();

        }

        else {
            try {

                Client client = ClientBuilder.newClient();
                WebTarget webTarget = client.target("http://localhost/WebserviceTD/server/produit");

                Invocation.Builder invocationBuilder
                        = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
                invocationBuilder.header("some-header", "true");
                Response response = invocationBuilder.get();
                System.out.println(response.readEntity(String.class));

                float prix = Float.parseFloat(prixProduits);
                String image = "file:/assets/img/" + nomProduits;

                JSONObject jo = new JSONObject();
                jo.put("nomProduit", nomProduits);
                jo.put("prixProduit", prix);
                jo.put("imageProduit", image);
                Response larep = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
                System.out.println("Form response " + larep.getStatus());

                App.setRoot("primary");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void produits(Client client) {

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                addProduits(e, nomProduits.getText(), prixProduits.getText(), img2, importCategorie.getSelectionModel().getSelectedItem().toString());
            }
        };

        addImages.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        EventHandler<MouseEvent> eventHandler1 = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                importImages(e, importImage);
            }
        };

        importImage.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler1);

        importCategorie.setItems(FXCollections.observableArrayList("Premier", "Deuxieme", "Troisieme"));

        int compteur = 0;

        WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/categorie");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();

        JSONArray jsonArray = new JSONArray(response.readEntity(String.class));

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            int idCategorie = Integer.parseInt((String) json.get("idCategorie"));
            String nomCategorie = (String) json.get("nomCategorie");

            switch (idCategorie) {
                case 1:
                    label1.setText(nomCategorie);
                    break;

                case 2:
                    label2.setText(nomCategorie);
                    break;

                case 3:
                    label3.setText(nomCategorie);
                    break;

                default:
                    break;
            }
        }

        WebTarget webTarget2 = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder2.header("some-header", "true");
        Response response2 = invocationBuilder2.get();


        JSONArray jsonArray2 = new JSONArray(response2.readEntity(String.class));

        for (int i = 0; i < jsonArray2.length(); i++) {
            JSONObject json = new JSONObject(jsonArray2.get(i).toString());
            String nomProduit = (String) json.get("nomProduit");
            int prixProduit = Integer.parseInt((String) json.get("prixProduit"));
            String img = (String) json.get("imageProduit");
            int idCategorie = Integer.parseInt((String) json.get("idCategorie"));
            Label nameProduits = new Label(nomProduit);
            Label prixProduits = new Label(""+prixProduit+"");

            Image imageAfter = new Image(getClass().getResourceAsStream(img.replace("file:", "")));
            ImageView image = new ImageView(imageAfter);
            image.setFitHeight(50);
            image.setFitWidth(50);

            switch (idCategorie) {
                case 1:
                    VBox vboxPizza = new VBox();
                    vboxPizza.getChildren().add(nameProduits);
                    vboxPizza.getChildren().add(prixProduits);
                    vboxPizza.getChildren().add(image);
                    produitsGrid.add(vboxPizza, 0, compteur);
                    break;

                case 2:
                    VBox vboxBoissons = new VBox();
                    vboxBoissons.getChildren().add(nameProduits);
                    vboxBoissons.getChildren().add(prixProduits);
                    vboxBoissons.getChildren().add(image);
                    produitsGrid.add(vboxBoissons, 1, compteur);
                    break;

                case 3:
                    VBox vboxDesserts = new VBox();
                    vboxDesserts.getChildren().add(nameProduits);
                    vboxDesserts.getChildren().add(prixProduits);
                    vboxDesserts.getChildren().add(image);
                    produitsGrid.add(vboxDesserts, 2, compteur);
                    break;

                default:
                    break;
            }

            compteur++;

        }
    }

}
