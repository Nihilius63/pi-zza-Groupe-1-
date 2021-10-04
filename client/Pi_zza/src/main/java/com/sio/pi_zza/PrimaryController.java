package com.sio.pi_zza;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.json.JSONObject;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
    
    private String inputImage = "";

    @FXML
    private ImageView close;

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
    private void closeButton() {
        App.close();
    }

    public void startController() {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton();
            }
        };
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startController();

        /*
        ImageView closeButton = new ImageView("https://icon-library.com/images/close-icon-png/close-icon-png-19.jpg");
        closeButton.setFitHeight(250);
        closeButton.setFitWidth(250);
        
        closeButton.setTranslateX(10);
        closeButton.setTranslateY(10);
        
        vbox.getChildren().add(closeButton);
        */
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
