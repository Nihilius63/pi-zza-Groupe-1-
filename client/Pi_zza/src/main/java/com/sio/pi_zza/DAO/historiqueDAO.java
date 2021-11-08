package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
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

}
