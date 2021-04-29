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
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.ProcedureTypePojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
@Named("ProcedureTypeCtrl")
@SessionScoped
public class ProcedureTypeCtrl implements Serializable {

    private ProcedureTypePojo selected = new ProcedureTypePojo();

    private ArrayList<ProcedureTypePojo> items;

    public ProcedureTypePojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureTypePojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureTypePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureTypePojo> items) {
        this.items = items;
    }

    public String toProcedureType() {
        selected = new ProcedureTypePojo();
        getProcTypes();
        return "/pages/procedure_type";
    }

    public ProcedureTypePojo prepareNew() {
        selected = new ProcedureTypePojo();
        return selected;
    }

    public void saveProcedureType() {
        JSONObject jo = selected.getJsonObject();

        Client client = Client.create();
        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype?api_key=EF16A5D4EF8AA6AA0580AF1390CF0600");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Type added successfully");
                getProcTypes();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }
        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype/" + selected.getId());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());

            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Type updated Successfully");
                getProcTypes();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
            }
        }
    }

    public void getProcTypes() {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype";

        ServiceConnector sc_ = new ServiceConnector();
        items = selected.getObjectList(sc_.GetRequestList(url_));
    }

    public void deleteProcedureType() {
        Client client = Client.create();
        WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype/" + selected.getId());
       
        ClientResponse response_ = webResource2.type("application/json").delete(ClientResponse.class);
        if (response_.getStatus() == 200 || response_.getStatus() == 204) {
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Deleted Successfully");
        } else if(response_.getStatus() == 500) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Procedure type already used, Can not delete");
        
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to delete");
        }
    }

    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
