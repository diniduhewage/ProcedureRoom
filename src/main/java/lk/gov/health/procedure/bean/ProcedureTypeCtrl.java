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
import javax.faces.application.FacesMessage.Severity;
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

    public void saveProcedureType() {
        JSONObject jo = selected.getJsonObject();

        Client client = Client.create();
        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 202) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Type Added Successfully");
            } else {
                response.bufferEntity();
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", response.getEntity(String.class));
            }
        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype/" + selected.getId());
            ClientResponse response =  webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Type Updated Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", response.getEntity(String.class));
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
        webResource2.delete();
    }
    
    public void addMessage(Severity sev,String summary, String detail) {
        FacesMessage message = new FacesMessage(sev, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
