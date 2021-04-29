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
import javax.inject.Inject;
import javax.inject.Named;
import lk.gov.health.procedure.facade.util.JsfUtil;
import lk.gov.health.procedure.pojo.ProcedureGroupPojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procedureGroupCtrl")
@SessionScoped
public class ProcedureGroupCtrl implements Serializable{  
    @Inject
    ProcedureGroupItemCtrl procGroupItemCtrl;
    
    private ProcedureGroupPojo selected = new ProcedureGroupPojo();
    private ArrayList<ProcedureGroupPojo> items;
    private final String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";
    
    public String toProcedureGroups() {
        selected = new ProcedureGroupPojo();
        this.getProcedureGroups();
        return "/pages/procedure_group";
    }
    
    public String getProcedureGroupItems() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to Manage");
            return "";
        }        
        procGroupItemCtrl.setProcGroup(selected);
        procGroupItemCtrl.fetchItemsPerGroup();
        return "/pages/procedure_group_item";
    }

    public void getProcedureGroups() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl+".proceduregroup");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void saveProcedureGroup() {
        JSONObject jo = selected.getJsonObject();

        Client client = Client.create();

        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource(baseUrl+".proceduregroup");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Group added successfully");
                getProcedureGroups();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }

        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource(baseUrl+".proceduregroup/" + selected.getId());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Group updated Successfully");
                getProcedureGroups();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
            }
        }
    }
    
    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ProcedureGroupPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureGroupPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureGroupPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureGroupPojo> items) {
        this.items = items;
    }
    
    public ArrayList<ProcedureGroupPojo> fetchGroups(String qryVal) {
        String url_ = baseUrl+".proceduregroup/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return selected.getObjectList(sc_.GetRequestList(url_));
    }
    
}
