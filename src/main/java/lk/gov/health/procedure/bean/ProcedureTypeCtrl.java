/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.bean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
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
    
    public void saveProcedureType(){
        JSONObject jo = selected.getJsonObject();
        
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype";

        ServiceConnector sc_ = new ServiceConnector();
        JSONObject res_jo_ = sc_.PostRequest(url_, jo);  
        
        items.add(selected.getObject(res_jo_));
    }
    
    public void getProcTypes() {        
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype";

        ServiceConnector sc_ = new ServiceConnector();
        items = selected.getObjectList(sc_.GetRequestList(url_));       
    } 
}
