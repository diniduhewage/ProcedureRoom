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
public class ProcedureGroupPojo {
    private Long id;
    private String procGroup;
    private String description;    
    
    public ProcedureGroupPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setProcGroup(jo_.containsKey("procGroup") ? jo_.get("procGroup").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);        
        return this;
    }    
    
    public ArrayList<ProcedureGroupPojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcedureGroupPojo> ObjectList = new ArrayList<>();
        
        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new ProcedureGroupPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("procGroup", this.getProcGroup());
        jo_.put("description", this.getDescription());
        
        return jo_;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcGroup() {
        return procGroup;
    }

    public void setProcGroup(String procGroup) {
        this.procGroup = procGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    } 
}
