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
import lk.gov.health.procedure.pojo.InventoryUnitPojo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("InventoryUnitCtrl")
@SessionScoped
public class InventoryUnitCtrl implements Serializable{

    public InventoryUnitCtrl() {
    }
    
    private InventoryUnitPojo selected = new InventoryUnitPojo();
    
    private ArrayList<InventoryUnitPojo> items = new ArrayList<>();

    public InventoryUnitPojo getSelected() {
        return selected;
    }

    public void setSelected(InventoryUnitPojo selected) {
        this.selected = selected;
    }

    public ArrayList<InventoryUnitPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<InventoryUnitPojo> items) {
        this.items = items;
    }

    public void getInventoryUnits() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource("http://localhost:8080/DrugService_K/resources/lk.gov.health.drugservice.inventoryunit");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray)new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }  
    
    public String toInventoryQuantityTypes() {
        selected = new InventoryUnitPojo();   
        getInventoryUnits();
        return "/pages/inventory_units";
    }

    public void deleteInventoryUnit(){
        Client client = Client.create();
        WebResource webResource2 = client.resource("http://localhost:8080/DrugService_K/resources/lk.gov.health.drugservice.inventoryunit/" + selected.getId());
        webResource2.delete();
    }     
    
    public void saveInventoryUnit(){
        JSONObject jo = selected.getJsonObject();
        
        Client client = Client.create();

        if (selected.getId() == null) {
            jo.put("id", 123654);
            System.out.println("55555555555 -->"+jo.toString());
            WebResource webResource1 = client.resource("http://localhost:8080/DrugService_K/resources/lk.gov.health.drugservice.inventoryunit");
            webResource1.type("application/json").post(ClientResponse.class, jo.toString());
        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/DrugService_K/resources/lk.gov.health.drugservice.inventoryunit/" + selected.getId());
            webResource2.type("application/json").put(ClientResponse.class, jo.toString());
        }
    }
}
