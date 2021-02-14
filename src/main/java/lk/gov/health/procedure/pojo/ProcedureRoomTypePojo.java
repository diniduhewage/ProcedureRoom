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
public class ProcedureRoomTypePojo {
    private String typeId;
    private String description;
    
    public ProcedureRoomTypePojo(String type_id_ , String description_){
        this.typeId = type_id_;
        this.description = description_;
    }
    
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("typeId", this.getTypeId());
        jo_.put("description", this.getDescription());
        
        return jo_;        
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
