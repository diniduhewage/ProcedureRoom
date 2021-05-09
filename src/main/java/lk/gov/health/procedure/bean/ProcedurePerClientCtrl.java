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
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lk.gov.health.procedure.enums.ProcPerClientStates;
import lk.gov.health.procedure.pojo.InstitutePojo;
import lk.gov.health.procedure.pojo.ProcPerInstPojo;
import lk.gov.health.procedure.pojo.ProcedurePerClientPojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procedurePerClientCtrl")
@SessionScoped
public class ProcedurePerClientCtrl implements Serializable {

    private ProcedurePerClientPojo selected = new ProcedurePerClientPojo();
    private ProcPerInstPojo medProcPerInst = new ProcPerInstPojo();
    private InstitutePojo institute = new InstitutePojo();
    private ArrayList<ProcedurePerClientPojo> items;
    private ArrayList<ProcPerInstPojo> procList;

    public String toAddProcedure() {
        this.getProcedures();
        return "/pages/medicalprocedures";
    }
    
    public String toProcedureLog() {
        selected = new ProcedurePerClientPojo();
//        this.getProcedureLog();
        return "/pages/procedure_log";
    }

    private final String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";

    private void getProcedures() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".procedureperclient");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(ProcPerInstCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProceduresPerInstitution(String insCode) {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".procedureperclient/filer_list/" + insCode);
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(ProcPerInstCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ProcPerInstPojo> fetchProcedures(String qryVal) {
        System.out.println("bbbbbbbbbbb -->"+institute.getCode());
        String url_ = baseUrl +".procedureperinstitute/filer_list/"+institute.getCode()+"/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return medProcPerInst.getObjectList(sc_.GetRequestList(url_));
    }

    public ArrayList<InstitutePojo> fetchRooms(String qryVal) {
        String url_ = baseUrl +".institute/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return institute.getObjectList(sc_.GetRequestList(url_));
    }

    public void saveClientProcedure() {
        Client client = Client.create();

        if (selected.getId() == null) {
            JSONObject jo = selected.getJsonObject();
            WebResource webResource1 = client.resource(baseUrl+".procedureperclient/register_procedure");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure added successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }

        } else {
            JSONObject jo = selected.getJsonObject();
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource(baseUrl+".procedureperclient/" + selected.getId());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Updated Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
            }
        }
    }

    public void addMessage(Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ProcPerClientStates[] getProcClientStatus() {
        return ProcPerClientStates.values();
    }

    public ProcedurePerClientPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedurePerClientPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedurePerClientPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedurePerClientPojo> items) {
        this.items = items;
    }

    public ArrayList<ProcPerInstPojo> getProcList() {
        return procList;
    }

    public void setProcList(ArrayList<ProcPerInstPojo> procList) {
        this.procList = procList;
    }

    public ProcPerInstPojo getmedProcPerInst() {
        return medProcPerInst;
    }

    public void setmedProcPerInst(ProcPerInstPojo medProcPerInst) {
        this.medProcPerInst = medProcPerInst;
    }

    public InstitutePojo getInstitute() {
        return institute;
    }

    public void setInstitute(InstitutePojo institute) {
        this.institute = institute;
    }
}
