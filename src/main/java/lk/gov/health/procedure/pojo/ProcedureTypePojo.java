/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class ProcedureTypePojo {
    private String procedureType;
    private String description; 
    
    public ProcedureTypePojo(String procedure_type_, String description_){
        this.procedureType = procedure_type_;
        this.description = description_;        
    }
    
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("procedureType", this.getProcedureType());
        jo_.put("description", this.getDescription());
        
        return jo_;        
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
}
