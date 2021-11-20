package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class commandeDAO {

    public static JSONArray getCommand() {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetCommande = client.target("http://localhost/pi-zza-Groupe-1-/server/commande");

        Invocation.Builder invocationCommande = webTargetCommande.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCommande.header("some-header", "true");
        Response response = invocationCommande.get();

        JSONArray jsonCommande = new JSONArray(response.readEntity(String.class));
        return jsonCommande;
    }

    public static JSONArray getCommandeByIdTables(int idTables) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetCommande = client.target("http://localhost/pi-zza-Groupe-1-/server/commande/tables");

        Invocation.Builder invocationCommande = webTargetCommande.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCommande.header("some-header", "true");

        WebTarget parametreHistorique = webTargetCommande.path("/"+idTables);
        Invocation.Builder requeteParamCommande = parametreHistorique.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCommande.header("some-header", "true");
        Response response = requeteParamCommande.get();

        JSONArray jsonCommande = new JSONArray(response.readEntity(String.class));
        return jsonCommande;
    }

    public static JSONObject getCommandeByIdCommande(int idCommande) {
        Client client = ClientBuilder.newClient();
        WebTarget webTargetCommande = client.target("http://localhost/pi-zza-Groupe-1-/server/commande");

        Invocation.Builder invocationCommande = webTargetCommande.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCommande.header("some-header", "true");

        WebTarget parametreHistorique = webTargetCommande.path("/"+idCommande);
        Invocation.Builder requeteParamCommande = parametreHistorique.request(MediaType.TEXT_PLAIN_TYPE);
        invocationCommande.header("some-header", "true");
        Response response = requeteParamCommande.get();

        JSONObject jsonCommande = new JSONObject(response.readEntity(String.class));
        return jsonCommande;
    }

    public static void deleteByIdCommande(int idCommande) {

        Client client = ClientBuilder.newClient();

        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/commande");
        Invocation.Builder invocationProduit = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");

        WebTarget parametreDel = webTargetProduit.path("/"+idCommande);
        Invocation.Builder delete = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");
        delete.delete();
    }

}
