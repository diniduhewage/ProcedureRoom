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
import lk.gov.health.procedure.enums.ObjectStatus;
import lk.gov.health.procedure.pojo.MedProcedurePojo;
import lk.gov.health.procedure.pojo.ProcedureRoomTypePojo;
import lk.gov.health.procedure.pojo.ProcedureTypePojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("MedProcedureCtrl")
@SessionScoped
public class MedProcedureCtrl implements Serializable {

    private MedProcedurePojo selected = new MedProcedurePojo();
    private ArrayList<ProcedureTypePojo> procTypes;
    private ProcedureTypePojo procType = new ProcedureTypePojo();
    private ProcedureRoomTypePojo roomType = new ProcedureRoomTypePojo();
    private ArrayList<ProcedureTypePojo> procTypeList;

    private ArrayList<MedProcedurePojo> items;

    public MedProcedurePojo getSelected() {
        return selected;
    }

    public void setSelected(MedProcedurePojo selected) {
        this.selected = selected;
    }

    public ArrayList<MedProcedurePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<MedProcedurePojo> items) {
        this.items = items;
    }

    public ArrayList<ProcedureTypePojo> getProcTypes() {
        return procTypes;
    }

    public void setProcTypes(ArrayList<ProcedureTypePojo> procTypes) {
        this.procTypes = procTypes;
    }

    public String toMedProcedure() {
        selected = new MedProcedurePojo();
        this.getMedicalProcedures();
        return "/pages/procedure";
    }

    public void saveProcedure() {
        Client client = Client.create();

        if (selected.getId() == null) {
            JSONObject jo = selected.getJsonObject();
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure");
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
            WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure/" + selected.getId());
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

    public ArrayList<ProcedureTypePojo> fetchProcTypes(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return procType.getObjectList(sc_.GetRequestList(url_));
    }

    public ArrayList<ProcedureRoomTypePojo> fetchRoomTypes(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return roomType.getObjectList(sc_.GetRequestList(url_));
    }

    public void getMedicalProcedures() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray)new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ObjectStatus[] getObjectStatus() {
        return ObjectStatus.values();
    }

    public ProcedureTypePojo getProcType() {
        return procType;
    }

    public void setProcType(ProcedureTypePojo procType) {
        this.procType = procType;
    }

    public ProcedureRoomTypePojo getRoomType() {
        return roomType;
    }

    public void setRoomType(ProcedureRoomTypePojo roomType) {
        this.roomType = roomType;
    }

    public ArrayList<ProcedureTypePojo> getProcTypeList() {
        return procTypeList;
    }

    public void setProcTypeList(ArrayList<ProcedureTypePojo> procTypeList) {
        this.procTypeList = procTypeList;
    }
    
    public void deleteProcedure() {
        Client client = Client.create();
        WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure/" + selected.getId());
        webResource2.delete();
        addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Removed Successfully");
    }
    
    public void addMessage(Severity sev,String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
