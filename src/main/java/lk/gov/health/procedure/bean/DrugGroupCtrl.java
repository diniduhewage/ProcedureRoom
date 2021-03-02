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
import lk.gov.health.procedure.pojo.DrugGroupPojo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("DrugGroupCtrl")
@SessionScoped
public class DrugGroupCtrl implements Serializable {

    public DrugGroupCtrl() {
    }

    private DrugGroupPojo selected = new DrugGroupPojo();
    private ArrayList<DrugGroupPojo> groupTypes;
    private DrugGroupPojo parentGroup = new DrugGroupPojo();

    private ArrayList<DrugGroupPojo> items;

    public DrugGroupPojo getSelected() {
        return selected;
    }

    public void setSelected(DrugGroupPojo selected) {
        this.selected = selected;
    }

    public ArrayList<DrugGroupPojo> getGroupTypes() {
        return groupTypes;
    }

    public void setGroupTypes(ArrayList<DrugGroupPojo> groupTypes) {
        this.groupTypes = groupTypes;
    }

    public DrugGroupPojo getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(DrugGroupPojo parentGroup) {
        this.parentGroup = parentGroup;
    }

    public ArrayList<DrugGroupPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<DrugGroupPojo> items) {
        this.items = items;
    }

    public void getDrugGroups() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.druggroup");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(DrugGroupCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String toDrugGroups() {
        selected = new DrugGroupPojo();
        this.getDrugGroups();
        return "/pages/drug_groups";
    }

    public void saveDrugGroup() {
        JSONObject jo = selected.getJsonObject();

        Client client = Client.create();

        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.druggroup");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Drug Group Added Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }

        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.druggroup/" + selected.getId());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Drug group Updated Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
            }
        }
    }

    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ArrayList<DrugGroupPojo> fetchDrugGroups(String qryVal) {
      //  String url_ = "http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.druggroup/filer_list/" + qryVal;
      //  ServiceConnector sc_ = new ServiceConnector();
        return this.getItems();
    }

    public void deleteDrugGroup() {
        Client client = Client.create();
        WebResource webResource2 = client.resource("http://localhost:8080/DrugService/resources/lk.gov.health.drugservice.druggroup/" + selected.getId());
        webResource2.delete();
        addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Removed Successfully");
    }
}
