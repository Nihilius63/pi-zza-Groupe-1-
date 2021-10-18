package com.sio.pi_zza;

import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.ws.rs.client.Client;

public class HistoriquesController extends DashboardController {

    private int cpt = 0;

    @FXML private GridPane historiques;

    public void historiques(Client client) {
        WebTarget webTarget = client.target("http://localhost/pi-zza-Groupe-1-/server/historique");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();

        JSONArray jsonArray = new JSONArray(response.readEntity(String.class));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            String nomProduit = (String) json.get("nomProduit");
            int quantite = Integer.parseInt((String) json.get("quantite"));
            int prixProduit = Integer.parseInt((String) json.get("prixProduit"));
            String date = (String) json.get("dateCommande");
            int numeroTable = Integer.parseInt((String) json.get("numeroTables"));

            Label lab = new Label(nomProduit + " et " + quantite + " et " + prixProduit + " et " + date + " et " + numeroTable );

            historiques.add(lab, cpt % 3, cpt / 3);

            if (cpt < jsonArray.length() ) {
                cpt++;
            }

        }
    }

}
