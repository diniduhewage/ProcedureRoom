package lk.gov.health.procedure.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kusal
 */
public class ServiceConnector {

    public JSONObject GetRequest(String resourceUrl) {

        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(resourceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            

        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonObject;
    }

    public JSONArray GetRequestList(String resourceUrl) {

        JSONArray jsonArray = new JSONArray();
        try {
            URL url = new URL(resourceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONParser jsonParser = new JSONParser();

            jsonArray = (JSONArray) jsonParser.parse(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            con.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonArray;
    }

    public JSONObject PostRequest(String resourceUrl, JSONObject payload) {

        JSONObject jsonObject = new JSONObject();
        String jsonInputString = null;
        try {
            URL url = new URL(resourceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONParser jsonParser = new JSONParser();
            JSONObject.toString(jsonInputString, payload);
            
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            jsonObject = (JSONObject) jsonParser.parse(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));

        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServiceConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonObject;
    }
    
}
