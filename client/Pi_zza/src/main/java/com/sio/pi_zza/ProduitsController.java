package com.sio.pi_zza;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.HBox;
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
import java.util.Objects;

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
    private String img3 = "";

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
                if(Objects.equals(categorieProduits, "Pizza")) {
                    categorieProduits = "1";
                } else if(Objects.equals(categorieProduits, "Boissons")) {
                    categorieProduits = "2";
                } else {
                    categorieProduits = "3";
                }

                Client client = ClientBuilder.newClient();
                WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

                String image = "file:images/" + nomProduits.replace(" ", "_") + ".png";

                JSONObject jo = new JSONObject();
                jo.put("nomProduit", nomProduits);
                jo.put("prixProduit", prixProduits);
                jo.put("imageProduit", image);
                jo.put("idCategorie", categorieProduits);
                Response larep = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
                System.out.println("Form response " + larep.getStatus());

                Path copied = Paths.get("images/"+nomProduits.replace(" ", "_")+".png");
                Path originalPath = Paths.get(img2.replace("file:/", ""));

                Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

                img2 = "";
                App.setRoot("primary");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void modifProduit(MouseEvent event, String idProduit, String nomProduit, float prixProduit, String imageProduit, int categorieProduit) {

        img3 = imageProduit;
        img2 = imageProduit;

        String categorieName = "";

        if(categorieProduit == 1) {
            categorieName = "Pizza";
        } else if(categorieProduit == 2) {
            categorieName = "Boissons";
        } else {
            categorieName = "Desserts";
        }

        Stage newStage = new Stage();

        VBox box = new VBox();

        TextField nomProduitText = new TextField(nomProduit);
        TextField prixProduitText = new TextField(""+prixProduit);
        ComboBox categorieProduitText = new ComboBox();
        categorieProduitText.setItems(FXCollections.observableArrayList("Pizza", "Boissons", "Desserts"));
        categorieProduitText.getSelectionModel().select(categorieProduit-1);

        HBox huBox = new HBox();
        Label nom = new Label("Nom du produit:");
        box.getChildren().add(nom);
        box.getChildren().add(nomProduitText);

        Label prix = new Label("Prix du produit:");
        box.getChildren().add(prix);
        box.getChildren().add(prixProduitText);

        Label imageSerieT = new Label("Image du produit:");
        ImageView imageSerie = new ImageView(""+imageProduit);
        imageSerie.setFitHeight(100);
        imageSerie.setFitWidth(100);
        box.getChildren().add(imageSerieT);
        box.getChildren().add(imageSerie);
        Button addImage = new Button("Modifier l'image");

        EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                importImages(e, addImage);
            }
        };

        addImage.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);

        box.getChildren().add(addImage);

        Label categorieT = new Label("Categorie du produit:");
        box.getChildren().add(categorieT);
        box.getChildren().add(categorieProduitText);

        Button modif = new Button("Modifier");
        Button retour = new Button("Retour");

        box.getChildren().add(huBox);

        huBox.getChildren().add(modif);
        huBox.getChildren().add(retour);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                clickModif(e, newStage, nomProduit, nomProduitText.getText(), prixProduitText.getText(), img2, categorieProduitText.getSelectionModel().getSelectedItem().toString(), idProduit);
            }
        };

        modif.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        retour.setOnAction(actionEvent -> newStage.hide());

        Scene stageScene = new Scene(box, 500, 550);
        newStage.setScene(stageScene);
        newStage.show();

    }

    public void clickModif(MouseEvent event, Stage newStage, String namePr, String nomProduit, String prixProduit, String imageProduit, String categorieProduit, String idProduit) {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        String categorieId = "";

        if(categorieProduit == "Pizza") {
            categorieId = "1";
        } else if(categorieProduit == "Boissons") {
            categorieId = "2";
        } else {
            categorieId = "3";
        }

        System.out.println("Id Produit : "+idProduit+"\nNom Produit : "+nomProduit+"\nPrix Produit : "+prixProduit+"\nImage Produit : "+imageProduit+"\nCategorie Produit : "+categorieId);

        String image = "file:images/" + nomProduit.replace(" ", "_") + ".png";

        JSONObject update = new JSONObject();
        update.put("idProduit", idProduit);
        update.put("nomProduit", nomProduit);
        update.put("prixProduit",prixProduit);
        update.put("imageProduit",image);
        update.put("idCategorie",categorieId);

        Response larep=webTarget.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(update.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
        System.out.println("Form response " + larep.getStatus());

        System.out.println("Modification réussi!");

        newStage.hide();

        try {
            if(!img2.equals(img3)) {
                if(!namePr.equals(nomProduit)) {
                    Path pathDelete = Paths.get("images/"+namePr.replace(" ", "_")+".png");
                    Files.delete(pathDelete);

                    Path copied = Paths.get("images/" + nomProduit.replace(" ", "_") + ".png");
                    Path originalPath = Paths.get(img2.replace("file:/", ""));

                    Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

                    img2 = "";
                    App.setRoot("primary");
                } else {
                    Path copied = Paths.get("images/" + nomProduit.replace(" ", "_") + ".png");
                    Path originalPath = Paths.get(img2.replace("file:/", ""));

                    Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

                    img2 = "";
                    App.setRoot("primary");
                }
            } else {
                if(!namePr.equals(nomProduit)) {
                    Path originalPath = Paths.get("images/"+namePr.replace(" ", "_")+".png");
                    Path copied = Paths.get("images/"+nomProduit.replace(" ", "_")+".png");

                    Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

                    Path pathDelete = Paths.get("images/"+namePr.replace(" ", "_")+".png");
                    Files.delete(pathDelete);

                    img2 = "";
                }

                App.setRoot("primary");

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void clickDelete(MouseEvent event, Stage newStage, String idProduit, String nomProduit) {
        try {
            Path pathDelete = Paths.get("images/"+nomProduit.replace(" ", "_")+".png");
            Files.delete(pathDelete);

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

            Invocation.Builder invocationBuilder
                    = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
            invocationBuilder.header("some-header", "true");
            Response response = invocationBuilder.get();

            WebTarget parametreDel = webTarget.path("/"+idProduit);
            Invocation.Builder delete
                    = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
            invocationBuilder.header("some-header", "true");
            Response deletePost = delete.delete();

            newStage.hide();
            App.setRoot("primary");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void suprProduit(MouseEvent event, String idProduit, String nomProduit) {

        Stage newStage = new Stage();
        VBox box = new VBox();
        HBox bo = new HBox();
        Label title = new Label("Vous êtes sur de vouloir suprimmé ?");
        Button yes = new Button("Oui");
        Button no = new Button("Non");

        title.getStyleClass().add("suprLabel");
        yes.getStyleClass().add("but");
        no.getStyleClass().add("but");
        bo.getStyleClass().add("boBOX");
        box.getStyleClass().add("boxSupr");

        box.getChildren().add(title);
        box.getChildren().add(bo);
        bo.getChildren().add(yes);
        bo.getChildren().add(no);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                clickDelete(e, newStage, nomProduit, idProduit);
            }
        };

        yes.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        no.setOnAction(actionEvent -> newStage.hide());

        Scene stageScene = new Scene(box, 500, 200);
        newStage.setScene(stageScene);
        newStage.show();

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

        int compteur = 0;
        String nomCategorie1="";
        String nomCategorie2="";
        String nomCategorie3="";

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
                    nomCategorie1 = nomCategorie;
                    break;

                case 2:
                    label2.setText(nomCategorie);
                    nomCategorie2 = nomCategorie;
                    break;

                case 3:
                    label3.setText(nomCategorie);
                    nomCategorie3 = nomCategorie;
                    break;

                default:
                    break;
            }
        }

        importCategorie.setItems(FXCollections.observableArrayList(nomCategorie1, nomCategorie2, nomCategorie3));

        WebTarget webTarget2 = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder2.header("some-header", "true");
        Response response2 = invocationBuilder2.get();


        JSONArray jsonArray2 = new JSONArray(response2.readEntity(String.class));

        for (int i = 0; i < jsonArray2.length(); i++) {
            JSONObject json = new JSONObject(jsonArray2.get(i).toString());
            String idProduit = (String) json.get("idProduit");
            String nomProduit = (String) json.get("nomProduit");
            float prixProduit = Float.parseFloat((String) json.get("prixProduit"));
            String img = (String) json.get("imageProduit");
            int idCategorie = Integer.parseInt((String) json.get("idCategorie"));
            Label nameProduits = new Label(nomProduit);
            Label prixProduits = new Label(""+prixProduit+"");

            Button buttonModif = new Button("Modifier");
            Button buttonSupr = new Button("Supprimer");

            ImageView image = new ImageView(img.replace("file:/", ""));
            image.setFitHeight(50);
            image.setFitWidth(50);

            switch (idCategorie) {
                case 1:
                    VBox vboxPizza = new VBox();
                    vboxPizza.getChildren().add(nameProduits);
                    vboxPizza.getChildren().add(prixProduits);
                    vboxPizza.getChildren().add(image);
                    HBox hbox = new HBox();
                    hbox.getChildren().add(buttonModif);
                    hbox.getChildren().add(buttonSupr);

                    EventHandler<MouseEvent> eventModif1 = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent e)
                        {
                            modifProduit(e, idProduit, nomProduit, prixProduit, img.replace("file:/", ""), idCategorie);
                        }
                    };
                    buttonModif.addEventHandler(MouseEvent.MOUSE_CLICKED, eventModif1);

                    EventHandler<MouseEvent> eventSupr1 = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent e)
                        {
                            suprProduit(e, nomProduit, idProduit);
                        }
                    };
                    buttonSupr.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSupr1);

                    vboxPizza.getChildren().add(hbox);
                    produitsGrid.add(vboxPizza, 0, compteur);
                    break;

                case 2:
                    VBox vboxBoissons = new VBox();
                    vboxBoissons.getChildren().add(nameProduits);
                    vboxBoissons.getChildren().add(prixProduits);
                    vboxBoissons.getChildren().add(image);

                    HBox hbox2 = new HBox();
                    hbox2.getChildren().add(buttonModif);
                    hbox2.getChildren().add(buttonSupr);

                    EventHandler<MouseEvent> eventModif2 = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent e)
                        {
                            modifProduit(e, idProduit, nomProduit, prixProduit, img.replace("file:/", ""), idCategorie);
                        }
                    };
                    buttonModif.addEventHandler(MouseEvent.MOUSE_CLICKED, eventModif2);

                    EventHandler<MouseEvent> eventSupr2 = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent e)
                        {
                            suprProduit(e, nomProduit, idProduit);
                        }
                    };
                    buttonSupr.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSupr2);

                    vboxBoissons.getChildren().add(hbox2);

                    produitsGrid.add(vboxBoissons, 1, compteur);
                    break;

                case 3:
                    VBox vboxDesserts = new VBox();
                    vboxDesserts.getChildren().add(nameProduits);
                    vboxDesserts.getChildren().add(prixProduits);
                    vboxDesserts.getChildren().add(image);

                    HBox hbox3 = new HBox();
                    hbox3.getChildren().add(buttonModif);
                    hbox3.getChildren().add(buttonSupr);

                    EventHandler<MouseEvent> eventModif3 = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent e)
                        {
                            modifProduit(e, idProduit, nomProduit, prixProduit, img.replace("file:/", ""), idCategorie);
                        }
                    };
                    buttonModif.addEventHandler(MouseEvent.MOUSE_CLICKED, eventModif3);

                    EventHandler<MouseEvent> eventSupr3 = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent e)
                        {
                            suprProduit(e, nomProduit, idProduit);
                        }
                    };
                    buttonSupr.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSupr3);

                    vboxDesserts.getChildren().add(hbox3);

                    produitsGrid.add(vboxDesserts, 2, compteur);
                    break;

                default:
                    break;
            }

            compteur++;

        }
    }

}
