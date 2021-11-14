package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class historiqueDAO {

    public static JSONArray getHistorique() {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetHistorique = client.target("http://localhost/pi-zza-Groupe-1-/server/historique");

        Invocation.Builder invocationBuilder = webTargetHistorique.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();

        JSONArray jsonHistorique = new JSONArray(response.readEntity(String.class));
        return jsonHistorique;
    }

    public static JSONObject getHistoriqueById(int idHistorique) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetHistorique = client.target("http://localhost/pi-zza-Groupe-1-/server/historique");

        Invocation.Builder invocationHistorique = webTargetHistorique.request(MediaType.TEXT_PLAIN_TYPE);
        invocationHistorique.header("some-header", "true");

        WebTarget parametreHistorique = webTargetHistorique.path("/"+idHistorique);
        Invocation.Builder requeteParamProduit = parametreHistorique.request(MediaType.TEXT_PLAIN_TYPE);
        invocationHistorique.header("some-header", "true");
        Response response = requeteParamProduit.get();

        JSONObject jsonHistorique = new JSONObject(response.readEntity(String.class));
        return jsonHistorique;
    }

    public static void createHistory(String nomProduit, int quantite, float prixProduit, String dateCommande, int numeroTable) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/historique");

        JSONObject create = new JSONObject();
        create.put("idHistorique", 1);
        create.put("nomProduit", nomProduit);
        create.put("quantite", quantite);
        create.put("prixProduit", prixProduit);
        create.put("dateCommande", dateCommande);
        create.put("numeroTables", numeroTable);

        webTargetProduit.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(create.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
    }

}
