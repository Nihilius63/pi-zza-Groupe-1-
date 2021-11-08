package com.sio.pi_zza;

import java.net.URL;
import java.util.ResourceBundle;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.json.JSONObject;


public class PrimaryController extends ProduitsController implements Initializable{

    Client client = ClientBuilder.newClient();

    @FXML private Button dashboardButton;
    @FXML private Button produitsButton;
    @FXML private Button historiqueButton;

    @FXML private ImageView close;

    @FXML private AnchorPane boxAll;

    @FXML private GridPane boxDashboard;
    @FXML private GridPane boxProduits;
    @FXML private GridPane boxHistorique;
    @FXML private VBox vboxAdd;

    @FXML
    private void handleClicks(ActionEvent event) {
        if(event.getSource() == dashboardButton) {
            dashboardWindow();
        }

        if(event.getSource() == produitsButton) {
            produitsWindow();
        }

        if(event.getSource() == historiqueButton) {
            historiqueWindow();
        }

    }

    @FXML
    private void closeButton(MouseEvent event) {
        App.close();
    }

    public void dashboardWindow() {
        vboxAdd.setVisible(false);
        boxDashboard.setVisible(true);
        boxProduits.setVisible(false);
        boxHistorique.setVisible(false);
    }

    public void produitsWindow() {
        vboxAdd.setVisible(true);
        boxDashboard.setVisible(false);
        boxProduits.setVisible(true);
        boxHistorique.setVisible(false);
    }

    public void historiqueWindow() {
        vboxAdd.setVisible(false);
        boxDashboard.setVisible(false);
        boxProduits.setVisible(false);
        boxHistorique.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton(e);
            }
        };
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        dashboardWindow();
        DashboardController:dashboard(client);
        ProduitsController:produits(client);
        HistoriquesController:historiques(client);

        /*
        // Requete vers l'URL
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/WebserviceTD/server/carte");

        // Méthode GET
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();
        System.out.println(response.readEntity(String.class));

        // Méthode GET avec un paramètre : on rajoute notre paramètre dans l'url
        WebTarget parametre = webTarget.path("/3");
        Invocation.Builder requeteParam = parametre.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response reponse2 = requeteParam.get();
        System.out.println(reponse2.readEntity(String.class));

        // Méthode DELETE Pareil qu'avant mais on met .delete() a la place de .get()
        WebTarget parametreDel = webTarget.path("/5");
        Invocation.Builder delete = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response deletePost = delete.delete();
        System.out.println(deletePost.readEntity(String.class));


        // Méthode POST

        JSONObject jo = new JSONObject();
        jo.put("idCarte", "1");
        jo.put("nomCarte", "La max corporation");
        jo.put("prix","300");
        jo.put("idMarque","1");
        Response larep= ((WebTarget) webTarget).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
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
