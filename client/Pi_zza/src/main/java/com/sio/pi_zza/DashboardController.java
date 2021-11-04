package com.sio.pi_zza;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class DashboardController {

    private int cpt = 0;

    @FXML private GridPane tables;

    public void dashboard(Client client) {

        WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/tables");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();

        JSONArray jsonArray = new JSONArray(response.readEntity(String.class));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            int idTables = Integer.parseInt((String) json.get("idTables"));
            int nbPlaces = Integer.parseInt((String) json.get("nbPlaces"));
            int nbPersonne = Integer.parseInt((String) json.get("nbPersonne"));

            VBox vbo = new VBox();

            Label lab = new Label("Numéro de la table: "+idTables+"\nNombre de places: "+nbPlaces+"\nNombre de personne: "+nbPersonne);
            Button infoSupl = new Button("Informations");

            vbo.getChildren().add(lab);
            vbo.getChildren().add(infoSupl);

            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e)
                {
                    clickInfosSupl(e, client, idTables, nbPersonne);
                }
            };

            infoSupl.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

            tables.add(vbo, cpt % 3, cpt / 3);

            if (cpt < jsonArray.length() ) {
                cpt++;
            }
        }
    }

    public void clickInfosSupl(MouseEvent e,Client client, int idTables, int nbPersonne) {

        Stage newStage = new Stage();
        VBox Box = new VBox();
        HBox Hubox = new HBox();

        int total = 0;

        WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/commande");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();

        JSONArray jsonArray = new JSONArray(response.readEntity(String.class));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            int idCommande = Integer.parseInt((String) json.get("idCommande"));
            int idTable = Integer.parseInt((String) json.get("idTables"));

            if(idTable == idTables) {

                WebTarget webTarget2 = client.target("http://localhost/pi-zza-Groupe-1-/server/contient");

                Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.TEXT_PLAIN_TYPE);
                invocationBuilder2.header("some-header", "true");
                Response response2 = invocationBuilder2.get();

                JSONArray jsonArray2 = new JSONArray(response2.readEntity(String.class));

                for (int j = 0; j < jsonArray2.length(); j++) {
                    JSONObject json2 = new JSONObject(jsonArray2.get(j).toString());
                    int idCommande2 = Integer.parseInt((String) json2.get("idCommande"));
                    int idProduit = Integer.parseInt((String) json2.get("idProduit"));
                    int quantite = Integer.parseInt((String) json2.get("quantite"));

                    if(idCommande == idCommande2) {

                        Label value = new Label(j + " commandes: " + idCommande2);

                        Hubox.getChildren().add(value);

                        WebTarget webTarget4 = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

                        Invocation.Builder invocationBuilder4 = webTarget4.request(MediaType.TEXT_PLAIN_TYPE);
                        invocationBuilder4.header("some-header", "true");
                        Response response4 = invocationBuilder2.get();

                        JSONArray jsonArray4 = new JSONArray(response4.readEntity(String.class));

                        for (int k = 0; k < jsonArray4.length(); k++) {
                            JSONObject json3 = new JSONObject(jsonArray4.get(k).toString());
                            int idProduit2 = Integer.parseInt((String) json3.get("idProduit"));





                        }

                        Label numeroTable = new Label("Numéro de la table: " + idTables);
                        Box.getChildren().add(numeroTable);

                        Label nombrePerso = new Label("Numéro de la table: " + nbPersonne);
                        Box.getChildren().add(nombrePerso);

                        Label nombreCommande = new Label("Commande: " + idTables);
                        Box.getChildren().add(nombreCommande);

                        Label sommeCommande = new Label("Sommes des commandes: " + total);
                        Box.getChildren().add(sommeCommande);

                    }
                }

            }
        }

        Scene stageScene = new Scene(Box, 500, 200);
        newStage.setScene(stageScene);
        newStage.show();
    }
}


