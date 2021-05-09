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
import lk.gov.health.procedure.pojo.InstitutePojo;
import lk.gov.health.procedure.pojo.ProcPerInstPojo;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procPerInstCtrl")
@SessionScoped
public class ProcPerInstCtrl implements Serializable{
    
    public ProcPerInstCtrl() {
    }
    private ProcPerInstPojo selected = new ProcPerInstPojo();
    private ArrayList<ProcPerInstPojo> items = new ArrayList<>();
    private InstitutePojo institute;
    private final String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";

    public ProcPerInstPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcPerInstPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcPerInstPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcPerInstPojo> items) {
        this.items = items;
    }      

    public void getProceduresPerInstitution(String instCode) {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".procedureperinstitute/procedure_list/" + instCode);
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteProcedure() {
        Client client = Client.create();
        WebResource r_ = client.resource(baseUrl + ".procedureperinstitute/" + selected.getId());
        ClientResponse response = r_.type("application/json").delete(ClientResponse.class);
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure removed successfully");
            getProceduresPerInstitution(selected.getInstitute().getCode());
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
        }
    }
    
    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void getProcPerInst() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".procedureperinstitute");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InstitutePojo getInstitute() {
        return institute;
    }

    public void setInstitute(InstitutePojo institute) {
        this.institute = institute;
    }
}
