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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.ProcedureGroupItemPojo;
import lk.gov.health.procedure.pojo.ProcedureGroupPojo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procedureGroupItemCtrl")
@SessionScoped
public class ProcedureGroupItemCtrl implements Serializable {

    private ProcedureGroupItemPojo selected = new ProcedureGroupItemPojo();
    private ArrayList<ProcedureGroupItemPojo> items;
    private ProcedureGroupPojo procGroup;
    private final String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";

    public void fetchItemsPerGroup() {
        fetchItems(procGroup);
    }
    
    public void prepareNew(){
        selected = new ProcedureGroupItemPojo();
    }

    public void addGroupItem() {
        Client client = Client.create();
        System.out.println("333333333333 -->"+procGroup.toString());
        selected.setProcGroup(procGroup);
        System.out.println("33333333333 33333333 -->"+selected.getId());
        if (selected.getId() == null) {
            JSONObject jo = selected.getJsonObject();
            jo.put("id", 123654);
            WebResource webResource1 = client.resource(baseUrl + ".groupitem");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Added Successfully");
                fetchItems(procGroup);
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }
        } 
    }
    
    public void removeProcedure() {
        Client client = Client.create();
        WebResource r_ = client.resource(baseUrl + ".groupitem/" + selected.getId());
        ClientResponse response = r_.type("application/json").delete(ClientResponse.class);
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Group Item removed successfully");
            fetchItems(procGroup);
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
        }
    }

    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void openNew(){
        selected = new ProcedureGroupItemPojo();
    }

    public ProcedureGroupItemPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureGroupItemPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureGroupItemPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureGroupItemPojo> items) {
        this.items = items;
    }

    public ProcedureGroupPojo getProcGroup() {
        return procGroup;
    }

    public void setProcGroup(ProcedureGroupPojo procGroup) {
        this.procGroup = procGroup;
    }

    private void fetchItems(ProcedureGroupPojo procGroup) {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".groupitem/filter_by_group/" + procGroup.getProcGroup());
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

}
