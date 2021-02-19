/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class ProcedureTypePojo {
    private Long id;
    private String procedureType;
    private String description; 
    
    public ProcedureTypePojo(String procedure_type_, String description_){
        this.procedureType = procedure_type_;
        this.description = description_;        
    }

    public ProcedureTypePojo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("procedureType", this.getProcedureType());
        jo_.put("description", this.getDescription());
        
        return jo_;        
    }
    
    public ProcedureTypePojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setProcedureType(jo_.containsKey("procedureType") ? jo_.get("procedureType").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);
        
        return this;
    }
    
    public ArrayList<ProcedureTypePojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcedureTypePojo> ObjectList = new ArrayList<>();
        
        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new ProcedureTypePojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    public String getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
