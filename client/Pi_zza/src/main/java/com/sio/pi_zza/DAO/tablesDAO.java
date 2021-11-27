package com.sio.pi_zza.DAO;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class tablesDAO {

    public static JSONArray getTables() {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/tables");

        Invocation.Builder invocationProduit = webTargetProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationProduit.header("some-header", "true");
        Response response = invocationProduit.get();

        JSONArray jsonTables = new JSONArray(response.readEntity(String.class));
        return jsonTables;
    }

    public static JSONObject getTablesById(int idTables) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetTables = client.target("http://localhost/pi-zza-Groupe-1-/server/tables");

        Invocation.Builder invocationTables = webTargetTables.request(MediaType.TEXT_PLAIN_TYPE);
        invocationTables.header("some-header", "true");

        WebTarget parametreIdProduit = webTargetTables.path("/"+idTables);
        Invocation.Builder requeteParamTables = parametreIdProduit.request(MediaType.TEXT_PLAIN_TYPE);
        invocationTables.header("some-header", "true");
        Response response = requeteParamTables.get();

        JSONObject jsonTables = new JSONObject(response.readEntity(String.class));
        return jsonTables;
    }

    public static void inoccupeTable(int idTables) {

        Client client = ClientBuilder.newClient();
        WebTarget webTargetProduit = client.target("http://localhost/pi-zza-Groupe-1-/server/tables");

        JSONObject jsoTable = getTablesById(idTables);
        int nbPlace = Integer.parseInt((String) jsoTable.get("nbPlaces"));

        JSONObject update = new JSONObject();
        update.put("idTables", idTables);
        update.put("nbPlaces", nbPlace);
        update.put("nbPersonne", 0);

        webTargetProduit.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(update.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
    }

}
