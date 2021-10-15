package com.sio.pi_zza;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class PrimaryController implements Initializable {

    private String inputImage = "";

    @FXML
    private Button dashboard;

    @FXML
    private Button produits;

    @FXML
    private Button historique;

    @FXML
    private ImageView close;

    @FXML
    private AnchorPane boxAll;

    @FXML
    private GridPane boxDashboard;

    @FXML
    private GridPane boxProduits;

    @FXML
    private GridPane boxHistorique;

    @FXML
    private Label labs;

    private void clickOnAddProduit() {
        /*
        if(inputNom.getText() == null || inputPrix.getText() == null || inputTaille.getText() == null || inputCatégorie.getText() == null || inputImage == null) {
            System.out.println("Veuillez remplir toute les cases");
        }

        // Requete vers l'URL
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/WebserviceTD/server/carte");

        // Méthode GET
        Invocation.Builder invocationBuilder
                = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();
        System.out.println(response.readEntity(String.class));

        // Methode POST
        JSONObject jo = new JSONObject();
        jo.put("nomCarte", inputNom.getText());
        jo.put("prix",inputPrix.getText());
        jo.put("idMarque",inputTaille.getText());
        Response larep=webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
        System.out.println("Form response " + larep.getStatus());
        inputImage = "";*/

    }

    private void inputAddImageProduit() {
            /*
        Stage newStage = new Stage();

        FileChooser fileChooser2 = new FileChooser();
        fileChooser2.setTitle("Open Resource File");
        fileChooser2.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser2.showOpenDialog(newStage);

        inputImage = selectedFile.toURI().toString();
        */


    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if(event.getSource() == dashboard) {
            dashboardWindow();
        }

        if(event.getSource() == produits) {
            produitsWindow();
        }

        if(event.getSource() == historique) {
            historiqueWindow();
        }

    }

    @FXML
    private void closeButton(MouseEvent event) {
        App.close();
    }

    public void startController() {

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton(e);
            }
        };
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void dashboardWindow() {
        boxDashboard.setVisible(true);
        boxProduits.setVisible(false);
        boxHistorique.setVisible(false);
    }

    public void produitsWindow() {
        boxDashboard.setVisible(false);
        boxProduits.setVisible(true);
        boxHistorique.setVisible(false);
    }

    public void historiqueWindow() {
        boxDashboard.setVisible(false);
        boxProduits.setVisible(false);
        boxHistorique.setVisible(true);

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/historique");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();

        JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            String nomProduit = (String) json.get("nomProduit");
            int quantite = Integer.parseInt((String) json.get("quantite"));
            String date = (String) json.get("dateCommande");
            int numeroTable = Integer.parseInt((String) json.get("numeroTables"));

            Label lab = new Label("" + nomProduit + " et " + quantite + " et " + date + " et " + numeroTable +"");

            boxDashboard.add(lab,1,1);

            System.out.println("f");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startController();
        dashboardWindow();

        /*
        // Requete vers l'URL
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/WebserviceTD/server/carte");

        // Méthode GET
        Invocation.Builder invocationBuilder
                = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();
        System.out.println(response.readEntity(String.class));

        // Méthode GET avec un paramètre : on rajoute notre paramètre dans l'url
        WebTarget parametre = webTarget.path("/3");
        Invocation.Builder requeteParam
                = parametre.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response reponse2 = requeteParam.get();
        System.out.println(reponse2.readEntity(String.class));

        // Méthode DELETE Pareil qu'avant mais on met .delete() a la place de .get()
        WebTarget parametreDel = webTarget.path("/5");
        Invocation.Builder delete
                = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response deletePost = delete.delete();
        System.out.println(deletePost.readEntity(String.class));


        // Méthode POST

        JSONObject jo = new JSONObject();
        jo.put("idCarte", "1");
        jo.put("nomCarte", "La max corporation");
        jo.put("prix","300");
        jo.put("idMarque","1");
        Response larep=webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
        System.out.println("Form response " + larep.getStatus());

        // Méthode PUT
        JSONObject update = new JSONObject();
        jo.put("idCarte", "12");
        jo.put("nomCarte", "La maxmx corporation");
        jo.put("prix","300");
        jo.put("idMarque","1");
        Response larep2=webTarget.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
        System.out.println("Form response " + larep.getStatus());*/

    }
}
