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
import lk.gov.health.procedure.pojo.ProcedureGroupItemPojo;
import lk.gov.health.procedure.pojo.ProcedureGroupPerInstitutePojo;
import lk.gov.health.procedure.pojo.ProcedureGroupPojo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procGroupInstituteCtrl")
@SessionScoped
public class ProcGroupInstituteCtrl implements Serializable {

    private ProcedureGroupPerInstitutePojo selected = new ProcedureGroupPerInstitutePojo();
    private ProcedureGroupItemPojo procGroupItem = new ProcedureGroupItemPojo();
    private ArrayList<ProcedureGroupItemPojo> procGroupItems;
    private ArrayList<ProcedureGroupPerInstitutePojo> items;
    private InstitutePojo institute;
    private ProcPerInstPojo procsPerInstitution = new ProcPerInstPojo();
    private final String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";

    public ProcedureGroupPerInstitutePojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureGroupPerInstitutePojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureGroupPerInstitutePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureGroupPerInstitutePojo> items) {
        this.items = items;
    }

    public InstitutePojo getInstitute() {
        return institute;
    }

    public void setInstitute(InstitutePojo institute) {
        this.institute = institute;
    }
    
    public void openNew(){
        selected = new ProcedureGroupPerInstitutePojo();
    }

    void fetchGroupsPerInstitute() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".proceduregroupperinstitute/proc_group_list/" + institute.getCode());
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addGroup() {
        Client client = Client.create();

        selected.setInstitute(institute);
        if (selected.getId() == null) {
            JSONObject jo = selected.getJsonObject();
            jo.put("id", 123654);
            WebResource webResource1 = client.resource(baseUrl + ".proceduregroupperinstitute");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            fetchGroupsPerInstitute();
            addProceduresPerInstitutes(selected.getProcedureGroup());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Group added Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }
        }
    }

    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void addProceduresPerInstitutes(ProcedureGroupPojo procGroup) {
        for (ProcedureGroupItemPojo i_ : getProcedurePerGroup(procGroup)) {
            Client client = Client.create();
            JSONObject jo = procsPerInstitution.getJsonObject(i_.getProcedure(),selected.getInstitute());
            System.out.println("mmmmmmmmmm -->"+jo.toString());
            WebResource webResource1 = client.resource(baseUrl + ".procedureperinstitute");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());            
        }
    }

    public ArrayList<ProcedureGroupItemPojo> getProcedurePerGroup(ProcedureGroupPojo procGroup) {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".groupitem/filter_by_group/" + procGroup.getProcGroup());
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            System.out.println("rrrrrrr -->"+outpt);
            procGroupItems = procGroupItem.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return procGroupItems;
    }
    
    public void removeProcedureGroup(){
        Client client = Client.create();
        WebResource r_ = client.resource(baseUrl + ".proceduregroupperinstitute/" + selected.getId());
        ClientResponse response = r_.type("application/json").delete(ClientResponse.class);
        removeProceduresPerInstitutes(selected.getProcedureGroup());
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Group removed successfully");
            fetchGroupsPerInstitute();
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
        }
    }

    private void removeProceduresPerInstitutes(ProcedureGroupPojo procedureGroup) {
        for (ProcedureGroupItemPojo i_ : getProcedurePerGroup(procedureGroup)) {
            Client client = Client.create();
            JSONObject jo = procsPerInstitution.getJsonObject(i_.getProcedure(),selected.getInstitute());
            System.out.println("mmmmmmmmmm -->"+jo.toString());
            WebResource webResource1 = client.resource(baseUrl + ".procedureperinstitute");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());            
        }
    }
    
    
}
