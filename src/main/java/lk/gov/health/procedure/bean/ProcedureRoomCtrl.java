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
import lk.gov.health.procedure.pojo.CurrentHashPojo;
import lk.gov.health.procedure.pojo.InstitutePojo;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procedureRoomCtrl")
@SessionScoped
public class ProcedureRoomCtrl implements Serializable {
    @Inject
    private ProcGroupInstituteCtrl procGroupInstituteCtrl;

    private InstitutePojo selected = new InstitutePojo();
    private CurrentHashPojo selectedHash = new CurrentHashPojo();
    private CurrentHashPojo selectedHash2 = new CurrentHashPojo();
    private ArrayList<InstitutePojo> items;
    String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";
    String mainAppUrl = "http://localhost:8080/chims/data?name=";

    public InstitutePojo getSelected() {
        return selected;
    }

    public void setSelected(InstitutePojo selected) {
        this.selected = selected;
    }

    public ArrayList<InstitutePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<InstitutePojo> items) {
        this.items = items;
    }

    public String toProcedureRoom(String userRole, String instituteCode) {
        selected = new InstitutePojo();

        this.getProcedureRoomsPerInstitute(userRole, instituteCode);
        return "/pages/procedure_room";
    }
    
    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void getProcedureRooms() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".procedureroom");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getAllocatedGroups(){
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to Manage");
            return "";
        }        
        procGroupInstituteCtrl.setInstitute(selected);
        procGroupInstituteCtrl.fetchGroupsPerInstitute();
        return "/pages/procedure_group_institute";
    }

    public void getProcedureRoomsPerInstitute(String userRole, String insCode) {
        try {
            String apiString;
            Client client = Client.create();
            if (userRole.equals("System_Administrator")) {
                apiString = baseUrl + ".institute/get_procedure_rooms/NO_FILTER";
            } else {
                apiString = baseUrl + ".institute/get_procedure_rooms/" + insCode;
            }            
            WebResource webResource1 = client.resource(apiString);
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkInstituteHash() {
        return (getForeignInstituteHash() != null && getLocalInstituteHash() != null ? getForeignInstituteHash().equals(getLocalInstituteHash()) : false);
    }

    public String getLocalInstituteHash() {
        Client client = Client.create();
        String apiPath = baseUrl + "/find_by_owner/INSTITUTE";

        WebResource r_ = client.resource(apiPath);
        ClientResponse cr = r_.accept("application/json").get(ClientResponse.class);
        String outpt = cr.getEntity(String.class);
        try {
            CurrentHashPojo currHash = selectedHash.getObjectList((JSONArray) new JSONParser().parse(outpt)).get(0);
            return currHash.getCurrHash();
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getForeignInstituteHash() {
        Client client = Client.create();
        String apiPath = mainAppUrl + "get_value_list_hash";

        WebResource r_ = client.resource(apiPath);
        ClientResponse cr = r_.accept("application/json").get(ClientResponse.class);
        String outpt = cr.getEntity(String.class);
        try {
            CurrentHashPojo currHash = selectedHash2.getObjectList((JSONArray) new JSONParser().parse(outpt)).get(0);
            return currHash.getCurrHash();
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public CurrentHashPojo getSelectedHash() {
        return selectedHash;
    }

    public void setSelectedHash(CurrentHashPojo selectedHash) {
        this.selectedHash = selectedHash;
    }

    public CurrentHashPojo getSelectedHash2() {
        return selectedHash2;
    }

    public void setSelectedHash2(CurrentHashPojo selectedHash2) {
        this.selectedHash2 = selectedHash2;
    }

    public ProcGroupInstituteCtrl getProcGroupInstituteCtrl() {
        return procGroupInstituteCtrl;
    }

    public void setProcGroupInstituteCtrl(ProcGroupInstituteCtrl procGroupInstituteCtrl) {
        this.procGroupInstituteCtrl = procGroupInstituteCtrl;
    }
}
