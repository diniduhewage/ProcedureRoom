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
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.MedProcedurePojo;
import lk.gov.health.procedure.pojo.ProcedureRoomTypePojo;
import lk.gov.health.procedure.pojo.ProcedureTypePojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONObject;

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
        return "/pages/procedure";
    }
    
    public void saveProcedure(){
        JSONObject jo = selected.getJsonObject();

        Client client = Client.create();
        System.out.println("yyyyyyyyyy -->"+jo);

        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure");
            webResource1.type("application/json").post(ClientResponse.class, jo);
        } else {
            WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.medprocedure/" + selected.getId());
            webResource2.type("application/json").put(ClientResponse.class, jo);
        }
    }

    public ArrayList<ProcedureTypePojo> fetchProcTypes(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype/filer_list/"+qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        procTypeList = procType.getObjectList(sc_.GetRequestList(url_));
        return procTypeList;
    }
    
    public ArrayList<ProcedureRoomTypePojo> fetchRoomTypes(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype/filer_list/"+qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return roomType.getObjectList(sc_.GetRequestList(url_));
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
}
