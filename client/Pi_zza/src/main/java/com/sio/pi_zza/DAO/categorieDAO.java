package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class categorieDAO {

    public static JSONArray getCategorie() {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetCategorie = client.target("http://localhost/pi-zza-Groupe-1-/server/categorie");

        Invocation.Builder invocationCategorie = webTargetCategorie.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCategorie.header("some-header", "true");
        Response response = invocationCategorie.get();

        JSONArray jsonCategorie = new JSONArray(response.readEntity(String.class));
        return jsonCategorie;
    }

    public static JSONObject getCategorieById(int idCategorie) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetCategorie = client.target("http://localhost/pi-zza-Groupe-1-/server/categorie");

        Invocation.Builder invocationCategorie = webTargetCategorie.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCategorie.header("some-header", "true");

        WebTarget parametreCategorie = webTargetCategorie.path("/"+idCategorie);
        Invocation.Builder requeteParamCategorie = parametreCategorie.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCategorie.header("some-header", "true");
        Response response = requeteParamCategorie.get();

        JSONObject jsonCategorie = new JSONObject(response.readEntity(String.class));
        return jsonCategorie;
    }
}
