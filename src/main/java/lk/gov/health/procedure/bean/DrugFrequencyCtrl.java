/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.bean;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.DrugFrequencyPojo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("DrugFrequencyCtrl")
@SessionScoped
public class DrugFrequencyCtrl implements Serializable {

    public DrugFrequencyCtrl() {
    }
    private DrugFrequencyPojo selected = new DrugFrequencyPojo();

    private ArrayList<DrugFrequencyPojo> items = new ArrayList<>();

    public DrugFrequencyPojo getSelected() {
        return selected;
    }

    public void setSelected(DrugFrequencyPojo selected) {
        this.selected = selected;
    }

    public ArrayList<DrugFrequencyPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<DrugFrequencyPojo> items) {
        this.items = items;
    }

    public void getDrugFrequencies() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.drugfrequency");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray)new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(DrugFrequencyCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }  
    
    public String toDrugFrequiences() {
        selected = new DrugFrequencyPojo();   
        getDrugFrequencies();
        return "/pages/drug_frequencies";
    }    


    public void deleteDrugFreqency(){
        Client client = Client.create();
        WebResource webResource2 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.drugfrequency/" + selected.getId());
        webResource2.delete();
    }     
    
    public void saveDrugFrequency(){
        JSONObject jo = selected.getJsonObject();
        
        Client client = Client.create();

        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.drugfrequency");
            webResource1.type("application/json").post(ClientResponse.class, jo.toString());
        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.drugfrequency/" + selected.getId());
            webResource2.type("application/json").put(ClientResponse.class, jo.toString());
        }
    }
    
}
