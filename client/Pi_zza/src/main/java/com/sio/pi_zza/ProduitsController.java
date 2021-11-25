package com.sio.pi_zza;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import com.sio.pi_zza.DAO.produitDAO;
import com.sio.pi_zza.DAO.categorieDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ResourceBundle;

public class ProduitsController extends DashboardController implements Initializable {

    @FXML private Button dashboardButton;
    @FXML private Button produitsButton;
    @FXML private Button historiqueButton;
    @FXML private Button addProducts;
    @FXML private ImageView closeImg;

    @FXML private HBox produitsHBox;
    @FXML private GridPane categorieGrid;

    private ScrollPane scrollProduit;
    private GridPane gridProduit;
    private String img2 = "";
    private String img3 = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };

        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        JSONArray jsonCategorie = categorieDAO.getCategorie();

        for (int i = 0; i < jsonCategorie.length(); i++) {
            int compteur = 0;

            JSONObject jsonObjectCommande = new JSONObject(jsonCategorie.get(i).toString());
            int idCategorie = Integer.parseInt((String) jsonObjectCommande.get("idCategorie"));
            String nomCategorie = (String) jsonObjectCommande.get("nomCategorie");

            HBox hbCate = new HBox();
            Label CategorieName = new Label(nomCategorie);
            hbCate.getChildren().add(CategorieName);
            hbCate.setAlignment(Pos.CENTER);
            CategorieName.getStyleClass().add("titleBar");
            categorieGrid.add(hbCate, i, 1);

            JSONArray jsonProduit = produitDAO.getProduitByIdCategorie(idCategorie);

            scrollProduit = new ScrollPane();
            scrollProduit.getStyleClass().add("scrollPane");
            scrollProduit.setPrefSize(1000,1000);
            gridProduit = new GridPane();

            for (int j = 0; j < jsonProduit.length(); j++) {

                JSONObject jsonObjectProduit = new JSONObject(jsonProduit.get(j).toString());
                int idProduit = Integer.parseInt((String) jsonObjectProduit.get("idProduit"));
                String nomProduit = (String) jsonObjectProduit.get("nomProduit");
                float prixProduit = Float.parseFloat((String) jsonObjectProduit.get("prixProduit"));
                String img = (String) jsonObjectProduit.get("imageProduit");

                VBox vboxProduit = new VBox();
                vboxProduit.setPrefWidth(236);
                vboxProduit.setAlignment(Pos.CENTER);
                vboxProduit.getStyleClass().add("boxProduit");
                Label Produits = new Label(nomProduit + "    " + prixProduit + "€");
                ImageView image = new ImageView("file:"+img);
                image.setFitHeight(80);
                image.setFitWidth(100);
                image.getStyleClass().add("imgForm");

                if(nomProduit.length() <= 15) {
                    Produits.getStyleClass().add("textSlideBar");
                } else {
                    Produits.getStyleClass().add("textProduitLittle");
                }

                Button buttonModif = new Button("Modifier");
                Button buttonSupr = new Button("Supprimer");

                buttonModif.getStyleClass().add("buttonProduit");
                buttonSupr.getStyleClass().add("buttonProduit");

                vboxProduit.getChildren().add(Produits);
                vboxProduit.getChildren().add(image);

                HBox hbox = new HBox();
                hbox.setSpacing(5);
                hbox.getStyleClass().add("boxProduit");
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().add(buttonModif);
                hbox.getChildren().add(buttonSupr);
                vboxProduit.getChildren().add(hbox);

                gridProduit.add(vboxProduit, 0, compteur);

                EventHandler<MouseEvent> eventModif = new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent e)
                    {
                        modifProduit(idProduit, nomProduit, prixProduit, img, idCategorie);
                    }
                };
                buttonModif.addEventHandler(MouseEvent.MOUSE_CLICKED, eventModif);

                EventHandler<MouseEvent> eventSupr = new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent e)
                    {
                        suprProduit(idProduit, nomProduit);
                    }
                };
                buttonSupr.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSupr);

                compteur++;
            }

            scrollProduit.setContent(gridProduit);
            produitsHBox.getChildren().add(scrollProduit);
        }
    }

    public void importImages(Button importImage) {
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

    public void modifProduit(int idProduit, String nomProduit, float prixProduit, String imageProduit, int categorieProduit) {

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
        newStage.initStyle(StageStyle.UNDECORATED);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add(String.valueOf(getClass().getResource("/assets/css/StylePrimary.css")));
        box.getStyleClass().add("infoBox");

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
        ImageView imageSerie = new ImageView("file:"+imageProduit);
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
                importImages(addImage);
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
                clickModif(newStage, nomProduit, nomProduitText.getText(), prixProduitText.getText(), img2, categorieProduitText.getSelectionModel().getSelectedItem().toString(), idProduit);
            }
        };

        modif.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        retour.setOnAction(actionEvent -> newStage.hide());

        Scene stageScene = new Scene(box, 500, 550);
        newStage.setScene(stageScene);
        newStage.show();

    }

    public void clickModif(Stage newStage, String namePr, String nomProduit, String prixProduit, String imageProduit, String categorieProduit, int idProduit) {
        int categorieId = 0;

        if(categorieProduit == "Pizza") {
            categorieId = 1;
        } else if(categorieProduit == "Boissons") {
            categorieId = 2;
        } else {
            categorieId = 3;
        }

        String image = "file:images/" + nomProduit.replace(" ", "_") + ".png";

        JSONObject update = new JSONObject();
        update.put("idProduit", idProduit);
        update.put("nomProduit", nomProduit);
        update.put("prixProduit",prixProduit);
        update.put("imageProduit",image);
        update.put("idCategorie",categorieId);

        produitDAO.updateProduitById(idProduit, nomProduit, Float.parseFloat(prixProduit), image, categorieId);

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

    public void suprProduit(int idProduit, String nomProduit) {
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.UNDECORATED);

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add(String.valueOf(getClass().getResource("/assets/css/StylePrimary.css")));

        HBox bo = new HBox();
        Label title = new Label("Vous êtes sur de vouloir suprimmé ?");
        Button yes = new Button("Oui");
        Button no = new Button("Non");

        box.getStyleClass().add("boxNo");
        title.getStyleClass().add("titleBarProduitSupr");
        yes.getStyleClass().add("buttonProduitSupr");
        no.getStyleClass().add("buttonProduitSupr");

        bo.setSpacing(5);
        bo.getStyleClass().add("boxProduitSupr");
        bo.setAlignment(Pos.CENTER);

        box.getChildren().add(title);
        box.getChildren().add(bo);
        bo.getChildren().add(yes);
        bo.getChildren().add(no);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                clickDelete(newStage, idProduit, nomProduit);
            }
        };

        yes.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        no.setOnAction(actionEvent -> newStage.hide());

        Scene stageScene = new Scene(box, 500, 200);
        newStage.setScene(stageScene);
        newStage.show();

    }

    public void clickDelete(Stage newStage, int idProduit, String nomProduit) {
        try {
            Path pathDelete = Paths.get("images/"+nomProduit.replace(" ", "_")+".png");
            Files.delete(pathDelete);

            produitDAO.deleteProduitById(idProduit);

            newStage.hide();
            App.setRoot("produit");

        } catch (IOException e) {
            e.printStackTrace();
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


}
