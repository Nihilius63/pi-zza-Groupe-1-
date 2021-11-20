package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class contientDAO {

    public static JSONArray getContient() {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetContient = client.target("http://localhost/pi-zza-Groupe-1-/server/contient");

        Invocation.Builder invocationContient = webTargetContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");
        Response response = invocationContient.get();

        JSONArray jsonContient = new JSONArray(response.readEntity(String.class));
        return jsonContient;
    }

    public static JSONArray getContientByIdCommande(int idCommande) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetContient = client.target("http://localhost/pi-zza-Groupe-1-/server/contient/commande");

        Invocation.Builder invocationContient = webTargetContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");

        WebTarget parametreContient = webTargetContient.path("/"+idCommande);
        Invocation.Builder requeteParamProduit = parametreContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");
        Response response = requeteParamProduit.get();

        JSONArray jsonContient = new JSONArray(response.readEntity(String.class));
        return jsonContient;
    }

    public static JSONObject getContientByIdProduit(int idProduit) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetContient = client.target("http://localhost/pi-zza-Groupe-1-/server/contient/produit/");

        Invocation.Builder invocationContient = webTargetContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");

        WebTarget parametreContient = webTargetContient.path("/"+idProduit);
        Invocation.Builder requeteParamProduit = parametreContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");
        Response response = requeteParamProduit.get();

        JSONObject jsonContient = new JSONObject(response.readEntity(String.class));
        return jsonContient;
    }

    public static JSONObject getContientByIdCommandeProduit(int idCommande, int idProduit) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetContient = client.target("http://localhost/pi-zza-Groupe-1-/server/contient/");

        Invocation.Builder invocationContient = webTargetContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");

        WebTarget parametreContient = webTargetContient.path("/"+idCommande+"/"+idProduit);
        Invocation.Builder requeteParamProduit = parametreContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");
        Response response = requeteParamProduit.get();

        JSONObject jsonContient = new JSONObject(response.readEntity(String.class));
        return jsonContient;
    }

    public static void deleteContientByIdCommande(int idCommande) {
        Client client = ClientBuilder.newClient();

        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/contient/commande/");
        Invocation.Builder invocationProduit = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");

        WebTarget parametreDel = webTargetProduit.path("/"+idCommande);
        Invocation.Builder delete = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");
        delete.delete();
    }

    public static void deleteContientByIdCommandeProduit(int idCommande, int idProduit) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetContient = client.target("http://localhost/pi-zza-Groupe-1-/server/contient/");

        Invocation.Builder invocationContient = webTargetContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");

        WebTarget parametreContient = webTargetContient.path("/"+idCommande+"/"+idProduit);
        Invocation.Builder requeteParamProduit = parametreContient.request(MediaType.TEXT_PLAIN_TYPE);
        invocationContient.header("some-header", "true");

        requeteParamProduit.delete();

    }
}
