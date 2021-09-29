/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sio.testjersey2;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientResponse;
import org.json.JSONObject;


/**
 *
 * @author palmier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Requete vers l'URL
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/Webservice/server/carte");
        
        // Méthode GET
        Invocation.Builder invocationBuilder
                = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response response = invocationBuilder.get();
        System.out.println(response.readEntity(String.class));
        
        // Méthode GET avec un paramètre : on rajoute notre paramètre dans l'url
        WebTarget parametre = webTarget.path("/3");
        Invocation.Builder requeteParam
                = parametre.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response reponse2 = requeteParam.get();
        System.out.println(reponse2.readEntity(String.class));
        
        // Méthode DELETE Pareil qu'avant mais on met .delete() a la place de .get()
        WebTarget parametreDel = webTarget.path("/5");
        Invocation.Builder delete
                = parametreDel.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
        Response deletePost = delete.delete();
        System.out.println(deletePost.readEntity(String.class));
        
        
        // Méthode POST 

        JSONObject jo = new JSONObject();
        jo.put("idCarte", "1");
        jo.put("nomCarte", "La max corporation");
        jo.put("prix","300");
        jo.put("idMarque","1");
        Response larep=webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
        System.out.println("Form response " + larep.getStatus());
        
        // Méthode PUT
        JSONObject update = new JSONObject();
        jo.put("idCarte", "12");
        jo.put("nomCarte", "La maxmx corporation");
        jo.put("prix","300");
        jo.put("idMarque","1");
        Response larep2=webTarget.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(jo.toString(),MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);
        System.out.println("Form response " + larep.getStatus());
        
        
        
        

    }
}
