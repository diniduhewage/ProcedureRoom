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
public class ProcedureRoomTypePojo {
    private Long id;
    private String typeId;
    private String description;
    
    public ProcedureRoomTypePojo(){
        
    }
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
    
    public ProcedureRoomTypePojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setTypeId(jo_.containsKey("typeId") ? jo_.get("typeId").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);
        
        return this;
    }
    
    public ArrayList<ProcedureRoomTypePojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcedureRoomTypePojo> ObjectList = new ArrayList<>();
        
        for (int i = 0; i < ja_.size(); i++) {
            JSONObject jo_ = (JSONObject) ja_.get(i);

            ObjectList.add(new ProcedureRoomTypePojo().getObject(jo_));
        }
        return ObjectList;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
