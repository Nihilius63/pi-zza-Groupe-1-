package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class produitDAO {

    public static JSONArray getProduit() {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        Invocation.Builder invocationProduit = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");
        Response response = invocationProduit.get();

        JSONArray jsonProduit = new JSONArray(response.readEntity(String.class));
        return jsonProduit;
    }

    public static JSONObject getProduitById(int idProduit) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        Invocation.Builder invocationProduit = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");

        WebTarget parametreIdProduit = webTargetProduit.path("/"+idProduit);
        Invocation.Builder requeteParamProduit = parametreIdProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");
        Response response = requeteParamProduit.get();

        JSONObject jsonProduit = new JSONObject(response.readEntity(String.class));
        return jsonProduit;
    }

    public static JSONArray getProduitByIdCategorie(int idCategorie) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/produit/categorie");

        Invocation.Builder invocationProduitByIdCategorie = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduitByIdCategorie.header("some-header", "true");

        WebTarget parametreIdCategorie = webTargetProduit.path("/"+idCategorie);
        Invocation.Builder requeteParamProduit = parametreIdCategorie.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduitByIdCategorie.header("some-header", "true");
        Response response = requeteParamProduit.get();

        JSONArray jsonProduitByCategorie = new JSONArray(response.readEntity(String.class));
        return jsonProduitByCategorie;
    }

    public static void deleteProduitById(int idProduit) {

        Client client = ClientBuilder.newClient();

        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");
        Invocation.Builder invocationProduit = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");

        WebTarget parametreDel = webTargetProduit.path("/"+idProduit);
        Invocation.Builder delete = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");
        delete.delete();
    }

    public static void updateProduitById(int idProduit, String nomProduit, float prixProduit, String image, int categorieId) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        JSONObject update = new JSONObject();
        update.put("idProduit", idProduit);
        update.put("nomProduit", nomProduit);
        update.put("prixProduit",prixProduit);
        update.put("imageProduit",image);
        update.put("idCategorie",categorieId);

        webTargetProduit.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(update.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
    }

    public static void createProduit(String nomProduit, float prixProduit, String image, int categorieId) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/produit");

        JSONObject create = new JSONObject();
        create.put("idProduit", 1);
        create.put("nomProduit", nomProduit);
        create.put("prixProduit", prixProduit);
        create.put("imageProduit", image);
        create.put("idCategorie", categorieId);

        webTargetProduit.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(create.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);

    }

}
