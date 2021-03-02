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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lk.gov.health.procedure.enums.ProcPerClientStates;
import lk.gov.health.procedure.pojo.MedProcedurePojo;
import lk.gov.health.procedure.pojo.ProcedurePerClientPojo;
import lk.gov.health.procedure.pojo.ProcedureRoomPojo;
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
    private MedProcedurePojo MedProcedure = new MedProcedurePojo();
    private ProcedureRoomPojo ProcRoom = new ProcedureRoomPojo();
    private ArrayList<ProcedurePerClientPojo> items;
    private ArrayList<MedProcedurePojo> procList;

    public String toAddProcedure() {
        selected = new ProcedurePerClientPojo();
        this.getProcedures();
        return "/pages/medicalprocedures";
    }

    private void getProcedures() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureperclient");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            System.out.println("jjjjjjjjjjjjjj -->"+outpt);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<MedProcedurePojo> fetchProcedures(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return MedProcedure.getObjectList(sc_.GetRequestList(url_));
    }
    
    public ArrayList<ProcedureRoomPojo> fetchRooms(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroom/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return ProcRoom.getObjectList(sc_.GetRequestList(url_));
    }
    
    public void saveClientProcedure() {
        Client client = Client.create();

        if (selected.getId() == null) {
            selected.setCreatedAt(new Date());
            JSONObject jo = selected.getJsonObject();
            jo.put("id", 123654);
            System.out.println("uuuuuuuuuuu 11 -->"+jo.toString());
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureperclient");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if(response.getStatus() == 200 || response.getStatus() == 204){
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Added Successfully");
            }
            else
            {
               addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }
                    
        } else {
            JSONObject jo = selected.getJsonObject();
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureperclient/" + selected.getId());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if(response.getStatus() == 200 || response.getStatus() == 204){
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Updated Successfully");
            }
            else
            {
               addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
            }
        }
    }
    
    public void addMessage(Severity sev,String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public ProcPerClientStates[] getProcClientStatus(){
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

    public ArrayList<MedProcedurePojo> getProcList() {
        return procList;
    }

    public void setProcList(ArrayList<MedProcedurePojo> procList) {
        this.procList = procList;
    }

    public MedProcedurePojo getMedProcedure() {
        return MedProcedure;
    }

    public void setMedProcedure(MedProcedurePojo MedProcedure) {
        this.MedProcedure = MedProcedure;
    }

}
