package com.sio.pi_zza;

import com.sio.pi_zza.DAO.commandeDAO;
import com.sio.pi_zza.DAO.contientDAO;
import com.sio.pi_zza.DAO.produitDAO;
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

        int sommeCommande = 0;
        int totalCommande = 0;

        JSONArray jsonCommande = new JSONArray();
        jsonCommande = commandeDAO.getCommandeByIdTables(idTables);

        totalCommande = jsonCommande.length();

        if(totalCommande == 0) {
        Label label = new Label("Aucune information n'est disponible pour la table numéro "+idTables);
        Box.getChildren().add(label);
        } else {
            for (int i = 0; i < jsonCommande.length(); i++) {
                JSONObject json1 = new JSONObject(jsonCommande.get(i).toString());
                int idCommande = Integer.parseInt((String) json1.get("idCommande"));
                int idTable = Integer.parseInt((String) json1.get("idTables"));

                Label numeroCommande = new Label("Numéro de la commande: " + idTables);
                Box.getChildren().add(numeroCommande);

                Button suprCommande = new Button("Supprimer la commande");
                Box.getChildren().add(suprCommande);

                JSONArray jsonContient = new JSONArray();
                jsonContient = contientDAO.getContientByIdCommande(idCommande);

                for (int j = 0; j < jsonContient.length(); j++) {
                    JSONObject json2 = new JSONObject(jsonContient.get(j).toString());
                    int idCommande2 = Integer.parseInt((String) json2.get("idCommande"));
                    int idProduit = Integer.parseInt((String) json2.get("idProduit"));
                    int quantite = Integer.parseInt((String) json2.get("quantite"));

                    Label value = new Label(j + " commandes: " + idCommande2);

                    Hubox.getChildren().add(value);

                    JSONObject jsonProduit = new JSONObject();
                    jsonProduit = produitDAO.getProduitById(idProduit);

                    int produitId = Integer.parseInt((String) jsonProduit.get("idProduit"));
                    String nomProduit = (String) jsonProduit.get("nomProduit");
                    float prixProduit = Float.parseFloat((String) jsonProduit.get("prixProduit"));
                    String imageProduit = (String) jsonProduit.get("imageProduit");
                    int idCategorie = Integer.parseInt((String) jsonProduit.get("idCategorie"));

                    /*
                    Button suprProduitCommande = new Button("Supprimer un produit");
                    Box.getChildren().add(suprProduitCommande);
                    */

                    sommeCommande += prixProduit * quantite;

                }

                Label numeroTable = new Label("Numéro de la table: " + idTables);
                Box.getChildren().add(numeroTable);

                Label nombrePerso = new Label("Numéro de la table: " + nbPersonne);
                Box.getChildren().add(nombrePerso);

                Label nombreCommande = new Label("Nombre de commande: " + totalCommande);
                Box.getChildren().add(nombreCommande);

                Label somCommande = new Label("Sommes des commandes: " + sommeCommande);
                Box.getChildren().add(somCommande);

                Button tableInoccupe = new Button("Mettre la table en innocupée");
                Box.getChildren().add(tableInoccupe);

                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        clickInnocupe(e, idTables);
                    }
                };

                tableInoccupe.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

            }
        }

        Scene stageScene = new Scene(Box, 500, 200);
        newStage.setScene(stageScene);
        newStage.show();
    }

    public void clickInnocupe(MouseEvent event, int idTables) {

        Stage newStage = new Stage();
        VBox Box = new VBox();
        HBox HuBox = new HBox();

        Box.getChildren().add(HuBox);

        Scene stageScene = new Scene(Box, 500, 200);
        newStage.setScene(stageScene);
        newStage.show();

    }
}


