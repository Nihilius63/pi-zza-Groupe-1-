package com.sio.pi_zza;

import com.sio.pi_zza.DAO.*;
import com.sio.pi_zza.DTO.tableInfoDTO;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class InfoSuplTableController extends DashboardController implements Initializable {

    @FXML private ImageView imgReloadSync;
    @FXML private ImageView closeImg;
    @FXML public Label numeroTable;
    @FXML private Label nombrePlaceOcu;
    @FXML private Label nombreCommandeEff;
    @FXML private Label sommeCommandes;
    @FXML private Button tableInnocupe;
    @FXML private Button retour;
    @FXML private ScrollPane scrollCommande;
    @FXML private ImageView imgTables;

    private VBox bloc;
    private Image imgTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };
        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        EventHandler<MouseEvent> eventReturn = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    App.setRoot("dashboard");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        retour.addEventHandler(MouseEvent.MOUSE_CLICKED, eventReturn);

        int idTables = tableInfoDTO.idTable;
        int nbPersonne = tableInfoDTO.nbPersonne;
        int nbPlaces = tableInfoDTO.nbPlace;

        float sommeCommande = 0;
        int totalCommande = 0;

        JSONArray jsonCommande = commandeDAO.getCommandeByIdTables(idTables);

        totalCommande = jsonCommande.length();

        if(nbPlaces <= 2) {
            imgTable = new Image("file:imgTools/table1.png");
        } else if(nbPlaces <=6) {
            imgTable = new Image("file:imgTools/table2.png");
        } else {
            imgTable = new Image("file:imgTools/table3.png");
        }

        imgTables.setImage(imgTable);

        if(totalCommande != 0) {
            for (int i = 0; i < jsonCommande.length(); i++) {
                JSONObject json1 = new JSONObject(jsonCommande.get(i).toString());
                int idCommande = Integer.parseInt((String) json1.get("idCommande"));
                int idTable = Integer.parseInt((String) json1.get("idTables"));

                bloc = new VBox();
                bloc.setPrefWidth(311);
                bloc.maxWidth(311);
                bloc.setAlignment(Pos.TOP_CENTER);
                bloc.getStyleClass().add("boxListeCommande");
                Label commandeTextId = new Label("Commande numéro: " + idCommande);
                commandeTextId.setAlignment(Pos.CENTER);
                commandeTextId.getStyleClass().add("textInfoSupl");
                Button suprCommande = new Button("Supprimer la commande");
                suprCommande.getStyleClass().add("buttonProduit");
                bloc.getChildren().add(commandeTextId);
                bloc.getChildren().add(suprCommande);
                Label point = new Label("-----------------------------");
                point.getStyleClass().add("textInfoSupl");
                bloc.getChildren().add(point);

                EventHandler<MouseEvent> eventSuprCommande = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        suprCommande(idCommande, idTables);
                    }
                };
                suprCommande.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSuprCommande);

                JSONArray jsonContient = contientDAO.getContientByIdCommande(idCommande);

                if(jsonContient == null) {
                    System.out.println("test");
                } else {
                    System.out.println("OUI");
                }

                if(jsonContient.length() != 0) {
                    for (int j = 0; j < jsonContient.length(); j++) {
                        JSONObject json2 = new JSONObject(jsonContient.get(j).toString());
                        int idCommande2 = Integer.parseInt((String) json2.get("idCommande"));
                        int idProduit = Integer.parseInt((String) json2.get("idProduit"));
                        int quantite = Integer.parseInt((String) json2.get("quantite"));

                        JSONObject jsonProduit = produitDAO.getProduitById(idProduit);

                        int produitId = Integer.parseInt((String) jsonProduit.get("idProduit"));
                        String nomProduit = (String) jsonProduit.get("nomProduit");
                        float prixProduit = Float.parseFloat((String) jsonProduit.get("prixProduit"));
                        String imageProduit = (String) jsonProduit.get("imageProduit");
                        int idCategorie = Integer.parseInt((String) jsonProduit.get("idCategorie"));

                        sommeCommande += prixProduit * quantite;

                        Label nomProduiText = new Label(nomProduit + " / " + prixProduit + "€");
                        nomProduiText.setAlignment(Pos.CENTER);
                        Button suprProduct = new Button("Supprimer le produit");
                        if (nomProduit.length() <= 15) {
                            nomProduiText.getStyleClass().add("textInfoSupl");
                        } else {
                            nomProduiText.getStyleClass().add("textInfoSuplLittel");
                        }
                        suprProduct.getStyleClass().add("buttonProduit");
                        bloc.getChildren().add(nomProduiText);
                        HBox hquantite = new HBox();
                        hquantite.setAlignment(Pos.CENTER);
                        Image moins = new Image("file:imgTools/moins.png");
                        ImageView moinsView = new ImageView(moins);
                        moinsView.setFitHeight(32);
                        moinsView.setFitWidth(32);
                        Image plus = new Image("file:imgTools/plus.png");
                        Label quantiteText = new Label("" + quantite);
                        quantiteText.getStyleClass().add("textInfoSupl");
                        ImageView plusView = new ImageView(plus);
                        plusView.setFitHeight(32);
                        plusView.setFitWidth(32);
                        hquantite.getChildren().add(moinsView);
                        hquantite.getChildren().add(quantiteText);
                        hquantite.getChildren().add(plusView);

                        EventHandler<MouseEvent> eventMoinsProduct = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                moinsProduct(idCommande, produitId, quantite);
                            }
                        };
                        moinsView.addEventHandler(MouseEvent.MOUSE_CLICKED, eventMoinsProduct);

                        EventHandler<MouseEvent> eventPlusProduct = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                plusProduct(idCommande, produitId, quantite);
                            }
                        };
                        plusView.addEventHandler(MouseEvent.MOUSE_CLICKED, eventPlusProduct);

                        bloc.getChildren().add(hquantite);
                        bloc.getChildren().add(suprProduct);

                        EventHandler<MouseEvent> eventSuprProduct = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                suprProduitCommande(idCommande, produitId, idTables);
                            }
                        };
                        suprProduct.addEventHandler(MouseEvent.MOUSE_CLICKED, eventSuprProduct);
                    }
                } else {
                    bloc = new VBox();
                    bloc.setAlignment(Pos.TOP_CENTER);
                    bloc.getStyleClass().add("boxListeCommande");
                    Label ttile = new Label(" Aucun produit n'a encore était ajouté! ");
                    ttile.setAlignment(Pos.CENTER);
                    ttile.getStyleClass().add("textInfoSuplLittel");
                    bloc.getChildren().add(ttile);
                    scrollCommande.setContent(bloc);
                    scrollCommande.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                }
            }
            scrollCommande.setContent(bloc);
            scrollCommande.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        } else {
            bloc = new VBox();
            bloc.setAlignment(Pos.TOP_CENTER);
            bloc.getStyleClass().add("boxListeCommande");
            Label ttile = new Label(" Aucune commande n'a été effectué ! ");
            ttile.setAlignment(Pos.CENTER);
            ttile.getStyleClass().add("textInfoSuplLittel");
            bloc.getChildren().add(ttile);
            scrollCommande.setContent(bloc);
            scrollCommande.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        }

        numeroTable.setText("Numéro de la table: " + idTables);
        nombrePlaceOcu.setText("Nombre de place occupé: " + nbPersonne + " / " + nbPlaces);
        nombreCommandeEff.setText("Nombre de commande effectué: " + totalCommande);
        sommeCommandes.setText("Somme des commandes: " + sommeCommande + "€");

        EventHandler<MouseEvent> eventClickInnocupe = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                clickInnocupe(idTables);
            }
        };

        tableInnocupe.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickInnocupe);
    }

    public void suprCommande(int idCommande, int idTables) {
        commandeDAO.deleteByIdCommande(idCommande);

        Stage suprCommandeStage = new Stage();
        suprCommandeStage.initStyle(StageStyle.UNDECORATED);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add(String.valueOf(getClass().getResource("/assets/css/StylePrimary.css")));
        box.getStyleClass().add("boxNo");
        Label msgView = new Label("Votre commmande a était supprimé avec succès!");
        msgView.getStyleClass().add("titleBar");
        box.getChildren().add(msgView);
        Scene suprCommandeScene = new Scene(box, 740, 110);
        suprCommandeStage.setScene(suprCommandeScene);
        suprCommandeStage.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(time -> {
            try {
                suprCommandeStage.hide();
                App.setRoot("infoCommande");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
        pause.play();
    }

    public void suprProduitCommande(int idCommande, int idProduit, int idTables) {
        if(contientDAO.getContientByIdCommande(idCommande).length() == 1) {
            commandeDAO.deleteByIdCommande(idCommande);
        } else {
            contientDAO.deleteContientByIdCommandeProduit(idCommande, idProduit);
        }

        Stage suprProduitCommande = new Stage();
        suprProduitCommande.initStyle(StageStyle.UNDECORATED);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add(String.valueOf(getClass().getResource("/assets/css/StylePrimary.css")));
        box.getStyleClass().add("boxNo");
        Label msgViews = new Label("Votre produit a était supprimé avec succès!");
        msgViews.getStyleClass().add("titleBar");
        box.getChildren().add(msgViews);
        Scene suprCommandeScene = new Scene(box, 690, 110);
        suprProduitCommande.setScene(suprCommandeScene);
        suprProduitCommande.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(time -> {
                    try {
                        suprProduitCommande.hide();
                        App.setRoot("infoCommande");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        pause.play();
    }

    public void clickInnocupe(int idTables) {
        tablesDAO.inoccupeTable(idTables);

        JSONArray jsonCommande = commandeDAO.getCommandeByIdTables(idTables);

        for (int j = 0; j < jsonCommande.length(); j++) {
            JSONObject jsoCommande = new JSONObject(jsonCommande.get(j).toString());
            int idCommande = Integer.parseInt((String) jsoCommande.get("idCommande"));

            JSONArray jsonContient = contientDAO.getContientByIdCommande(idCommande);

            for (int i = 0; i < jsonContient.length(); i++) {
                JSONObject jsoContient = new JSONObject(jsonContient.get(i).toString());
                int idProduit = Integer.parseInt((String) jsoContient.get("idProduit"));
                int quantite = Integer.parseInt((String) jsoContient.get("quantite"));

                JSONObject jsonProduit = produitDAO.getProduitById(idProduit);
                String nomProduit = (String) jsonProduit.get("nomProduit");
                float prixProduit = Float.parseFloat((String) jsonProduit.get("prixProduit"));

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();

                historiqueDAO.createHistory(nomProduit, quantite, prixProduit, dateFormat.format(date), idTables);
                contientDAO.deleteContientByIdCommande(idCommande);
                commandeDAO.deleteByIdCommande(idCommande);
            }
        }

        Stage innocupeTable = new Stage();
        innocupeTable.initStyle(StageStyle.UNDECORATED);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add(String.valueOf(getClass().getResource("/assets/css/StylePrimary.css")));
        box.getStyleClass().add("boxNo");
        Label msgViews = new Label("Votre table est désormais innocupée!");
        msgViews.getStyleClass().add("titleBar");
        box.getChildren().add(msgViews);
        Scene suprCommandeScene = new Scene(box, 690, 110);
        innocupeTable.setScene(suprCommandeScene);
        innocupeTable.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(time -> {
                    try {
                        innocupeTable.hide();
                        App.setRoot("dashboard");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        pause.play();

    }

    public void moinsProduct(int idCommande, int produitId, int quantite) {
        try {
            contientDAO.updateContientByIdCommandeAndIdProduit(idCommande, produitId, quantite-1);
            JSONObject objectProduct = contientDAO.getContientByIdCommandeProduit(idCommande, produitId);
            int quantity = Integer.parseInt((String) objectProduct.get("quantite"));
            if(quantity == 0) {
                contientDAO.deleteContientByIdCommandeProduit(idCommande, produitId);
                App.setRoot("infoCommande");
            } else {
                App.setRoot("infoCommande");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void plusProduct(int idCommande, int produitId, int quantite) {
        try {
            contientDAO.updateContientByIdCommandeAndIdProduit(idCommande, produitId, quantite+1);
            App.setRoot("infoCommande");
        } catch (IOException e) {
            e.printStackTrace();
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
        App.setRoot("infoCommande");
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
